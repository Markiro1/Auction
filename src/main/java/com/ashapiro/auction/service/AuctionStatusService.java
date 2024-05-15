package com.ashapiro.auction.service;

import com.ashapiro.auction.entity.statuses.AuctionStatus;

public interface AuctionStatusService {
    AuctionStatus getActiveStatus();
    AuctionStatus getPausedStatus();
    AuctionStatus getClosedStatus();
    AuctionStatus getStatusByName(String name);
}
