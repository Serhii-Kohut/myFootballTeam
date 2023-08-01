package com.serhii.myproject.component;

import com.serhii.myproject.model.User;
import com.serhii.myproject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.security.Principal;

@Component
public class HeaderComponent {

    private static final Logger logger = LoggerFactory.getLogger(HeaderComponent.class);

    public final UserRepository userRepository;

    public HeaderComponent(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUserToModel(Model model, Principal principal) {
        User loggedInUser = null;
        if (principal != null) {
            logger.info("Adding user to model: {}", principal.getName());

            loggedInUser = userRepository.findByEmail(principal.getName());
            model.addAttribute("loggedInUser", loggedInUser);
        } else {
            logger.info("No user found");
        }
    }
}
