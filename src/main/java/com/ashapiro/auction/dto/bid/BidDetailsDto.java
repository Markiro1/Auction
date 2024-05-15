package com.ashapiro.auction.dto.bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BidDetailsDto(Long id, BigDecimal bidSum, LocalDateTime bidTime, Long auctionId, String productName, Long customerId, String customerEmail) {
}
