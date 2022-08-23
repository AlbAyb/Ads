package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsUpdateResponseDTO {
    private long id;
    private String title;
    private long price;
    private String description;
}
