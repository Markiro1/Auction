package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.entity.statuses.ProductStatus;
import com.ashapiro.auction.exceptions.product.ProductStatusNotFoundException;
import com.ashapiro.auction.repository.statuses.ProductStatusRepository;
import com.ashapiro.auction.service.ProductStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductStatusServiceImpl implements ProductStatusService {

    private final ProductStatusRepository productStatusRepository;

    @Override
    public ProductStatus getOnReviewStatus() {
        return productStatusRepository.findById(1L)
                .orElseThrow(() -> new ProductStatusNotFoundException("ON_REVIEW"));
    }

    @Override
    public ProductStatus getAuctionedStatus() {
        return productStatusRepository.findById(2L)
                .orElseThrow(() -> new ProductStatusNotFoundException("AUCTIONED"));
    }

    @Override
    public ProductStatus getSoldStatus() {
        return productStatusRepository.findById(3L)
                .orElseThrow(() -> new ProductStatusNotFoundException("SOLD"));
    }

    @Override
    public ProductStatus getAcceptedStatus() {
        return productStatusRepository.findById(4L)
                .orElseThrow(() -> new ProductStatusNotFoundException("ACCEPTED"));
    }

    @Override
    public ProductStatus getStatusByName(String name) {
        return productStatusRepository.getByName(name)
                .orElseThrow(() -> new ProductStatusNotFoundException(name));
    }
}
