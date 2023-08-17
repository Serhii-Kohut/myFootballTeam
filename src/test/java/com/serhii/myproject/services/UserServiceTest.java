package com.serhii.myproject.services;

import com.serhii.myproject.model.Role;
import com.serhii.myproject.model.User;
import com.serhii.myproject.service.UserService;
import org.junit.Before;
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

    private User user1;
    private User user2;

    @Before
    public void setUp() {
        user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Elkann");
        user1.setEmail("test@emample.com");
        user1.setPassword("51515Sdsd");
        user1.setRole(Role.valueOf(Role.SPORT_DIRECTOR.name()));

        user2 = new User();
        user2.setFirstName("Tom");
        user2.setLastName("Pinter");
        user2.setEmail("test2@emample.com");
        user2.setPassword("51515Sdsd");
        user2.setRole(Role.valueOf(Role.COACH.name()));

        userService.create(user1);
        userService.create(user2);
    }

    @Test
    public void testCreateUser() {
        User createdUser = userService.create(user1);

        assertNotNull(createdUser.getId());
        assertEquals("John", createdUser.getFirstName());
        assertEquals(Role.SPORT_DIRECTOR, createdUser.getRole());

    }

    @Test
    public void testReadUser() {
        User createdUser = userService.create(user1);

        User userById = userService.readById(createdUser.getId());

        assertNotNull(userById.getId());
        assertEquals("John", userById.getFirstName());
        assertEquals("Elkann", userById.getLastName());
        assertEquals(Role.SPORT_DIRECTOR, userById.getRole());

    }

    @Test
    public void testUpdateUser() {
        User createdUser = userService.create(user1);
        User updatedUser = userService.update(createdUser);

        assertEquals(user1.getId(), updatedUser.getId());
        assertEquals(user1.getFirstName(), updatedUser.getFirstName());
        assertEquals(user1.getEmail(), updatedUser.getEmail());
        assertEquals(Role.SPORT_DIRECTOR, updatedUser.getRole());
    }

    @Test
    public void testDeleteUser() {
        userService.create(user1);
        List<User> users = userService.getAllUsers();
        assertTrue(users.contains(user1));

        userService.delete(user1.getId());

        users = userService.getAllUsers();
        assertFalse(users.contains(user1));
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userService.getAllUsers();

        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));

    }

}
