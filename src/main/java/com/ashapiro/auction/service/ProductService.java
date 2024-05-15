package com.ashapiro.auction.service;

import com.ashapiro.auction.dto.product.ProductDto;
import com.ashapiro.auction.dto.product.ProductWithEmailDto;
import com.ashapiro.auction.dto.product.SimpleProductDto;
import com.ashapiro.auction.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    String add(ProductWithEmailDto productWithEmailDto);

    List<ProductWithEmailDto> getAllProductsWithUserEmail();

    Optional<Product> getProductByIdAndUserId(Long productId, Long userId);

    List<SimpleProductDto> getProductsByUserId(Long id);

    ProductDto getProductById(Long id);

    Map<String, List<SimpleProductDto>> getEmailsAndProductNames();

    ProductWithEmailDto getProductsWithUserEmail(Long productId);

    String updateStatus(Long productId, String status);

    String updateProduct(ProductWithEmailDto productWithEmailDto);

    String delete(Long productId);
}
