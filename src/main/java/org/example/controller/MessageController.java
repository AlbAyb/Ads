package org.example.controller;

import org.example.dto.MessageCreateRequestDTO;
import org.example.dto.MessageGetByIdResponseDTO;
import org.example.manager.MessageManager;
import org.example.manager.UserManager;
import org.example.model.Person;
import org.example.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class MessageController {
    private MessageManager manager;
    private UserManager userManager;

    @Autowired
    public MessageController(MessageManager manager, UserManager userManager) {
        this.manager = manager;
        this.userManager = userManager;
    }

    //http://localhost:8080/message.getAll?limit=50&offset=0
    @GetMapping("/meMessage")
    public String getAll(Model model) {
        model.addAttribute("all", manager.getAll(50, 0));
        return "meMessage";
    }

    //http://localhost:8080/ads/edit/1
    @GetMapping("/meMessageId/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
//        Person person = personDetails.getPerson();
//        List<MessageGetByIdResponseDTO> biId = manager.getBiId(id);
//        for (MessageGetByIdResponseDTO dto : biId) {
//            String resiver = dto.getResiver();
//            if (dto.getUser_add() != person.getId()) {
//                resiver = dto.getSender();
//            }
//
//        }
        model.addAttribute("text", manager.getBiId(id));
        model.addAttribute("add", new MessageCreateRequestDTO());
        model.addAttribute("user", userManager.userGetById(id));


        return "meMessageId";
    }


    @PostMapping("/message/{id}")
    public String create(@PathVariable("id") long id, @ModelAttribute("add") MessageCreateRequestDTO dto) {
        manager.createMessage(dto, id);
        return "redirect:/meMessageId/{id}";
    }

//    @PostMapping("/meMessageId/delete/{id}/{me}")
//    public String delete(@PathVariable("id") long id) {
//        manager.delete(id);
//        return "redirect:/meMessageId/{me}";
//
//    }

    @PostMapping("/meMessageId/delete/{id}/{me}")
    public String delete(@PathVariable("id") long id) {
        manager.delete(id);
        return "redirect:/meMessageId/{me}";

    }
}