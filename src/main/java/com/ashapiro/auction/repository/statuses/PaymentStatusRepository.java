package com.ashapiro.auction.repository.statuses;

import com.ashapiro.auction.entity.statuses.PaymentStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {

    @Cacheable("paymentStatuses")
    @Query("select ps from PaymentStatus ps where ps.statusName = :paymentStatus")
    Optional<PaymentStatus> getByName(String paymentStatus);
}
