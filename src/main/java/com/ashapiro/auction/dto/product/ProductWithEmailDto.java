package com.ashapiro.auction.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductWithEmailDto {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal askingPrice;
    private String status;
    private String description;
    private String email;
}
