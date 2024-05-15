package com.ashapiro.auction.service;

import com.ashapiro.auction.entity.Role;

public interface RoleService {

    Role findByName(String name);
}
