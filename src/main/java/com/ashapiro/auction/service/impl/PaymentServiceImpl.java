package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.dto.payment.ChargeRequestDto;
import com.ashapiro.auction.entity.Auction;
import com.ashapiro.auction.entity.Payment;
import com.ashapiro.auction.entity.User;
import com.ashapiro.auction.entity.statuses.AuctionStatus;
import com.ashapiro.auction.entity.statuses.PaymentStatus;
import com.ashapiro.auction.entity.statuses.ProductStatus;
import com.ashapiro.auction.exceptions.auction.AuctionNotFoundException;
import com.ashapiro.auction.exceptions.user.UserNotFoundException;
import com.ashapiro.auction.repository.PaymentRepository;
import com.ashapiro.auction.service.*;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final AuctionService auctionService;
    private final ProductStatusService productStatusService;
    private final AuctionStatusService auctionStatusService;
    private final PaymentStatusService paymentStatusService;

    @Value("${stripe.api.secretKey}")
    private String stripeSecretKey;

    @Value("${stripe.api.publicKey}")
    private String stripePublicKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    @Transactional
    public Charge charge(ChargeRequestDto chargeRequestDto) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException, CardException {
        chargeRequestDto.setCurrency("USD");
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequestDto.getAmount());
        chargeParams.put("currency", chargeRequestDto.getCurrency());
        chargeParams.put("source", chargeRequestDto.getStripeToken());
        Charge charge = Charge.create(chargeParams);

        if (charge.getPaid()) {
            savePayment(chargeRequestDto);
        }
        return charge;
    }

    @Override
    public String getPublicKey() {
        return stripePublicKey;
    }

    private void savePayment(ChargeRequestDto chargeRequestDto) {
        Long userId = chargeRequestDto.getUserId();
        Long auctionId = chargeRequestDto.getAuctionId();
        BigDecimal amount = chargeRequestDto.getAmount();

        User user = userService.getById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Auction auction = auctionService.getById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(auctionId));

        Payment payment = new Payment();
        payment.addUser(user);
        payment.addAuction(auction);
        payment.setAmount(amount.divide(BigDecimal.valueOf(100)));
        paymentRepository.save(payment);

        updateStatuses(auction);
    }

    private void updateStatuses(Auction auction) {
        AuctionStatus auctionStatus = auctionStatusService.getClosedStatus();
        PaymentStatus paymentStatus = paymentStatusService.getPaidStatus();
        ProductStatus productStatus = productStatusService.getSoldStatus();

        auction.getProduct().setStatus(productStatus);
        auction.setStatus(auctionStatus);
        auction.getWonAuction().setStatus(paymentStatus);
    }
}
