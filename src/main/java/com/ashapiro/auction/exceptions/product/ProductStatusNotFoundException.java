package com.ashapiro.auction.exceptions.product;

public class ProductStatusNotFoundException extends RuntimeException{
    public ProductStatusNotFoundException(String status) {
        super(String.format("Product %s status does not exist", status));
    }
}
