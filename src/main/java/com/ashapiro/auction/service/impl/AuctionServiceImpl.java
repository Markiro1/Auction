package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.dto.auction.*;
import com.ashapiro.auction.entity.*;
import com.ashapiro.auction.entity.statuses.AuctionStatus;
import com.ashapiro.auction.entity.statuses.PaymentStatus;
import com.ashapiro.auction.entity.statuses.ProductStatus;
import com.ashapiro.auction.exceptions.auction.AuctionNotFoundException;
import com.ashapiro.auction.exceptions.product.ProductNotFoundException;
import com.ashapiro.auction.exceptions.user.UserNotFoundException;
import com.ashapiro.auction.repository.AuctionRepository;
import com.ashapiro.auction.repository.CustomUserRepository;
import com.ashapiro.auction.repository.UserRepository;
import com.ashapiro.auction.repository.WonAuctionRepository;
import com.ashapiro.auction.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;

    private final UserRepository userRepository;

    private final ProductService productService;

    private final WonAuctionRepository wonAuctionRepository;

    private final AuctionStatusService auctionStatusService;

    private final ProductStatusService productStatusService;

    private final PaymentStatusService paymentStatusService;

    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public String add(AuctionRequestDto auctionRequestDto) {
        String email = auctionRequestDto.getOwnerEmail();
        Long productId = auctionRequestDto.getProductId();

        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        Product product = productService.getProductByIdAndUserId(productId, user.getId())
                .orElseThrow(() -> new ProductNotFoundException(productId));

        createAuction(product, auctionRequestDto);
        return "Auction successfully added";
    }

    @Transactional
    @Override
    public String updateStatus(Long auctionId, String status) {
        AuctionStatus auctionStatus = auctionStatusService.getStatusByName(status);
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(auctionId));
        auction.setStatus(auctionStatus);
        return "Auction status updated successfully";
    }

    @Transactional
    @Override
    public String deleteAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(auctionId));
        try {
            auctionRepository.delete(auction);
            return "Auction deleted successfully";
        } catch (Exception e) {
            return String.format("Error deleting auction with id: %d", auctionId);
        }
    }

    @Transactional
    @Override
    public void updateExpiredAuctions() {
        AuctionStatus activeAuctionStatus = auctionStatusService.getActiveStatus();
        AuctionStatus pausedAuctionStatus = auctionStatusService.getPausedStatus();

        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        List<Auction> expiredAuctions = auctionRepository.findAllExpiredAuctions(activeAuctionStatus, now);

        for (Auction auction : expiredAuctions) {
            auction.setStatus(pausedAuctionStatus);

            User lastBidder = getLastBidder(auction);

            if (lastBidder != null) {
                PaymentStatus paymentStatus = paymentStatusService.getUnpaidStatus();

                WonAuction wonAuction = WonAuction.builder()
                        .auction(auction)
                        .user(lastBidder)
                        .status(paymentStatus)
                        .paymentDeadline(LocalDateTime.now().plusDays(3))
                        .build();
                wonAuction.addAuction(auction);
                wonAuctionRepository.save(wonAuction);
            }
        }
    }

    @Override
    public Optional<Auction> getById(Long id) {
        return auctionRepository.findById(id);
    }

    @Override
    public List<AuctionDataDto> getAll() {
        return auctionRepository.findAllAuctions();
    }

    @Override
    public Page<SimpleAuctionDto> getActiveAuctions(Pageable pageable) {
        AuctionStatus activeStatus = auctionStatusService.getActiveStatus();
        return auctionRepository.findAllByStatus(activeStatus, pageable);
    }

    @Override
    public AuctionDetailsDto getAuctionDetailsById(Long auctionId) {
        AuctionDetailsDto auctionDetailsDto = auctionRepository.getAuctionDetailsById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(auctionId));
        return auctionDetailsDto;
    }

    @Override
    public BigDecimal getMaxCurrentPrice(Long auctionId) {
        return auctionRepository.getMaxCurrentPriceByActionId(auctionId);
    }

    @Override
    public List<SimpleWonAuctionDto> getWonAuctions(Long userId) {
        return wonAuctionRepository.findWonAuctionsIdByUserId(userId);
    }

    @Override
    public WonAuctionDto getWonAuctionById(Long auctionId) {
        return wonAuctionRepository.findWonActionById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(auctionId));
    }

    @Override
    public AuctionDto getAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(auctionId));
        return modelMapper.map(auction, AuctionDto.class);
    }

    @Override
    public String getWonAuctionPaymentStatus(Long auctionId) {
        return auctionRepository.getWonAuctionPaymentStatus(auctionId);
    }

    private User getLastBidder(Auction auction) {
        return auction.getBids().stream()
                .max(Comparator.comparing(Bid::getBidTime))
                .map(Bid::getCustomer)
                .orElse(null);
    }

    private void createAuction(Product product, AuctionRequestDto auctionRequestDto) {
        AuctionStatus auctionStatus = auctionStatusService.getActiveStatus();
        ProductStatus productStatus = productStatusService.getAuctionedStatus();
        long timezone = auctionRequestDto.getTimezone();

        LocalDateTime startTime = auctionRequestDto.getStartTime().minusHours(timezone);
        LocalDateTime endTime = auctionRequestDto.getEndTime().minusHours(timezone);

        Auction auction = new Auction();
        auction.addProduct(product);
        auction.setCurrentPrice(product.getAskingPrice());
        auction.setStatus(auctionStatus);
        auction.setStartTime(startTime);
        auction.setEndTime(endTime);
        product.setStatus(productStatus);
        auctionRepository.save(auction);
    }
}
