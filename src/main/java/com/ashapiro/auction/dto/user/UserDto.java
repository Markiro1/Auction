package com.ashapiro.auction.dto.user;

import java.time.LocalDateTime;

public record UserDto(Long id, String email, String firstName, String lastName, String phoneNumber, String roleName, LocalDateTime createdAt) {
}
