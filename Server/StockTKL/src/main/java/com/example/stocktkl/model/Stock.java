package com.example.stocktkl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long stockId;

    @NotBlank
    @Column(length = 10, nullable = false)
    private String symbol;

    @NotBlank
    @Column(length = 100)
    private String companyName;

    private String industry;

    private String sector;

    @OneToMany(mappedBy = "stock")
    @JsonBackReference
    private List<Portfolio> portfolios;

    @OneToMany(mappedBy = "stock")
    @JsonBackReference
    private List<Quote> quotes;

    @OneToMany(mappedBy = "stock")
    @JsonBackReference
    private List<Order> orders;

//    @ManyToMany(mappedBy = "watchlistedStocks",fetch = FetchType.EAGER)
//    private Set<User> users = new HashSet<>();

}
