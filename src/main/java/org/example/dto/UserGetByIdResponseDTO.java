package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGetByIdResponseDTO {
    private String username;

    private long id;
    private String title;
    private String description;
    private int price;
    private String name; // categories
    private String created;
}
