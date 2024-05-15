package com.ashapiro.auction.dto.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDetailsDto {
    Long userId;
    String name;
    BigDecimal askingPrice;
    Long quantity;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
