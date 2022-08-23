package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdsRestoreRequestDTO {
    private long id;
    private String title;
    private long price;
    private String description;
}
