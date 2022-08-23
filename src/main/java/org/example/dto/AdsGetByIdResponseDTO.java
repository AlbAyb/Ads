package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdsGetByIdResponseDTO {
    private long id;
    private String title;
    private String description;
    private long price;
    private String name;
    private long user_id;
    private String username;
    private long message_user;
    private String removed;
}
