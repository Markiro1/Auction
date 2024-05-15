package com.ashapiro.auction.utils;

import com.ashapiro.auction.service.BidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BidScheduler {

    private final BidService bidService;

    @Scheduled(fixedRate = 120000)
    public void clearBids() {
        log.info("Clearing bids.");
        bidService.clearBids();
    }
}
