package com.serhii.myproject.services;

import com.serhii.myproject.model.Role;
import com.serhii.myproject.model.User;
import com.serhii.myproject.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import org.junit.Assert;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Elkann");
        user.setEmail("test@emample.com");
        user.setPassword("51515Sdsd");
        user.setRole(Role.valueOf(Role.SPORT_DIRECTOR.name()));

        User createdUser = userService.create(user);

        assertNotNull(createdUser.getId());
        assertEquals("John", createdUser.getFirstName());
        assertEquals(Role.SPORT_DIRECTOR, createdUser.getRole());

    }
}
