package com.ashapiro.auction.exceptions.auction;

public class AuctionStatusNotFoundException extends RuntimeException{

    public AuctionStatusNotFoundException(String status) {
        super(String.format("Auction %s status does not exist", status));
    }
}
