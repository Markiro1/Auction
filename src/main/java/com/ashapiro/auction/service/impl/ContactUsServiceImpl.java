package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.dto.contactUs.ContactUsDto;
import com.ashapiro.auction.entity.ContactUs;
import com.ashapiro.auction.repository.ContactUsRepository;
import com.ashapiro.auction.service.ContactUsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactUsServiceImpl implements ContactUsService {

    private final ContactUsRepository contactUsRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void save(ContactUsDto contactUsDto) {
        ContactUs contactUs = modelMapper.map(contactUsDto, ContactUs.class);
        contactUsRepository.save(contactUs);
    }
}
