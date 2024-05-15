package com.ashapiro.auction.advice;

import com.ashapiro.auction.exceptions.address.AddressNotFoundException;
import com.ashapiro.auction.exceptions.auction.AuctionNotFoundException;
import com.ashapiro.auction.exceptions.auction.AuctionStatusNotFoundException;
import com.ashapiro.auction.exceptions.bid.BidNotFoundException;
import com.ashapiro.auction.exceptions.payment.PaymentStatusNotFoundException;
import com.ashapiro.auction.exceptions.product.ProductNotFoundException;
import com.ashapiro.auction.exceptions.product.ProductStatusNotFoundException;
import com.ashapiro.auction.exceptions.role.RoleNotFoundException;
import com.ashapiro.auction.exceptions.user.DuplicateUserException;
import com.ashapiro.auction.exceptions.user.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(value = {
            PaymentStatusNotFoundException.class,
            AuctionStatusNotFoundException.class,
            ProductStatusNotFoundException.class
    })
    public ModelAndView handleStatusNotFound(PaymentStatusNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("/error/errorPage");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = {
            AddressNotFoundException.class,
            AuctionNotFoundException.class,
            BidNotFoundException.class,
            ProductNotFoundException.class,
            RoleNotFoundException.class,
            UserNotFoundException.class
    })
    public ModelAndView handleNotFound(RuntimeException e) {
        ModelAndView modelAndView = new ModelAndView("/error/errorPage");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ModelAndView handleDuplicateUser(DuplicateUserException e) {
        ModelAndView modelAndView = new ModelAndView("/error/errorPage");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }
}
