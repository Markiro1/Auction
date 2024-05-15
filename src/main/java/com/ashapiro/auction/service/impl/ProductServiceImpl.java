package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.dto.product.ProductDto;
import com.ashapiro.auction.dto.product.ProductWithEmailDto;
import com.ashapiro.auction.dto.product.SimpleProductDto;
import com.ashapiro.auction.entity.Product;
import com.ashapiro.auction.entity.User;
import com.ashapiro.auction.entity.statuses.ProductStatus;
import com.ashapiro.auction.exceptions.product.ProductNotFoundException;
import com.ashapiro.auction.exceptions.user.UserNotFoundException;
import com.ashapiro.auction.repository.ProductRepository;
import com.ashapiro.auction.service.ProductService;
import com.ashapiro.auction.service.ProductStatusService;
import com.ashapiro.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductStatusService productStatusService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public String add(ProductWithEmailDto productWithEmailDto) {
        String email = productWithEmailDto.getEmail();
        User owner = userService.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        Product product = modelMapper.map(productWithEmailDto, Product.class);

        String status = productWithEmailDto.getStatus();
        ProductStatus productStatus = productStatusService.getStatusByName(status);

        product.addOwner(owner);
        product.setStatus(productStatus);
        productRepository.save(product);
        return "Product successfully added";
    }

    @Override
    public Optional<Product> getProductByIdAndUserId(Long productId, Long userId) {
        return productRepository.findProductByIdAndOwnerId(productId, userId);
    }

    @Override
    public List<ProductWithEmailDto> getAllProductsWithUserEmail() {
        return productRepository.getAllProductsWithUserEmail();
    }

    @Override
    public List<SimpleProductDto> getProductsByUserId(Long id) {
        return productRepository.findProductByOwnerId(id);
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Map<String, List<SimpleProductDto>> getEmailsAndProductNames() {
        return productRepository.getEmailsAndProductNames()
                .stream()
                .collect(Collectors.groupingBy(obj -> (String) obj[0],
                        Collectors.mapping(obj -> (SimpleProductDto) obj[1], Collectors.toList())));
    }

    @Override
    public ProductWithEmailDto getProductsWithUserEmail(Long productId) {
        return productRepository.getSingleProductWithEmailById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    @Transactional
    public String updateProduct(ProductWithEmailDto productWithEmailDto) {
        Long productId = productWithEmailDto.getId();
        Product currentProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        updateProductFields(currentProduct, productWithEmailDto);
        return "Product updated successfully";
    }

    @Override
    @Transactional
    public String updateStatus(Long productId, String status) {
        ProductStatus productStatus = productStatusService.getStatusByName(status);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        product.setStatus(productStatus);
        return "Product status updated successfully";
    }

    @Override
    @Transactional
    public String delete(Long productId) {
        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException(productId));
        try {
            productRepository.delete(product);
            return "Product deleted successfully";
        } catch (Exception e) {
            return String.format("Error deleting product with id: %d");
        }
    }

    private void updateProductFields(Product product, ProductWithEmailDto productDto) {
        String status = productDto.getStatus();
        ProductStatus productStatus = productStatusService.getStatusByName(status);

        product.setName(productDto.getName());
        product.setStatus(productStatus);
        product.setAskingPrice(productDto.getAskingPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
    }
}
