package com.ashapiro.auction.dto.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDataDto {
    private Long id;
    private String name;
    private Long productId;
    private String ownerEmail;
    private Long ownerId;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
