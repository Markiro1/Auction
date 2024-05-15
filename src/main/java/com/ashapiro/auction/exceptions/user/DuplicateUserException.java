package com.ashapiro.auction.exceptions.user;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String email) {
        super(String.format("User with email: %s already exists", email));
    }
}
