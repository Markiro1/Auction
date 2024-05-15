package com.ashapiro.auction.service;

import com.ashapiro.auction.entity.statuses.ProductStatus;

public interface ProductStatusService {
    ProductStatus getOnReviewStatus();
    ProductStatus getAuctionedStatus();
    ProductStatus getSoldStatus();
    ProductStatus getAcceptedStatus();
    ProductStatus getStatusByName(String name);
}
