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

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.*;


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

    @Test
    public void testReadUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Elkann");
        user.setEmail("test@emample.com");
        user.setPassword("51515Sdsd");
        user.setRole(Role.valueOf(Role.SPORT_DIRECTOR.name()));

        User createdUser = userService.create(user);

        User userById = userService.readById(createdUser.getId());

        assertNotNull(userById.getId());
        assertEquals("John", userById.getFirstName());
        assertEquals("Elkann", userById.getLastName());
        assertEquals(Role.SPORT_DIRECTOR, userById.getRole());

    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Elkann");
        user.setEmail("test@emample.com");
        user.setPassword("51515Sdsd");
        user.setRole(Role.valueOf(Role.SPORT_DIRECTOR.name()));

        User createdUser = userService.create(user);
        User updatedUser = userService.update(createdUser);

        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(user.getFirstName(), updatedUser.getFirstName());
        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(Role.SPORT_DIRECTOR, updatedUser.getRole());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Elkann");
        user.setEmail("test@emample.com");
        user.setPassword("51515Sdsd");
        user.setRole(Role.valueOf(Role.SPORT_DIRECTOR.name()));

        userService.create(user);
        List<User> users = userService.getAllUsers();
        assertTrue(users.contains(user));

        userService.delete(user.getId());

        users = userService.getAllUsers();
        assertFalse(users.contains(user));
    }

    @Test
    public void testGetAllUsers(){
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Elkann");
        user1.setEmail("test@emample.com");
        user1.setPassword("51515Sdsd");
        user1.setRole(Role.valueOf(Role.SPORT_DIRECTOR.name()));

        User user2 = new User();
        user2.setFirstName("Tom");
        user2.setLastName("Pinter");
        user2.setEmail("test2@emample.com");
        user2.setPassword("51515Sdsd");
        user2.setRole(Role.valueOf(Role.COACH.name()));

        userService.create(user1);
        userService.create(user2);

        List<User> users = userService.getAllUsers();

        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));


    }


}
