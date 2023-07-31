package com.serhii.myproject.component;

import com.serhii.myproject.model.User;
import com.serhii.myproject.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.security.Principal;

@Component
public class HeaderComponent {
    public final UserRepository userRepository;

    public HeaderComponent(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUserToModel(Model model, Principal principal) {
        User user = null;
        if (principal != null) {
            user = userRepository.findByEmail(principal.getName());
            model.addAttribute("user", user);
        }
    }
}
