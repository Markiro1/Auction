package com.ashapiro.auction.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {
    private String email;

    @jakarta.validation.constraints.Pattern(regexp = "^[a-zA-Z]+$", message = "The firstname must contain only letters")
    private String firstName;

    @jakarta.validation.constraints.Pattern(regexp = "^[a-zA-Z]+$", message = "The lastname must contain only letters")
    private String lastName;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$",
            message = "The password must contain both letters and numbers")
    private String password;
}
