package com.bookstore.service;

import com.bookstore.model.Role;
import com.bookstore.model.RoleName;
import com.bookstore.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByRoleName(RoleName roleName) {
        return roleRepository.findRoleByRoleName(roleName).orElseThrow(() ->
                new RuntimeException("can't find role by roleName: " + roleName));
    }
}
