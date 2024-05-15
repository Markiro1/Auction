package com.ashapiro.auction.exceptions.address;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(Long id) {
        super(String.format("Address with id: %d does not exist", id));
    }
}
