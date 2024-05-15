package com.ashapiro.auction.dto.bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BidInfoDto(Long id, BigDecimal sum, Long customerId, Long auctionId, LocalDateTime bidTime) {}
