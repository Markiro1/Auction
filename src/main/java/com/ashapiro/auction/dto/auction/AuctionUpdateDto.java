package com.ashapiro.auction.dto.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionUpdateDto {
    private Long countOfBids;
    private BigDecimal currentPrice;
}
