package com.serhii.myproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(99)
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/users/create").hasRole("PRESIDENT")
                .antMatchers("/users/{id}/read").hasRole("PRESIDENT")
                .antMatchers("/users/{id}/update").hasRole("PRESIDENT")
                .antMatchers("/users/{id}/delete").hasRole("PRESIDENT")
                .antMatchers("/managers-home").hasAnyAuthority("PRESIDENT", "SPORT_DIRECTOR", "COACH")
                .antMatchers("/players/**").hasAnyAuthority("PRESIDENT", "SPORT_DIRECTOR", "COACH")
                .antMatchers("/player-home").authenticated()
                .and()
                .formLogin()
                .loginPage("/custom-login")
                .and()
                .csrf().disable();
    }
}
