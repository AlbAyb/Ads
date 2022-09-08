package org.example.controller;

import org.example.dto.MessageGetAllResponseDTO;
import org.example.manager.MessageManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class MessageRestController {
    private MessageManager manager;

    public MessageRestController(MessageManager manager) {
        this.manager = manager;
    }

    //http://localhost:8080/message?limit=50&offset=0
    @GetMapping("/messageAll")
    public List<MessageGetAllResponseDTO> getAll() {
        return manager.getAll(50, 0);
    }


}
