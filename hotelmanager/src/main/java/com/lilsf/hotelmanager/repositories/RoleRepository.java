package com.lilsf.hotelmanager.repositories;


import com.lilsf.hotelmanager.models.Role;
import com.lilsf.hotelmanager.models.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleType(RoleType roleType);
}
