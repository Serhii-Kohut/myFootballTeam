package com.serhii.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogOutController {
    public String logout(){
        return "redirect:/login";
    }

}
