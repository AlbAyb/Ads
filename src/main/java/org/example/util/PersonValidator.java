package org.example.util;

import org.example.dto.UserRegisterRequestDTO;
import org.example.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class PersonValidator implements Validator {

    private PersonDetailsService detailsService;

    @Autowired
    public PersonValidator(PersonDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegisterRequestDTO dto = (UserRegisterRequestDTO)o;

        try{
            detailsService.loadUserByUsername(dto.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("username", "", "Имя занято");
    }
}
