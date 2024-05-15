package com.ashapiro.auction.exceptions.role;

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String roleName) {
        super(String.format("Role %s does not exist", roleName));
    }
}
