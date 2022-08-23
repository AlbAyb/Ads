package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdsUpdateRequestDTO {
    private long id;
    private String title;
    private long price;
    private String description;
//    private int name; // categories
//    private int username; // users
}
