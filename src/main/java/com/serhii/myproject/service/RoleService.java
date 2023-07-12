package com.serhii.myproject.service;

import com.serhii.myproject.model.Role;

import java.util.List;

public interface RoleService {
    Role create(Role role);
    Role readById(long id);
    Role update(Role user);
    void delete(long id);

    List<Role> getAll();
    Role readByName(String name);
}
