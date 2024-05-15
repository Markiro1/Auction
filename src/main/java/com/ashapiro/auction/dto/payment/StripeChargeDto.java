package com.ashapiro.auction.dto.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StripeChargeDto {
    private Long userId;
    private Long auctionId;
    private String stripeToken;
    private BigDecimal amount;
    private Boolean success;
    private String message;
    private String chargeId;
}
