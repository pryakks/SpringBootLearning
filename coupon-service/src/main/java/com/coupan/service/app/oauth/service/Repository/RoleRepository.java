package com.coupan.service.app.oauth.service.model.Repository;

import com.coupan.service.app.oauth.service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
