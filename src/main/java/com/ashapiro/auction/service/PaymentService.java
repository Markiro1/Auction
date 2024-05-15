package com.ashapiro.auction.service;

import com.ashapiro.auction.dto.payment.ChargeRequestDto;
import com.stripe.exception.*;
import com.stripe.model.Charge;

public interface PaymentService {

   Charge charge(ChargeRequestDto chargeRequestDto) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException, CardException;

   String getPublicKey();
}
