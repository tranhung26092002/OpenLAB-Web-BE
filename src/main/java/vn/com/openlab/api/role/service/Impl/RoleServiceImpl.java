package vn.com.openlab.api.role.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.openlab.api.role.model.Role;
import vn.com.openlab.api.role.repository.RoleRepository;
import vn.com.openlab.api.role.service.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}