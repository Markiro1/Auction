package com.ashapiro.auction.service;

import com.ashapiro.auction.dto.address.AddressDto;
import com.ashapiro.auction.entity.Address;
import com.ashapiro.auction.entity.User;

public interface AddressService {

    Address createAddress(User user, AddressDto addressDto);

    Address updateAddress(Long addressId, AddressDto addressDto);

    Address getAddressById(Long addressId);
}
