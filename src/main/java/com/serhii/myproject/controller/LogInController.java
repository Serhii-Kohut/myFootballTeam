package com.serhii.myproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/custom-login")
public class LogInController {

    Logger logger = LoggerFactory.getLogger(LogInController.class);
    @GetMapping
    public String login() {

        logger.info("Custom login page was showed");

        return "customLogin";
    }


}
