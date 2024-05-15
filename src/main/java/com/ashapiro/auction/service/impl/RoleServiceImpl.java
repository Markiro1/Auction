package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.entity.Role;
import com.ashapiro.auction.exceptions.role.RoleNotFoundException;
import com.ashapiro.auction.repository.RoleRepository;
import com.ashapiro.auction.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findRoleByName(name)
                .orElseThrow(() -> new RoleNotFoundException(name));
    }
}
