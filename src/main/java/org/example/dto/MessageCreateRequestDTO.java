package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageCreateRequestDTO {
    private long deleted;
    private String text;
    private long user_id;
    private long user_add;
}
