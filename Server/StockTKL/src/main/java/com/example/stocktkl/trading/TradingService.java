package com.example.stocktkl.trading;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.Portfolio;
import com.example.stocktkl.model.Quote;
import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.model.enum_class.EOrderStatus;
import com.example.stocktkl.model.enum_class.EOrderType;
import com.example.stocktkl.repository.OrderRepository;
import com.example.stocktkl.repository.PortfolioRepository;
import com.example.stocktkl.repository.QuoteRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tan Dang
 * @since 25/11/2023 - 9:31 pm
 */
@Service
@Log
public class TradingService implements ITradingService {

    private final OrderRepository orderRepository;
    private final QuoteRepository quoteRepository;
    private final PortfolioRepository portfolioRepository;

    public TradingService(OrderRepository orderRepository, QuoteRepository quoteRepository, PortfolioRepository portfolioRepository) {
        this.orderRepository = orderRepository;
        this.quoteRepository = quoteRepository;
        this.portfolioRepository = portfolioRepository;
    }

    private BigDecimal calculateNewPrice(Order order, Quote latestQuote) {
        return latestQuote.getPrice();
        // TODO: 25/11/2023 Implement algorithm to calculate new price
//        BigDecimal newPrice;
//        if (order.getDirection() == EOrderDirection.BUY) {
//            newPrice = latestQuote.getPrice().multiply(BigDecimal.valueOf(latestQuote.getVolume()))
//                    .add(order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
//                    .divide(BigDecimal.valueOf(latestQuote.getVolume() + order.getQuantity()));
//        } else {
//            newPrice = latestQuote.getPrice().multiply(BigDecimal.valueOf(latestQuote.getVolume()))
//                    .subtract(order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
//                    .divide(BigDecimal.valueOf(latestQuote.getVolume() - order.getQuantity()));
//        }
//        return newPrice;
    }

    private Quote createNewQuote(Quote latestQuote, Order order) {
        int newVolume = order.getQuantity() + latestQuote.getVolume();
        return new Quote().toBuilder()
                .stockId(latestQuote.getStockId())
                .price(calculateNewPrice(order, latestQuote))
                .percentChange(latestQuote.getPercentChange())
                .changeValue(latestQuote.getChangeValue())
                .volume(newVolume)
                .build();
    }

    private Portfolio createNewPortfolio(Order order, BigDecimal purchasePrice, int soldQuantity, int boughtQuantity) {
        try {
            Portfolio oldPortfolio = portfolioRepository.findByStockIdAndUserId(order.getStockId(),
                    order.getUserId()).orElseThrow(RuntimeException::new);
            if (order.getDirection() == EOrderDirection.BUY) {
                int totalQuantity = oldPortfolio.getQuantity() + boughtQuantity;
                BigDecimal avgPurchasePrice = calculateAvgPrice(oldPortfolio, totalQuantity,
                        purchasePrice);
                oldPortfolio.setQuantity(totalQuantity);
                oldPortfolio.setAvgPurchasePrice(avgPurchasePrice);
                oldPortfolio.setPurchaseDate(LocalDateTime.now());
                return oldPortfolio;
            } else {
                oldPortfolio.setQuantity(oldPortfolio.getQuantity() - soldQuantity);
                oldPortfolio.setPurchaseDate(LocalDateTime.now());
                return oldPortfolio;
            }
        } catch (Exception e) {
            return new Portfolio().toBuilder()
                    .userId(order.getUserId())
                    .stockId(order.getStockId())
                    .quantity(boughtQuantity)
                    .avgPurchasePrice(purchasePrice)
                    .build();
        }
    }

    private BigDecimal calculateAvgPrice(Portfolio oldPortfolio, int newTotalQuantity, BigDecimal purchasePrice) {
        BigDecimal totalPrice = purchasePrice.multiply(
                        BigDecimal.valueOf((newTotalQuantity - oldPortfolio.getQuantity())))
                .add(oldPortfolio.getAvgPurchasePrice().multiply(
                        BigDecimal.valueOf(oldPortfolio.getQuantity())));
        return totalPrice.divide(BigDecimal.valueOf(newTotalQuantity), 2,
                RoundingMode.HALF_UP);
    }

    private void handleMarketBuyOrder(Order order) {
        // update order status to complete
        order.setStatus(EOrderStatus.COMPLETED);

        Quote latestQuote = quoteRepository.findLatestQuote(order.getStockId());
        log.info("Latest Quote: " + latestQuote);
        BigDecimal purchasePrice = latestQuote.getPrice();
        order.setPrice(purchasePrice);

        // update changes into database
        log.info("Order update: " + orderRepository.save(order));
        log.info("New Quote: " + quoteRepository.save(createNewQuote(latestQuote, order)));
        log.info("New Portfolio: " + portfolioRepository.save(
                createNewPortfolio(order, purchasePrice, 0, order.getQuantity())));
    }

