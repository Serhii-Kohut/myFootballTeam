package com.serhii.myproject.dto;

import com.serhii.myproject.model.User;
import com.serhii.myproject.service.RoleService;

public class UserTransformer {
    public static UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getName()
        );
    }

    public static User convertToEntity(UserDto userDto, RoleService roleService) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(roleService.readByName(userDto.getRole()));
        return user;
    }

}
