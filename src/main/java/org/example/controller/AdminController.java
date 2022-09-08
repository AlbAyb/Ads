package org.example.controller;

import org.example.dto.UserGetAllResponseDTO;
import org.example.manager.AdminManager;
import org.example.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {
    private AdminManager manager;
    private UserManager userManager;

    @Autowired
    public AdminController(AdminManager manager, UserManager userManager) {
        this.manager = manager;
        this.userManager = userManager;
    }


    //http://localhost:8080/admin
    @GetMapping("/admin")
    public String getAll(Model model){
        List<UserGetAllResponseDTO> all = manager.getAll(50, 0);
        model.addAttribute("person", all);
        for (UserGetAllResponseDTO dto : all) {
            Boolean removed = dto.getRemoved();
            if(removed.equals(false)){
                dto.setDel("активен");
            }else {
                dto.setDel("не активен");
            }System.out.println(dto.getDel());
        }
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

        @GetMapping("/user/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userManager.getByIdUserAds(id));
        return "user";
    }
}
