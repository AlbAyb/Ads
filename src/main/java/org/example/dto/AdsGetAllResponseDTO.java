package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdsGetAllResponseDTO {
    private long id;
    private String title;
    private long price;
    private String name;
    private String username;
}
