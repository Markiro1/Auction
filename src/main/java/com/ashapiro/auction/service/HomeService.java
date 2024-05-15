package com.ashapiro.auction.service;

import com.ashapiro.auction.dto.contactUs.ContactUsDto;
import com.ashapiro.auction.dto.user.UpdateUserProfileDto;

public interface HomeService {
    void saveContactUs(ContactUsDto contactUsDto);

    void updateProfile(Long userId, UpdateUserProfileDto updateUserProfileDto);
}
