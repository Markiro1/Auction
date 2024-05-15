package com.ashapiro.auction.repository.statuses;

import com.ashapiro.auction.entity.statuses.AuctionStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuctionStatusRepository extends JpaRepository<AuctionStatus, Long> {

    @Cacheable("auctionStatuses")
    @Query("select a from AuctionStatus a where a.statusName = :auctionStatus")
    Optional<AuctionStatus> getByName(String auctionStatus);
}
