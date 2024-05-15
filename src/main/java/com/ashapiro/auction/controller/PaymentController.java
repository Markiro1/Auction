package com.ashapiro.auction.controller;

import com.ashapiro.auction.dto.payment.ChargeRequestDto;
import com.ashapiro.auction.service.PaymentService;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;


    @GetMapping("/user/checkout")
    public String createPayment(@RequestParam("auctionId") Long auctionId,
                                @RequestParam("priceToPaid") BigDecimal priceToPaid,
                                Model model) {
        model.addAttribute("stripe", new ChargeRequestDto());
        model.addAttribute("auctionId", auctionId);
        model.addAttribute("priceToPaid", priceToPaid.multiply(BigDecimal.valueOf(100)));
        model.addAttribute("stripePublicKey", paymentService.getPublicKey());
        return "payment/checkout";
    }


    @PostMapping("/user/charge")
    public String charge(ChargeRequestDto chargeRequestDto) {
        try {
            Charge charge = paymentService.charge(chargeRequestDto);
            if (charge.getPaid()) {
                return "payment/success";
            }
        } catch (Exception e) {
            log.error("Failed charge payment");
        }
        return "payment/failed";
    }
}
