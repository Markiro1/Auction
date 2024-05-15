package com.ashapiro.auction.dto.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionRequestDto {
    private Long productId;
    private String ownerEmail;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long timezone;
}
