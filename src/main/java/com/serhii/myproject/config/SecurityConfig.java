package com.serhii.myproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetails customUserDetails;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetails customUserDetails, PasswordEncoder passwordEncoder) {
        this.customUserDetails = customUserDetails;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/admin/**").hasRole("PRESIDENT")
                .antMatchers("/players/create").hasAnyRole("PRESIDENT", "SPORT_DIRECTOR")
                .antMatchers("/players/*/edit").hasAnyRole("COACH", "SPORT_DIRECTOR")
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetails).passwordEncoder(passwordEncoder);
    }
}
