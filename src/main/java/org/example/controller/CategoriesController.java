package org.example.controller;

import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.example.dto.CategoriesGetAllResponseDTO;
import org.example.dto.MessageCreateRequestDTO;
import org.example.manager.CategoriesManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
//@RestController
@Controller
public class CategoriesController {
    private CategoriesManager manager;

    //http://localhost:8080/categories.getAll?limit=50&offset=0
    @GetMapping("/categories.getAll")
    public List<CategoriesGetAllResponseDTO> getAll() {
        return manager.getAll();
    }

    //http://localhost:8080/categories/edit/1
    @GetMapping("/categories/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("views", manager.getByIdCategories(id));
        model.addAttribute("categories", manager.getAll());
        return "categoriesId";
    }
}
