package com.ashapiro.auction.dto.product;

import java.math.BigDecimal;

public record ProductDto(String name, Long quantity, BigDecimal askingPrice, String status, String description) {

}
