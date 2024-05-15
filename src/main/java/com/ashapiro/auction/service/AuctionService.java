package com.ashapiro.auction.service;

import com.ashapiro.auction.dto.auction.*;
import com.ashapiro.auction.entity.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AuctionService {
    String add(AuctionRequestDto auctionRequestDto);

    List<AuctionDataDto> getAll();

    Page<SimpleAuctionDto> getActiveAuctions(Pageable pageable);

    AuctionDetailsDto getAuctionDetailsById(Long id);

    Optional<Auction> getById(Long id);

    BigDecimal getMaxCurrentPrice(Long auctionId);

    List<SimpleWonAuctionDto> getWonAuctions(Long userId);

    WonAuctionDto getWonAuctionById(Long auctionId);
    AuctionDto getAuction(Long auctionId);

    String deleteAuction(Long auctionId);

    String updateStatus(Long auctionId, String status);

    void updateExpiredAuctions();

    String getWonAuctionPaymentStatus(Long auctionId);
}
