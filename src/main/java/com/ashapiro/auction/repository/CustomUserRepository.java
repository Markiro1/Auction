package com.ashapiro.auction.repository;

import com.ashapiro.auction.entity.User;

import java.util.Optional;

public interface CustomUserRepository {

    Optional<User> getUserFetchRelations(Long userId);
}
