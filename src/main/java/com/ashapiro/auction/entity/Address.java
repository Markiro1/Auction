package com.ashapiro.auction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.MapsId;

import lombok.Setter;
import lombok.Getter;

import lombok.ToString;

@Entity
@Table(name = "addresses")
@Getter @Setter
@ToString(exclude = "user")
public class Address {
    @Id
    private Long id;

    private String state;

    private String city;

    private String address;

    private String zip;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public void addUser(User user) {
        user.setAddress(this);
        this.user = user;
    }
}
