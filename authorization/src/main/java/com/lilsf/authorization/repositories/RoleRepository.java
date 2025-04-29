package com.lilsf.authorization.repositories;


import com.lilsf.authorization.models.Role;
import com.lilsf.authorization.models.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleType(RoleType roleType);
}
