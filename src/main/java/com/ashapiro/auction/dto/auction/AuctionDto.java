package com.ashapiro.auction.dto.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDto {
    private Long id;
    private BigDecimal currentPrice;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private String status;
    private Long productId;
}
