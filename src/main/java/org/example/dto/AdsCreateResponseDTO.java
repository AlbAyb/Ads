package org.example.dto;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsCreateResponseDTO {
    //private long id;
    private String title;
    private long price;
    private String description;
    private int name; //categories
    private int username; // users
}