    private void handleLimitBuyOrder(Order order) {
        List<Order> sellPendingOrders = orderRepository.findAllByDirectionAndStatusOrderByPriceAsc(
                EOrderDirection.SELL,
                EOrderStatus.PENDING);
        for (Order sellOrder : sellPendingOrders) {
            if (sellOrder.getPrice().compareTo(order.getPrice()) > 0)
                return;
            else if (sellOrder.getPrice().compareTo(
                    order.getPrice()) <= 0 && sellOrder.getQuantity() >= order.getQuantity()) {
                order.setStatus(EOrderStatus.COMPLETED);

                int reamingQuantity = sellOrder.getQuantity() - order.getQuantity();
                if (reamingQuantity == 0)
                    sellOrder.setStatus(EOrderStatus.COMPLETED);
                else {
                    sellOrder.setQuantity(reamingQuantity);
                }
                BigDecimal purchasePrice = sellOrder.getPrice();
                Portfolio buyerPortfolio = createNewPortfolio(order, purchasePrice, 0,
                        order.getQuantity());
                Portfolio sellerPortfolio = createNewPortfolio(sellOrder, purchasePrice,
                        order.getQuantity(), 0);

                portfolioRepository.save(buyerPortfolio);
                if (sellerPortfolio.getQuantity() > 0) {
                    portfolioRepository.save(sellerPortfolio);
                } else {
                    portfolioRepository.delete(sellerPortfolio);
                }
                quoteRepository.save(
                        createNewQuote(quoteRepository.findLatestQuote(order.getStockId()), order));
                orderRepository.save(order);
                orderRepository.save(sellOrder);
                return;
            }
        }
    }

    private void handleMarketSellOrder(Order order) {
        order.setStatus(EOrderStatus.COMPLETED);

        Quote latestQuote = quoteRepository.findLatestQuote(order.getStockId());
        order.setPrice(latestQuote.getPrice());

        // update changes into database
        orderRepository.save(order);
        quoteRepository.save(createNewQuote(latestQuote, order));
        Portfolio newPortfolio = createNewPortfolio(order, latestQuote.getPrice(),
                order.getQuantity(), 0);
        if (newPortfolio.getQuantity() > 0)
            portfolioRepository.save(newPortfolio);
        else
            portfolioRepository.delete(newPortfolio);
    }

    private void handleLimitSellOrder(Order order) {
        List<Order> buyPendingOrders = orderRepository.findAllByDirectionAndStatusOrderByPriceDesc(
                EOrderDirection.BUY,
                EOrderStatus.PENDING);
        for (Order buyOrder : buyPendingOrders) {
            if (buyOrder.getPrice().compareTo(order.getPrice()) < 0)
                return;
            else if (buyOrder.getPrice().compareTo(
                    order.getPrice()) >= 0 && buyOrder.getQuantity() >= order.getQuantity()) {
                order.setStatus(EOrderStatus.COMPLETED);

                int reamingQuantity = buyOrder.getQuantity() - order.getQuantity();
                if (reamingQuantity == 0)
                    buyOrder.setStatus(EOrderStatus.COMPLETED);
                else {
                    buyOrder.setQuantity(reamingQuantity);
                }
                BigDecimal purchasePrice = buyOrder.getPrice();
                Portfolio buyerPortfolio = createNewPortfolio(buyOrder, purchasePrice, 0,
                        order.getQuantity());
                Portfolio sellerPortfolio = createNewPortfolio(order, purchasePrice,
                        order.getQuantity(), 0);

                portfolioRepository.save(buyerPortfolio);
                if (sellerPortfolio.getQuantity() > 0) {
                    portfolioRepository.save(sellerPortfolio);
                } else {
                    portfolioRepository.delete(sellerPortfolio);
                }
                quoteRepository.save(
                        createNewQuote(quoteRepository.findLatestQuote(order.getStockId()), order));

                // update order

                orderRepository.save(order);
                orderRepository.save(buyOrder);
                return;
            }
        }
    }

    @Override
    public void handleBuyRequest(Order order) {
        if (order.getOrderType() == EOrderType.MARKET) {
            handleMarketBuyOrder(order);
        } else {
            handleLimitBuyOrder(order);
        }
    }

    @Override
    public void handleSellRequest(Order order) {
        if (order.getOrderType() == EOrderType.MARKET) {
            handleMarketSellOrder(order);
        } else {
            handleLimitSellOrder(order);
        }
    }
}
