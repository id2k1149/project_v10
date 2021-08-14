package org.id2k1149.project_v10.repo;

import org.id2k1149.project_v10.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}
