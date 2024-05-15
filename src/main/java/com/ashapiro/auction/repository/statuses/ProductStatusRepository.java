package com.ashapiro.auction.repository.statuses;

import com.ashapiro.auction.entity.statuses.ProductStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductStatusRepository extends JpaRepository<ProductStatus, Long> {

    @Cacheable("productStatuses")
    @Query("select ps from ProductStatus ps where ps.statusName = :paymentStatus")
    Optional<ProductStatus> getByName(String paymentStatus);
}
