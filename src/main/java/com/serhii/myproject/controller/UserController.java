package com.serhii.myproject.controller;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.dto.UserDto;
import com.serhii.myproject.dto.UserTransformer;
import com.serhii.myproject.model.User;
import com.serhii.myproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final HeaderComponent headerComponent;

    public UserController(UserService userService, HeaderComponent headerComponent) {
        this.userService = userService;
        this.headerComponent = headerComponent;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, Principal principal) {
        headerComponent.addUserToModel(model, principal);
        model.addAttribute("userDto", new UserDto());

        logger.info("Create user page was showed");

        return "create-user";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @PostMapping("/create")
    public String create(@ModelAttribute("userDto") UserDto userDto) {
        User user = UserTransformer.convertToEntity(userDto);
        userService.create(user);

        logger.info("New user was created");

        return "redirect:/managers-home";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @GetMapping("/{id}/read")
    public String read(@PathVariable("id") long id, Model model,Principal principal) {
        headerComponent.addUserToModel(model, principal);
        model.addAttribute("user", UserTransformer.convertToDto(userService.readById(id)));

        logger.info("User info page was showed");

        return "user-info";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") long id, Model model, Principal principal) {
        headerComponent.addUserToModel(model, principal);
        model.addAttribute("user", UserTransformer.convertToDto(userService.readById(id)));

        logger.info("Update user page was showed");

        return "update-user";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @PostMapping("/update")
    private String update(@ModelAttribute UserDto userDto) {
        userService.update(UserTransformer.convertToEntity(userDto));

        logger.info("User data was updated");

        return "redirect:/managers-home";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @GetMapping("/{id}/delete")
    private String delete(@PathVariable("id") long id) {
        userService.delete(id);

        logger.info("User was deleted");

        return "redirect:/managers-home";
    }

}
