package com.ashapiro.auction.repository;

import com.ashapiro.auction.dto.auction.AuctionDataDto;
import com.ashapiro.auction.dto.auction.AuctionDetailsDto;
import com.ashapiro.auction.dto.auction.SimpleAuctionDto;
import com.ashapiro.auction.entity.Auction;
import com.ashapiro.auction.entity.statuses.AuctionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository  extends JpaRepository<Auction, Long> {

    @Query("select new com.ashapiro.auction.dto.auction.AuctionDataDto(" +
            "a.id, " +
            "p.name, " +
            "p.id, " +
            "u.email, " +
            "u.id, " +
            "a.status.statusName," +
            "a.startTime as start_time," +
            "a.endTime as end_time" +
            ") from Auction a " +
            "left join a.product p " +
            "left join p.owner u")
    List<AuctionDataDto> findAllAuctions();

    @Query("select new com.ashapiro.auction.dto.auction.SimpleAuctionDto(" +
            "a.id, " +
            "p.name) from Auction a " +
            "join Product p on p.id = a.product.id " +
            "where a.status = :auctionStatus")
    Page<SimpleAuctionDto> findAllByStatus(AuctionStatus auctionStatus, Pageable pageable);

    @Query("select new com.ashapiro.auction.dto.auction.AuctionDetailsDto(" +
            "p.owner.id, " +
            "p.name, " +
            "p.askingPrice, " +
            "p.quantity," +
            "p.description," +
            "a.startTime as start_time," +
            "a.endTime as end_time" +
            ") from Auction a " +
            "join Product p on p.id = a.product.id " +
            "where a.id = :id")
    Optional<AuctionDetailsDto> getAuctionDetailsById(Long id);

    @Query("select coalesce(max(a.currentPrice), 0) " +
            "from Auction a " +
            "where a.id = :auctionId")
    BigDecimal getMaxCurrentPriceByActionId(Long auctionId);

    @Query("select a from Auction a " +
            "left join fetch a.product " +
            "left join fetch a.wonAuction " +
            "left join fetch a.payment " +
            "left join fetch a.bids " +
            "where a.status = :status and a.endTime < :now")
    List<Auction> findAllExpiredAuctions(AuctionStatus status, LocalDateTime now);

    @Query("select wa.status.statusName from WonAuction wa where wa.id = :auctionId")
    String getWonAuctionPaymentStatus(Long auctionId);
}
