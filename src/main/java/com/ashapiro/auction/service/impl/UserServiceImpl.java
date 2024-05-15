package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.dto.user.*;
import com.ashapiro.auction.entity.Role;
import com.ashapiro.auction.entity.User;
import com.ashapiro.auction.exceptions.user.DuplicateUserException;
import com.ashapiro.auction.exceptions.user.UserNotFoundException;
import com.ashapiro.auction.repository.CustomUserRepository;
import com.ashapiro.auction.repository.UserRepository;
import com.ashapiro.auction.service.RoleService;
import com.ashapiro.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final CustomUserRepository customUserRepository;

    @Transactional
    @Override
    public void save(UserRegistrationDto request) {
        validateEmail(request.getEmail());
        User user = createUserFromRequest(request);
        addUserRole(user);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public String saveByAdmin(AddUserDto userDto) {
        String email = userDto.getEmail();
        String roleName = userDto.getRoleName();
        validateEmail(email);
        User user = createUserFromRequest(userDto);
        Role role = roleService.findByName(roleName);
        user.addRole(role);
        userRepository.save(user);
        return "User added successfully";
    }

    @Transactional
    @Override
    public String deleteUserById(Long userId) {
        User user = customUserRepository.getUserFetchRelations(userId)
                        .orElseThrow(() -> new UserNotFoundException(userId));
        try {
            userRepository.delete(user);
            return "User deleted successfully";
        } catch (Exception e) {
            return String.format("Error deleting user with id: %d", userId);
        }
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserProfileDto getUserProfileByUserId(Long userId) {
        return userRepository.getUserProfileByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public List<String> getAllUsersEmail() {
        return userRepository.getAllEmails();
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.getAllUsers();
    }

    private void addUserRole(User user) {
        Role role = roleService.findByName("ROLE_USER");
        user.addRole(role);
    }

    private User createUserFromRequest(UserRegistrationDto request) {
        User user = convertToUserFromRequest(request);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return user;
    }

    private User createUserFromRequest(AddUserDto request) {
        User user = convertToUserFromRequest(request);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return user;
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateUserException(email);
        }
    }

    private User convertToUserFromRequest(UserRegistrationDto userRequestDto) {
        return modelMapper.map(userRequestDto, User.class);
    }

    private User convertToUserFromRequest(AddUserDto addUserDto) {
        return modelMapper.map(addUserDto, User.class);
    }
}
