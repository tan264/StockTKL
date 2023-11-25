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

    private Quote createNewQuote(Order order) {
        Quote quote;
        try {
            Quote latestQuote = quoteRepository.findTheNewestQuote(
                    order.getStockId()).orElseThrow(RuntimeException::new);
            quote = Quote.builder()
                    .stockId(order.getStockId())
                    .price(calculateNewPrice(order, latestQuote))
                    .changeValue(latestQuote.getChangeValue())
                    .percentChange(latestQuote.getPercentChange())
                    .volume(latestQuote.getVolume() + order.getQuantity())
                    .build();
        } catch (Exception e) {
            quote = Quote.builder()
                    .stockId(order.getStockId())
                    .price(order.getPrice())
                    .changeValue(BigDecimal.valueOf(0))
                    .percentChange(BigDecimal.valueOf(0))
                    .volume(order.getQuantity())
                    .build();
        }

        return quote;
    }

    private Portfolio updatePortfolioForBuyOrder(Order order) {
        try {
            Portfolio portfolio = portfolioRepository.findByStockId(order.getStockId()).orElseThrow(
                    RuntimeException::new);
            portfolio.setQuantity(portfolio.getQuantity() + order.getQuantity());
            return portfolio;
        } catch (Exception e) {
            return Portfolio.builder()
                    .userId(order.getUserId())
                    .stockId(order.getStockId())
                    .quantity(order.getQuantity())
                    .build();
        }
    }

    private Portfolio updatePortfolioForSellOrder(Order order) {
        try {
            Portfolio portfolio = portfolioRepository.findByStockId(order.getStockId()).orElseThrow(
                    RuntimeException::new);
            int quantity = portfolio.getQuantity() - order.getQuantity();
            if (quantity > 0) {
                return portfolio;
            } else {
                portfolioRepository.delete(portfolio);
                return null;
            }
        } catch (Exception e) {
            return Portfolio.builder()
                    .userId(order.getUserId())
                    .stockId(order.getStockId())
                    .quantity(order.getQuantity())
                    .build();
        }
    }

    @Override
    public void handleBuyRequest(Order order) {
        if (order.getOrderType() == EOrderType.MARKET) {
            // update order status to complete and save to db
            order.setStatus(EOrderStatus.COMPLETED);
            orderRepository.save(order);

            // add new quote to db
            quoteRepository.save(createNewQuote(order));

            // add new/update portfolio to db
            portfolioRepository.save(updatePortfolioForBuyOrder(order));
        } else {
            // TODO: 25/11/2023 Handle limit order
            List<Order> orders = orderRepository.findAllByDirectionAndStatus(EOrderDirection.SELL,
                    EOrderStatus.PENDING);
        }
    }

    @Override
    public void handleSellRequest(Order order) {
        if (order.getOrderType() == EOrderType.MARKET) {
            // update order status to complete and save to db
            order.setStatus(EOrderStatus.COMPLETED);
            orderRepository.save(order);

            // add new quote to db
            quoteRepository.save(createNewQuote(order));

            // add new/update portfolio to db
            Portfolio portfolio = updatePortfolioForSellOrder(order);
            if (portfolio != null) {
                portfolioRepository.save(portfolio);
            }
        } else {
            // TODO: 25/11/2023 Handle limit order
            List<Order> orders = orderRepository.findAllByDirectionAndStatus(EOrderDirection.BUY,
                    EOrderStatus.PENDING);
        }
    }
}
