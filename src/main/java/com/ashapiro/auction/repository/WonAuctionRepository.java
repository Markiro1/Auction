package com.ashapiro.auction.repository;

import com.ashapiro.auction.dto.auction.SimpleWonAuctionDto;
import com.ashapiro.auction.dto.auction.WonAuctionDto;
import com.ashapiro.auction.entity.statuses.PaymentStatus;
import com.ashapiro.auction.entity.WonAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface WonAuctionRepository extends JpaRepository<WonAuction, Long> {

    @Query("select new com.ashapiro.auction.dto.auction.SimpleWonAuctionDto(wa.id) " +
            "from WonAuction wa " +
            "where wa.user.id = :userId")
    List<SimpleWonAuctionDto> findWonAuctionsIdByUserId(Long userId);

    @Query("select new com.ashapiro.auction.dto.auction.WonAuctionDto(" +
            "wa.auction.id," +
            "a.currentPrice," +
            "p.name," +
            "wa.paymentDeadline) " +
            "from Auction a " +
            "join Product p on p.id = a.product.id " +
            "join WonAuction wa on a.id = wa.id " +
            "where a.id = :wonAuctionId")
    Optional<WonAuctionDto> findWonActionById(Long wonAuctionId);

    @Query("select wa from WonAuction wa where wa.paymentDeadline < :date and wa.status = :paymentStatus")
    List<WonAuction> findAllWithDeadlineBeforeAndStatus(LocalDateTime date, PaymentStatus paymentStatus);
}
