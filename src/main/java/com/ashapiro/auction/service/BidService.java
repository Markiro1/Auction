package com.ashapiro.auction.service;

import com.ashapiro.auction.dto.bid.BidDetailsDto;
import com.ashapiro.auction.dto.bid.BidDto;
import com.ashapiro.auction.dto.bid.BidInfoDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BidService {
    Long getCountOfBidsByAuctionId(Long auctionId);

    void save(BidDto bidDto);

    List<BidDetailsDto> getAllBidDetails();

    BidInfoDto getBidInfo(Long bidId);

    String deleteBid(Long bidId);

    void clearBids();
}
