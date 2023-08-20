package com.serhii.myproject.dto;

import com.serhii.myproject.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Role role;

    private String photo;


    public UserDto() {

    }
}
