package com.ashapiro.auction.entity;

import com.ashapiro.auction.entity.statuses.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "won_auctions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@ToString(exclude = "auction")
public class WonAuction {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_status_id")
    private PaymentStatus status;

    @Column(name = "payment_deadline")
    private LocalDateTime paymentDeadline;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addAuction(Auction auction) {
        auction.setWonAuction(this);
        this.auction = auction;
    }
}
