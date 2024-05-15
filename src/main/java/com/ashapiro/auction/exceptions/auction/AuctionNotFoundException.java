package com.ashapiro.auction.exceptions.auction;

public class AuctionNotFoundException extends RuntimeException{
    public AuctionNotFoundException(Long id) {
        super(String.format("Auction with id: %d does not exist", id));
    }
}
