package com.ashapiro.auction.entity;

import com.ashapiro.auction.entity.statuses.AuctionStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auctions")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auction_status_id")
    private AuctionStatus status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.REMOVE)
    private List<Bid> bids = new ArrayList<>();

    @OneToOne(mappedBy = "auction", cascade = CascadeType.REMOVE)
    private WonAuction wonAuction;

    @OneToOne(mappedBy = "auction")
    private Payment payment;

    @Column(nullable = false, name = "current_price")
    private BigDecimal currentPrice = BigDecimal.ZERO;

    @Column(nullable = false, name = "start_time")
    private LocalDateTime startTime;

    @Column(nullable = false, name = "end_time")
    private LocalDateTime endTime;

    public void addProduct(Product product) {
        this.product = product;
        product.getAuctions().add(this);
    }

    public void setPayment(Payment payment) {
        payment.setAuction(this);
        this.payment = payment;
    }
}
