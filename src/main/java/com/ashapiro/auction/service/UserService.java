package com.ashapiro.auction.service;

import com.ashapiro.auction.dto.user.*;
import com.ashapiro.auction.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(UserRegistrationDto userDto);

    String saveByAdmin(AddUserDto userDto);

    Optional<User> findByEmail(String email);

    Optional<User> getById(Long id);

    UserProfileDto getUserProfileByUserId(Long userId);

    List<String> getAllUsersEmail();

    List<UserDto> getAll();

    String deleteUserById(Long userId);
}
