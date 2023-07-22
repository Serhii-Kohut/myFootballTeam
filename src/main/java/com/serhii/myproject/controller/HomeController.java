package com.serhii.myproject.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    public String showHomePage(){
        return "home";
    }
}
