package com.ashapiro.auction.exceptions.product;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id) {
        super(String.format("Product with id: %d does not exist", id));
    }
}
