package com.serhii.myproject.service.impl;

import com.serhii.myproject.model.Role;
import com.serhii.myproject.repository.RoleRepository;
import com.serhii.myproject.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role readById(long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role update(Role role) {
        Role oldRole = readById(role.getId());
        if (oldRole != null) {
            oldRole.setName(role.getName());
            return roleRepository.save(oldRole);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.isEmpty() ? new ArrayList<>() : roles;
    }

    @Override
    public Role readByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
