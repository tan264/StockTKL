//package com.example.stocktkl.service.impl;
//
//import com.example.stocktkl.model.Order;
//import com.example.stocktkl.model.enum_class.EOrderStatus;
//import com.example.stocktkl.model.enum_class.EOrderType;
//import com.example.stocktkl.payload.SellStatusMessage;
//import com.example.stocktkl.payload.request.LimitBuyRequest;
//import com.example.stocktkl.payload.request.MarketBuyRequest;
//import com.example.stocktkl.payload.request.SellRequest;
//import com.example.stocktkl.payload.response.OrderResponse;
//import com.example.stocktkl.repository.OrderRepository;
//import com.example.stocktkl.service.OrderService;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Service
//public class OrderServiceImpl implements OrderService {
//
//    @Value("${rabbitmq.exchange.name}")
//    private String exchange;
//
//    @Value("${rabbitmq.buyLimitRouting.key}")
//    private String buyLimitRoutingKey;
//
//    @Value("${rabbitmq.sellRouting.key}")
//    private String sellRoutingKey;
//
//    private final  RabbitTemplate rabbitTemplate;
//
//    private final OrderRepository orderRepository;
//
//    public OrderServiceImpl(RabbitTemplate rabbitTemplate, OrderRepository orderRepository ) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.orderRepository = orderRepository;
//    }
//    @Override
//    public BigDecimal getMarketPrice(String symbol) {
//        return orderRepository.getPrice(symbol) ;
//    }
//    @Override
//    public void processLimitBuy(LimitBuyRequest request) {
//        BigDecimal marketPrice = getMarketPrice(request.getSymbol());
////        // Xác định nếu giá thị trường thấp hơn giá mua LIMIT
////        if (marketPrice <= request.getPrice()) {
////            // Mua ngay với giá thị trường
////            executeMarketBuy(request);
////        } else {
////            // Xử lý khớp lệnh với các lệnh bán ở trạng thái Pending
////            // ...
////
////            // Nếu không có lệnh nào khớp, đưa vào con thỏ
////            // ...
////        }
//    }
//
//
//
//    @Override
//    public OrderResponse executeMarketBuy(MarketBuyRequest request) {
//        // Thực hiện lệnh mua ngay lập tức với giá thị trường
//        BigDecimal marketPrice = getMarketPrice(request.getSymbol());
//        BigDecimal quantity = new BigDecimal(request.getQuantity());
//
//        Order order = new Order();
//        order.setOrderType(EOrderType.BUY_MARKET);
//        order.setOrderDate(LocalDateTime.now());
//        order.setQuantity(request.getQuantity());
//        order.setPrice(quantity.multiply(marketPrice));
//        order.setUser();
//
//        // Sau khi thực hiện, cập nhật trạng thái của đơn đặt hàng
//
//
//    }
//
//    @Override
//    public void processSell(SellRequest request) {
//        SellStatusMessage sellStatusMessage = new SellStatusMessage(request, EOrderStatus.PENDING);
//        rabbitTemplate.convertAndSend(exchange,sellRoutingKey,sellStatusMessage);
//    }
//}
