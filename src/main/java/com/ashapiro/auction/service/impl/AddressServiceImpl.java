package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.dto.address.AddressDto;
import com.ashapiro.auction.entity.Address;
import com.ashapiro.auction.entity.User;
import com.ashapiro.auction.exceptions.address.AddressNotFoundException;
import com.ashapiro.auction.repository.AddressRepository;
import com.ashapiro.auction.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Address createAddress(User user, AddressDto addressDto) {
        Address address = new Address();
        modelMapper.map(addressDto, address);
        address.addUser(user);
        return addressRepository.save(address);
    }

    @Transactional
    @Override
    public Address updateAddress(Long addressId, AddressDto addressDto) {
        Address address = getAddressById(addressId);
        updateFields(address, addressDto);
        addressRepository.save(address);
        return address;
    }

    @Override
    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }

    private void updateFields(Address address, AddressDto addressDto) {
        Address newAddress = modelMapper.map(addressDto, Address.class);
        Field[] fields = address.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                if (name.equals("id")) {
                    continue;
                }
                Object newValue = field.get(newAddress);
                if (newValue != "" || newValue != null) {
                    field.set(address, newValue);
                }
            }
        } catch (IllegalAccessException e) {
            log.error("ERROR UPDATE FIELD");
        }
    }
}
