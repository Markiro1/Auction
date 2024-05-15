package com.ashapiro.auction.service;

import com.ashapiro.auction.entity.statuses.PaymentStatus;

public interface PaymentStatusService {
    PaymentStatus getPaidStatus();
    PaymentStatus getUnpaidStatus();
    PaymentStatus getStatusByName(String name);
}
