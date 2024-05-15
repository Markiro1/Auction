package com.ashapiro.auction.entity;

import com.ashapiro.auction.entity.statuses.ProductStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false, name = "asking_price")
    private BigDecimal askingPrice;

    @ManyToOne
    @JoinColumn(name = "product_status_id")
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Auction> auctions = new ArrayList<>();

    public void addOwner(User owner) {
        this.owner = owner;
        owner.getProducts().add(this);
    }
}
