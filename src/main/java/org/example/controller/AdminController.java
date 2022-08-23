package org.example.controller;

import org.example.manager.AdminManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    private AdminManager manager;

    @Autowired
    public AdminController(AdminManager manager) {
        this.manager = manager;
    }


    //http://localhost:8080/admin
    @GetMapping("/admin")
    public String getAll(Model model){
        model.addAttribute("person", manager.getAll(50, 0));
        return "/admin";
    }

    @PostMapping("/admin/user/{id}")
    public String delete(@PathVariable("id") long id) {
        manager.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}")
    public String restoreUser(@PathVariable("id") long id) {
        manager.restoreUser(id);
        return "redirect:/admin";
    }
}
