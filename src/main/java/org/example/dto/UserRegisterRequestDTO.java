package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserRegisterRequestDTO {

    @NotEmpty(message = "Введите имя")
    @Size(min = 3 , max = 10, message = "Имя должно содержать от 3 до 10 символов")
    private String username;

    @NotEmpty(message = "Введите пароль")
    private String password;

}
