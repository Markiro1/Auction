package com.ashapiro.auction.repository;

import com.ashapiro.auction.dto.user.UserDto;
import com.ashapiro.auction.dto.user.UserProfileDto;
import com.ashapiro.auction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u left join fetch u.role where u.email = :email")
    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select new com.ashapiro.auction.dto.user.UserProfileDto(" +
            "u.firstName," +
            "u.lastName," +
            "u.email," +
            "u.phoneNumber," +
            "u.address.city," +
            "u.address.state," +
            "u.address.address," +
            "u.address.zip) " +
            "from User u left join u.address where u.id = :userId")
    Optional<UserProfileDto> getUserProfileByUserId(Long userId);

    @Query("select u.email from User u where u.role.name = 'ROLE_USER'")
    List<String> getAllEmails();

    @Query("select new com.ashapiro.auction.dto.user.UserDto(" +
            "u.id," +
            "u.email," +
            "u.firstName," +
            "u.lastName," +
            "u.phoneNumber," +
            "u.role.name," +
            "u.createdAt) " +
            "from User u join u.role")
    List<UserDto> getAllUsers();

    Optional<User> getUserByEmail(String email);
}
