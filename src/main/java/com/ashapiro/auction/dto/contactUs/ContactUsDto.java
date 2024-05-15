package com.ashapiro.auction.dto.contactUs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactUsDto {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String descriptionOfGoods;
}