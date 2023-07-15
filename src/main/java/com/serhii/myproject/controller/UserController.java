package com.serhii.myproject.controller;

import com.serhii.myproject.dto.UserDto;
import com.serhii.myproject.dto.UserTransformer;
import com.serhii.myproject.model.Role;
import com.serhii.myproject.model.User;
import com.serhii.myproject.service.RoleService;
import com.serhii.myproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Role> roles = roleService.getAll();
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", roles);
        return "create-user";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("userDto") UserDto userDto){
        User user = UserTransformer.convertToEntity(userDto, roleService);
        userService.create(user);
        return "redirect:/users";
    }
}
