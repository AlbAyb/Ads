package org.example.controller;

import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.example.dto.CategoriesGetAllResponseDTO;
import org.example.manager.CategoriesManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class CategoriesController {
    private CategoriesManager manager;

    //http://localhost:8080/categories.getAll?limit=50&offset=0
    @RequestMapping("/categories.getAll")
    public List<CategoriesGetAllResponseDTO> getAll() {
        return manager.getAll();
    }
}
