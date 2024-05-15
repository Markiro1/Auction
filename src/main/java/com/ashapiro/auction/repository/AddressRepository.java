package com.ashapiro.auction.repository;

import com.ashapiro.auction.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address, Long> {
}
