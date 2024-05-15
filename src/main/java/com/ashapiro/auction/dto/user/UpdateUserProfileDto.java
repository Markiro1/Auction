package com.ashapiro.auction.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileDto {
    private String phoneNumber;
    private String city;
    private String state;
    private String address;
    private String zip;

}
