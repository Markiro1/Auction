package com.ashapiro.auction.exceptions.user;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String email) {
        super(String.format("User with email: %s does not exist", email));
    }

    public UserNotFoundException(Long id) {
        super(String.format("User with id: %d does not exist", id));
    }
}
