package com.example.stocktkl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="quotes")
@ToString(exclude = {"stock"})
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quoteId;

    @Column(name = "stock_id")
    private Long stockId;

    @Column(precision = 10, scale = 4)
    @DecimalMin("0.00")
    private BigDecimal price;

    @Column(precision = 10, scale = 4)
    private BigDecimal changeValue;

    @Column(precision = 10, scale = 4)
    private BigDecimal percentChange;

    private Integer volume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", insertable = false, updatable = false)
    @JsonBackReference
    private Stock stock;

//    @CreatedDate
    private LocalDateTime timeStamp = LocalDateTime.now();

}
