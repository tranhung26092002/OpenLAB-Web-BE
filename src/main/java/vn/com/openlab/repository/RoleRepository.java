package vn.com.openlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
