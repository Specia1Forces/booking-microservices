package com.lilsf.client.repositories;



import com.lilsf.client.models.Role;
import com.lilsf.client.models.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleType(RoleType roleType);
}
