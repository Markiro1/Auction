package com.ashapiro.auction.exceptions.payment;

public class PaymentStatusNotFoundException extends RuntimeException{

    public PaymentStatusNotFoundException(String status) {
        super(String.format("Payment %s status does not exist", status));
    }
}
