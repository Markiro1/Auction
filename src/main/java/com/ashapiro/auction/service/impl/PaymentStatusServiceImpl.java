package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.entity.statuses.PaymentStatus;
import com.ashapiro.auction.exceptions.payment.PaymentStatusNotFoundException;
import com.ashapiro.auction.repository.statuses.PaymentStatusRepository;
import com.ashapiro.auction.service.PaymentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentStatusServiceImpl implements PaymentStatusService {

    private final PaymentStatusRepository paymentStatusRepository;

    @Override
    public PaymentStatus getPaidStatus() {
        return paymentStatusRepository.findById(1L)
                .orElseThrow(() -> new PaymentStatusNotFoundException("PAID"));
    }

    @Override
    public PaymentStatus getUnpaidStatus() {
        return paymentStatusRepository.findById(2L)
                .orElseThrow(() -> new PaymentStatusNotFoundException("UNPAID"));
    }

    @Override
    public PaymentStatus getStatusByName(String name) {
        return paymentStatusRepository.getByName(name)
                .orElseThrow(() -> new PaymentStatusNotFoundException(name));
    }
}
