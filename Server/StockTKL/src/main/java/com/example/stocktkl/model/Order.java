package com.example.stocktkl.model;

import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.model.enum_class.EOrderStatus;
import com.example.stocktkl.model.enum_class.EOrderType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "stock"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "stock_id")
    private Long stockId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EOrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private EOrderDirection direction;

    private Integer quantity;

    @Column(precision = 10, scale = 2)
    @DecimalMin("0.00")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EOrderStatus status = EOrderStatus.PENDING;

    private LocalDateTime orderDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    // change from JsonBackReference to JsonIgnore to avoid error when receive message from queue
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", insertable = false, updatable = false)
    @JsonIgnore
    private Stock stock;
}
