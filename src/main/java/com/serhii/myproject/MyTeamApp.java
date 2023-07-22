package com.serhii.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class MyTeamApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MyTeamApp.class, args);
    }
}