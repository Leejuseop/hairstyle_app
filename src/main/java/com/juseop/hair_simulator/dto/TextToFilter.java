package com.juseop.hair_simulator.dto;

import com.juseop.hair_simulator.domain.HairStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextToFilter {
    private String style;              // 필터에서 선택될 스타일 이름
    private Integer minLength;         // 최소 기장 (1~10)
    private Integer maxLength;         // 최대 기장 (1~10)
}
