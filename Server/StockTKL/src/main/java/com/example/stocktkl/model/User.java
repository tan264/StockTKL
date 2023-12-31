package com.example.stocktkl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String hashed_password;

    @Size(max =100 )
    private String fullName;

    private LocalDateTime dateOfBirth;

    private String country;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Portfolio> portfolios;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<UserDevice> userDevices;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<BankAccount> bankAccounts;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Transaction> transactions;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "watchlists",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id")
    )
    private Set<Stock> watchlistedStocks = new HashSet<>();



    public User(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.hashed_password = password;
        this.fullName = fullName;
    }

}
