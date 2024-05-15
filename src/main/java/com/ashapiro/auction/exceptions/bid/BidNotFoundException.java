package com.ashapiro.auction.exceptions.bid;

public class BidNotFoundException extends RuntimeException{

    public BidNotFoundException(Long id) {
        super(String.format("Bid with id: %d does not exist", id));
    }
}
