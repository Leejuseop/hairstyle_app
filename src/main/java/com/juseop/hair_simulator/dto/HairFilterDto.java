package com.juseop.hair_simulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HairFilterDto {
    private String gender;
    private String style;
    private Integer minLen;
    private Integer maxLen;
}
