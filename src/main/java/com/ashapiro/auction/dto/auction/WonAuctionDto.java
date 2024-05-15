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
public class WonAuctionDto {
    private Long id;
    private BigDecimal priceToPaid;
    private String name;
    private LocalDateTime paymentDeadline;
}
