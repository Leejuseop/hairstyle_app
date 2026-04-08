package com.juseop.hair_simulator.service;

import com.juseop.hair_simulator.domain.HairStyle;
import com.juseop.hair_simulator.domain.User;
import com.juseop.hair_simulator.repository.HairStyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HairStyleService {

    private final HairStyleRepository hairStyleRepository;

    public List<HairStyle> getRecommendedStyles(List<String> keywords, User user) {
        List<HairStyle> allStyles = hairStyleRepository.findAll();

        return allStyles.stream()
                .filter(style -> style.getHairGender().equals(user.getUserGender()))
                .sorted((s1, s2) -> {
                    int score1 = calculateScore(s1, keywords);
                    int score2 = calculateScore(s2, keywords);
                    return Integer.compare(score2, score1);
                })

                .limit(10)
                .collect(Collectors.toList());
    }

    private int calculateScore(HairStyle hairstyle, List<String> keywords) {
        int score = 0;

        if (hairstyle.getHairStyle() != null && keywords.contains(hairstyle.getHairStyle())) {
            score += 5;
        }

        // [길이 매칭: 2점] - DB의 Long(1~5)을 문자열로 변환해 비교
        if (hairstyle.getHairLength() != null) {
            for (String kw : keywords) {
                if (kw.matches("[1-5]")) {
                    long userWantedLength = Long.parseLong(kw);
                    long photoLength = hairstyle.getHairLength();

                    long lengthScore = 5 - Math.abs(userWantedLength - photoLength);

                    score += Math.max(0, lengthScore);
                    break;
                }
            }
        }

        if (hairstyle.getHairColor() != null && keywords.contains(hairstyle.getHairColor())) {
            score += 3;
        }

        return score;
    }
}