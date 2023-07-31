package com.serhii.myproject.controller;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.dto.UserDto;
import com.serhii.myproject.dto.UserTransformer;
import com.serhii.myproject.model.User;
import com.serhii.myproject.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/users")
public class UserController {
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
        return "create-user";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @PostMapping("/create")
    public String create(@ModelAttribute("userDto") UserDto userDto) {
        User user = UserTransformer.convertToEntity(userDto);
        userService.create(user);
        return "redirect:/managers-home";
    }

    @GetMapping("/{id}/read")
    public String read(@PathVariable("id") long id, Model model,Principal principal) {
        headerComponent.addUserToModel(model, principal);
        model.addAttribute("user", UserTransformer.convertToDto(userService.readById(id)));
        return "user-info";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") long id, Model model, Principal principal) {
        headerComponent.addUserToModel(model, principal);
        model.addAttribute("user", UserTransformer.convertToDto(userService.readById(id)));
        return "update-user";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @PostMapping("/update")
    private String update(@ModelAttribute UserDto userDto) {
        userService.update(UserTransformer.convertToEntity(userDto));
        return "redirect:/managers-home";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @GetMapping("/{id}/delete")
    private String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/managers-home";
    }

}
