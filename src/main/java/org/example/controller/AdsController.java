package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.*;
import org.example.manager.AdsManager;
import org.example.manager.CategoriesManager;
import org.example.manager.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@AllArgsConstructor
@Controller
//@RestController
public class AdsController {
    private AdsManager manager;
    private CategoriesManager categoriesManager;
    private UserManager userManager;

    //http://localhost:8080/ads?limit=50&offset=0
    @GetMapping("/ads")
    public String getAll(Model model) {
        model.addAttribute("ads", manager.getAll(50, 0));
        model.addAttribute("categories", categoriesManager.getAll());
        return "index";
    }

    //http://localhost:8080/ads/edit/1
    @GetMapping("/ads/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("views", manager.getById(id));
        model.addAttribute("add", new MessageCreateRequestDTO());
        return "edit";
    }

    //http://localhost:8080/ads/create
    @GetMapping("/ads/create")
    public String createAds(Model model) {
        model.addAttribute("ads", new AdsCreateRequestDTO());
        model.addAttribute("categories", categoriesManager.getAll());
        return "create";
    }

    @PostMapping("/ads/create")
    public String addAds(@ModelAttribute("ads") AdsCreateRequestDTO dto, Model model) {
        manager.addAds(dto);
        return "redirect:/ads";
    }

    @PostMapping("/me/ads/{id}")
    public String delete(@PathVariable("id") long id) {
        manager.delete(id);
        return "redirect:/me";
    }

    @GetMapping("/me/ads/restore")
    public String restore(Model model) {
        model.addAttribute("ads", userManager.restoreList());
        return "restore";
    }

    @PostMapping("/me/ads/restore/{id}")
    public String restore(@PathVariable("id") long id) {
        manager.restore(id);
        return "redirect:/me";
    }

    @GetMapping("/updateAds/{id}")
    public String update(@PathVariable("id") long id, Model model)  {
        model.addAttribute("views", manager.getById(id));
        model.addAttribute("update", new AdsUpdateRequestDTO());
        return "updateAds";
    }

    @PostMapping("/updateAds/{id}")
    public String update(@ModelAttribute("update") AdsUpdateRequestDTO dto,
                         @PathVariable("id") long id, Model model) {
        manager.update(id, dto);
        return "redirect:/me";

    }



}
