package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.entity.Auction;
import com.ashapiro.auction.entity.WonAuction;
import com.ashapiro.auction.entity.statuses.AuctionStatus;
import com.ashapiro.auction.entity.statuses.PaymentStatus;
import com.ashapiro.auction.repository.WonAuctionRepository;
import com.ashapiro.auction.service.AuctionStatusService;
import com.ashapiro.auction.service.PaymentStatusService;
import com.ashapiro.auction.service.WonAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WonAuctionServiceImpl implements WonAuctionService {

    private final WonAuctionRepository wonAuctionRepository;
    private final PaymentStatusService paymentStatusService;
    private final AuctionStatusService auctionStatusService;

    @Override
    @Transactional
    public void relistUnpaidAuctions() {
        LocalDateTime now = LocalDateTime.now();
        PaymentStatus unpaidPaymentStatus = paymentStatusService.getUnpaidStatus();

        List<WonAuction> expiredUnpaidAuctions =
                wonAuctionRepository.findAllWithDeadlineBeforeAndStatus(now, unpaidPaymentStatus);

        AuctionStatus auctionActiveStatus = auctionStatusService.getActiveStatus();

        for (WonAuction wonAuction : expiredUnpaidAuctions) {
            Auction auction = wonAuction.getAuction();
            auction.setStatus(auctionActiveStatus);
            auction.setStartTime(now);
            auction.setEndTime(now.plusWeeks(1));
            wonAuctionRepository.delete(wonAuction);
        }
    }
}
