package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageGetByIdResponseDTO {
    private long id;
    private long user_id;
    private long user_add;
    private String text;
    //    private long user;
//    private long add;
//    private String username;
    private String sender;
    private String resiver;
//    private Boolean removed;

}
