package com.serhii.myproject.controller;

import com.serhii.myproject.dto.UserDto;
import com.serhii.myproject.dto.UserTransformer;
import com.serhii.myproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<UserDto> userDtos = userService.getAllUsers().stream()
                .map(UserTransformer::convertToDto)
                .collect(Collectors.toList());

        model.addAttribute("users", userDtos);

        return "home";
    }
}
