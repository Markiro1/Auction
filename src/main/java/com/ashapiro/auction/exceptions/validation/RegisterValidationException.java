package com.ashapiro.auction.exceptions.validation;

public class RegisterValidationException extends RuntimeException{
    public RegisterValidationException() {
        super("Make sure you spell your email correctly, names do not have numbers,\n" +
                "and your password has no specific characters");
    }
}
