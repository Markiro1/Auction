package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.entity.statuses.AuctionStatus;
import com.ashapiro.auction.exceptions.auction.AuctionStatusNotFoundException;
import com.ashapiro.auction.repository.statuses.AuctionStatusRepository;
import com.ashapiro.auction.service.AuctionStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionStatusServiceImpl implements AuctionStatusService {

    private final AuctionStatusRepository auctionStatusRepository;

    @Override
    public AuctionStatus getActiveStatus() {
        return auctionStatusRepository.findById(3L)
                .orElseThrow(() -> new AuctionStatusNotFoundException("ACTIVE"));
    }

    @Override
    public AuctionStatus getPausedStatus() {
        return auctionStatusRepository.findById(1L)
                .orElseThrow(() -> new AuctionStatusNotFoundException("PAUSED"));
    }

    @Override
    public AuctionStatus getClosedStatus() {
        return auctionStatusRepository.findById(2L)
                .orElseThrow(() -> new AuctionStatusNotFoundException("CLOSED"));
    }

    @Override
    public AuctionStatus getStatusByName(String name) {
        return auctionStatusRepository.getByName(name)
                .orElseThrow(() -> new AuctionStatusNotFoundException(name));
    }
}
