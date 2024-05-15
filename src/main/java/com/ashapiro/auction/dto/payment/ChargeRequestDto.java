package com.ashapiro.auction.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChargeRequestDto {
    private BigDecimal amount;
    private String currency;
    private String stripeToken;
    private Long userId;
    private Long auctionId;
}
