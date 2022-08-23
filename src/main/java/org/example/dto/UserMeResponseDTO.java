package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserMeResponseDTO {
    private long id;
    private String title;
    private String description;
    private String created;
    private long price;
    private long user_add;
    private String name; // categories
    private String username;
}
