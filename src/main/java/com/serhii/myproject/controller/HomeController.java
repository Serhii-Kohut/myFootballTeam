package com.serhii.myproject.controller;

import com.serhii.myproject.model.User;
import com.serhii.myproject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final UserRepository userRepository;

    @Autowired
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

        logger.info("Home page was showed");

        return "home-page";
    }
}

