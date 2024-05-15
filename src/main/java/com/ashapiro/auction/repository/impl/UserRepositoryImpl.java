package com.ashapiro.auction.repository.impl;

import com.ashapiro.auction.entity.*;
import com.ashapiro.auction.repository.CustomUserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepositoryImpl implements CustomUserRepository {

    private final EntityManager entityManager;

    @Override
    public Optional<User> getUserFetchRelations(Long userId) {
        User user = entityManager.createQuery(
                        "SELECT u FROM User u LEFT JOIN FETCH u.products WHERE u.id = :userId",
                        User.class
                )
                .setParameter("userId", userId)
                .getSingleResult();

        List<Long> productIds = user.getProducts().stream()
                .map(Product::getId)
                .toList();

        List<Product> productsWithAuctions = entityManager.createQuery(
                        "SELECT p FROM Product p LEFT JOIN FETCH p.auctions a " +
                                "left join fetch a.wonAuction wa " +
                                "left join fetch a.payment WHERE p.id IN :productIds",
                        Product.class
                )
                .setParameter("productIds", productIds)
                .getResultList();
        user.setProducts(productsWithAuctions);
        return Optional.of(user);
    }
}
