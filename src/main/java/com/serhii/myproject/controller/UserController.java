package com.serhii.myproject.controller;

import com.serhii.myproject.dto.UserDto;
import com.serhii.myproject.dto.UserTransformer;
import com.serhii.myproject.model.User;
import com.serhii.myproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "create-user";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("userDto") UserDto userDto) {
        User user = UserTransformer.convertToEntity(userDto);
        userService.create(user);
        return "redirect:/home";
    }

    @GetMapping("/{id}/read")
    public String read(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", UserTransformer.convertToDto(userService.readById(id)));
        return "user-info";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", UserTransformer.convertToDto(userService.readById(id)));
        return "update-user";
    }

    @PostMapping("/update")
    private String update(@ModelAttribute UserDto userDto) {
        userService.update(UserTransformer.convertToEntity(userDto));
        return "redirect:/home";
    }

}
