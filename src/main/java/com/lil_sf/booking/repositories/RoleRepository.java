package com.lil_sf.booking.repositories;

import com.lil_sf.booking.models.Role;
import com.lil_sf.booking.models.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleType(RoleType roleType);
}
