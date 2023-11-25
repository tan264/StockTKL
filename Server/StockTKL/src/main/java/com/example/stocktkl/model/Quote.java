package com.example.stocktkl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="quotes")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quoteId;

    @Column(name = "stock_id")
    private Long stockId;

    @NotBlank
    @Column(precision = 10, scale = 4)
    @DecimalMin("0.00")
    private BigDecimal price;

    @NotBlank
    @Column(precision = 10, scale = 4)
    @DecimalMin("0.00")
    private BigDecimal changeValue;

    @NotBlank
    @Column(precision = 10, scale = 4)
    @DecimalMin("0.00")
    private BigDecimal percentChange;

    @NotBlank
    private Integer volume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", insertable = false, updatable = false)
    @JsonBackReference
    private Stock stock;

//    @CreatedDate
    private LocalDateTime timeStamp = LocalDateTime.now();

}
