package org.example.controller;

import org.example.dto.UserRegisterRequestDTO;
import org.example.manager.UserManager;
import org.example.security.PersonDetails;
import org.example.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {
    private UserManager manager;
    private PersonValidator personValidator;

    @Autowired
    public UserController(UserManager manager, PersonValidator personValidator) {
        this.manager = manager;
        this.personValidator = personValidator;
    }


    //http://localhost:8080/registration
    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("person", new UserRegisterRequestDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("person")
                           @Valid UserRegisterRequestDTO dto,
                           BindingResult bindingResult) {
        personValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        manager.register(dto);
        return "redirect:/login";
    }


    @GetMapping("/user/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", manager.getByIdUserAds(id));
        return "user";
    }

    //http://localhost:8080/me
    @GetMapping("/me")
    public String me(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("person", personDetails.getPerson());
        model.addAttribute("ads", manager.me());
        return "me";
    }


}
