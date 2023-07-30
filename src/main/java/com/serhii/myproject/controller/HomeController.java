package com.serhii.myproject.controller;

import com.serhii.myproject.model.User;
import com.serhii.myproject.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping({"/", "/home"})
    public String homePage(Model model, Principal principal) {
        User userDto = null;
        if (principal != null) {
            userDto = userRepository.findByEmail(principal.getName());
            model.addAttribute("user", userDto);
        }
        return "home-page";
    }
}

