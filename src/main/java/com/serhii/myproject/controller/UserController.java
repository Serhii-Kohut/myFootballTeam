package com.serhii.myproject.controller;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.dto.UserDto;
import com.serhii.myproject.dto.UserTransformer;
import com.serhii.myproject.model.User;
import com.serhii.myproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@Controller
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final HeaderComponent headerComponent;

    @Autowired
    private FileUploadController fileUploadController;

    public UserController(UserService userService, HeaderComponent headerComponent) {
        this.userService = userService;
        this.headerComponent = headerComponent;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, Principal principal) {
        headerComponent.addUserToModel(model, principal);
        model.addAttribute("userDto", new UserDto());

        logger.info("Create user page was showed");

        return "create-user";
    }

    @PreAuthorize("hasRole('PRESIDENT')")
    @PostMapping("/create")
    public String create(@ModelAttribute("userDto") UserDto userDto, @RequestParam("file") MultipartFile file) {
        User user = UserTransformer.convertToEntity(userDto);
        userService.create(user);

        fileUploadController.uploadToS3(file);

        logger.info("New user was created");

        return "redirect:/managers-home";
    }

    @PreAuthorize("hasRole('PRESIDENT')")
    @GetMapping("/{id}/read")
    public String read(@PathVariable("id") long id, Model model, Principal principal) {
        headerComponent.addUserToModel(model, principal);
        User user = userService.readById(id);
        model.addAttribute("user", UserTransformer.convertToDto(user));
        model.addAttribute("photoUrl", user.getPhoto());

        logger.info("User info page was showed");

        return "user-info";
    }

    @PreAuthorize("hasRole('PRESIDENT')")
    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") long id, Model model, Principal principal) {
        headerComponent.addUserToModel(model, principal);
        model.addAttribute("user", UserTransformer.convertToDto(userService.readById(id)));

        logger.info("Update user page was showed");

        return "update-user";
    }

    @PreAuthorize("hasRole('PRESIDENT')")
    @PostMapping("/update")
    private String update(@ModelAttribute UserDto userDto, @RequestParam("file") MultipartFile file, Model model) {
        userService.update(UserTransformer.convertToEntity(userDto));

        String photoUrl = fileUploadController.uploadToS3(file);

        model.addAttribute("photoUrl", photoUrl);

        logger.info("User data was updated");

        return "redirect:/managers-home";
    }

    @PreAuthorize("hasRole('PRESIDENT')")
    @GetMapping("/{id}/delete")
    private String delete(@PathVariable("id") long id) {
        userService.delete(id);

        logger.info("User was deleted");

        return "redirect:/managers-home";
    }

}
