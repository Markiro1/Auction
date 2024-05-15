package com.ashapiro.auction.entity.statuses;

import com.ashapiro.auction.entity.Auction;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auction_statuses")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "status_name", unique = true)
    private String statusName;

    @OneToMany(mappedBy = "status")
    private List<Auction> auction = new ArrayList<>();

    public enum Status {
        ACTIVE,
        PAUSED,
        CLOSED
    }
}
