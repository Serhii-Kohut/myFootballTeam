package com.serhii.myproject.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    private static final String NAME_REGEXP = "[A-Z][a-z]+(-[A-Z][a-z]+){0,1}";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = NAME_REGEXP,
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "name", nullable = false)
    private String firstName;

    @Pattern(regexp = NAME_REGEXP,
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Email(message = "Must be a valid e-mail address")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?$\\.&]{8,}$",
            message = "Must be minimum 8 characters, at least one uppercase, one lowercase, one number and one special character")
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "roleid")
    @NotNull
    private Role role;
}
