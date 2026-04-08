package com.juseop.hair_simulator.dto;

import com.juseop.hair_simulator.domain.HairStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StyleRecommendResponse {
    private List<String> keywordBadge;
    private List<HairStyle> hairStyles;

}
