package com.bookstore.repository.user;

import java.util.Optional;
import com.bookstore.model.Role;
import com.bookstore.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleName(RoleName roleName);
}