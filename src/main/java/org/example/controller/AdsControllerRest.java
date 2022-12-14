package org.example.controller;


import org.example.dto.AdsGetAllResponseDTO;
import org.example.manager.AdsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class AdsControllerRest {
    private AdsManager manager;

    public AdsControllerRest(AdsManager manager) {
        this.manager = manager;
    }


    //http://localhost:8080/adss?limit=50&offset=0
    @GetMapping("/adss")
    public List<AdsGetAllResponseDTO> getAll() {
        return manager.getAll(50, 0);
    }
}
