package vn.com.openlab.api.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.openlab.api.role.model.Role;
import vn.com.openlab.api.role.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}