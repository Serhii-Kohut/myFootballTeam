package com.serhii.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/custom-login")
public class LogInController {
    @GetMapping
    public String login() {
        return "customLogin";
    }


}
