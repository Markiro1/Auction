package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.dto.address.AddressDto;
import com.ashapiro.auction.dto.contactUs.ContactUsDto;
import com.ashapiro.auction.dto.user.UpdateUserProfileDto;
import com.ashapiro.auction.entity.Address;
import com.ashapiro.auction.entity.ContactUs;
import com.ashapiro.auction.entity.User;
import com.ashapiro.auction.exceptions.user.UserNotFoundException;
import com.ashapiro.auction.repository.ContactUsRepository;
import com.ashapiro.auction.service.AddressService;
import com.ashapiro.auction.service.HomeService;
import com.ashapiro.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService {

    private final ContactUsRepository contactUsRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void saveContactUs(ContactUsDto contactUsDto) {
        ContactUs contactUs = modelMapper.map(contactUsDto, ContactUs.class);
        contactUsRepository.save(contactUs);
    }

    @Transactional
    @Override
    public void updateProfile(Long userId, UpdateUserProfileDto updateUserProfileDto) {
        String phoneNumber = updateUserProfileDto.getPhoneNumber();
        User user = userService.getById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        updateOrCreateUserAddress(user, updateUserProfileDto);
        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
        }
    }

    private Address updateOrCreateUserAddress(User user, UpdateUserProfileDto updateUserProfileDto) {
        AddressDto addressDto = AddressDto.builder()
                .city(updateUserProfileDto.getCity())
                .state(updateUserProfileDto.getState())
                .address(updateUserProfileDto.getAddress())
                .zip(updateUserProfileDto.getZip())
                .build();
        Address userAddress = user.getAddress();
        if (userAddress == null) {
            return addressService.createAddress(user, addressDto);
        } else {
            Long addressId = userAddress.getId();
            return addressService.updateAddress(addressId, addressDto);
        }
    }
}
