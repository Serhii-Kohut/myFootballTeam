package com.serhii.myproject.config;

import com.serhii.myproject.service.impl.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetails customUserDetails;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetails)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/admin/**").hasRole("PRESIDENT")
                .antMatchers("/users/create").hasRole("PRESIDENT")
                .antMatchers("/users/*/edit").hasRole("PRESIDENT")
                .antMatchers("/players/create").hasAnyRole("PRESIDENT", "SPORT_DIRECTOR")
                .antMatchers("/players/*/edit").hasAnyRole("PRESIDENT", "SPORT_DIRECTOR")
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

}
