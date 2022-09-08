package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGetAllResponseDTO {
    private long id;
    private String username;
    private String role;
    private long userId;
    private String del;
    private Boolean removed;




}
