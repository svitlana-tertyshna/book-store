package com.bookstore.service;

import com.bookstore.model.Role;
import com.bookstore.model.RoleName;

public interface RoleService {
    Role getRoleByRoleName(RoleName roleName);
}