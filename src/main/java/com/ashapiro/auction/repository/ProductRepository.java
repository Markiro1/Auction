package com.ashapiro.auction.repository;

import com.ashapiro.auction.dto.product.ProductDto;
import com.ashapiro.auction.dto.product.ProductWithEmailDto;
import com.ashapiro.auction.dto.product.SimpleProductDto;
import com.ashapiro.auction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new com.ashapiro.auction.dto.product.ProductWithEmailDto(" +
            "p.id, " +
            "p.name, " +
            "p.quantity, " +
            "p.askingPrice, " +
            "p.status.statusName, " +
            "p.description, " +
            "p.owner.email" +
            ") from Product p join User u ON u.id = p.owner.id")
    List<ProductWithEmailDto> getAllProductsWithUserEmail();

    @Query("select new com.ashapiro.auction.dto.product.ProductWithEmailDto(" +
            "p.id, " +
            "p.name, " +
            "p.quantity, " +
            "p.askingPrice, " +
            "p.status.statusName, " +
            "p.description, " +
            "p.owner.email" +
            ") from Product p join User u ON u.id = p.owner.id " +
            "where p.id = :productId")
    Optional<ProductWithEmailDto> getSingleProductWithEmailById(Long productId);

    Optional<Product> findProductByIdAndOwnerId(Long productId, Long userId);

    List<SimpleProductDto> findProductByOwnerId(Long id);

    @Query("select new com.ashapiro.auction.dto.product.ProductDto(" +
            "p.name, " +
            "p.quantity," +
            "p.askingPrice," +
            "ps.statusName," +
            "p.description" +
            ") from Product p join ProductStatus ps on p.status = ps " +
            "where p.id = :id")
    Optional<ProductDto> findProductById(Long id);

    @Query("select u.email, new com.ashapiro.auction.dto.product.SimpleProductDto(p.id, p.name)" +
            "from User u join Product p on u.id = p.owner.id " +
            "where p.status.statusName = 'ACCEPTED'")
    List<Object[]> getEmailsAndProductNames();
}