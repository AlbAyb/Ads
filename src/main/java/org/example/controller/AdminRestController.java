package org.example.controller;

import org.example.dto.UserGetAllResponseDTO;
import org.example.manager.AdminManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class AdminRestController {

    private AdminManager manager;

    public AdminRestController(AdminManager manager) {
        this.manager = manager;
    }

    @GetMapping("/adminAll")
    public List<UserGetAllResponseDTO> getAll(){
        List<UserGetAllResponseDTO> all = manager.getAll(50, 0);
        for (UserGetAllResponseDTO dto : all) {
            Boolean removed = dto.getRemoved();
            if(removed.equals(false)){
                dto.setDel("активен");
            }else {
                dto.setDel("не активен");
            }
        }

        return all;
    }
}
