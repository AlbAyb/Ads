package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDeleteRequestDTO {
    private long id;
    private long user_id;
    private long user_add;
    private long deleted;
    private boolean removed;
}
