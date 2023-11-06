package com.example.stocktkl.model;

import com.example.stocktkl.model.enum_class.EOrderType;
import com.example.stocktkl.model.enum_class.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EOrderType orderType;

    @NotBlank
    private Integer quantity;

    @NotBlank
    @Column(precision = 10, scale = 2)
    @DecimalMin("0.00")
    private BigDecimal orderPrice;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EOrderType status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
