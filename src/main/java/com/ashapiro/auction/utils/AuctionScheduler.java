package com.ashapiro.auction.utils;

import com.ashapiro.auction.service.AuctionService;
import com.ashapiro.auction.service.WonAuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuctionScheduler {

    private final AuctionService auctionService;
    private final WonAuctionService wonAuctionService;

/*
    @Scheduled(fixedRate = 60000)
    public void checkAndUpdateAuctionStatus() {
        log.info("Check and update auction status.");
        auctionService.updateExpiredAuctions();
    }
*/

    @Scheduled(fixedRate = 60000)
    public void checkAndReopenExpiredWonAuctions() {
        log.info("Check and reopen expired auctions.");
        wonAuctionService.relistUnpaidAuctions();
    }
}
