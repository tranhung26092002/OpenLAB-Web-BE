package vn.com.openlab.api.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.api.role.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
