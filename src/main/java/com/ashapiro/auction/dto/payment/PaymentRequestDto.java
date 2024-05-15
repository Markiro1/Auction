package com.ashapiro.auction.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentRequestDto {
    private String cardNumber;
    private String expiryDate;
    private String cvc;
    private BigDecimal amount;
    private Long userId;
    private Long auctionId;
}
