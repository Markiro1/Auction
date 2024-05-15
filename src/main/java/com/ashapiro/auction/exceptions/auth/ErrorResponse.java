package com.ashapiro.auction.exceptions.auth;

import java.util.Date;

public record ErrorResponse(int status, String message, Date timestamp) {
    public ErrorResponse(int status, String message) {
        this(status, message, new Date());
    }
}
