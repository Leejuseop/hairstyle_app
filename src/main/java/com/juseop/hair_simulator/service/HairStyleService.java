package com.juseop.hair_simulator.service;

import com.juseop.hair_simulator.domain.User;
import com.juseop.hair_simulator.domain.UserHistory;
import com.juseop.hair_simulator.domain.UserKeyword;
import com.juseop.hair_simulator.dto.HairFilterDto;
import com.juseop.hair_simulator.repository.HairStyleRepository;
import com.juseop.hair_simulator.repository.UserHistoryRepository;
import com.juseop.hair_simulator.repository.UserKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HairStyleService {

    private final HairStyleRepository hairStyleRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final UserKeywordRepository userKeywordRepository;

    public HairFilterDto latestKeywordToFilter(User user) {
        UserHistory latestHistory = userHistoryRepository.findFirstByUserOrderByPkIdDesc(user);
        List<UserKeyword> keywords = userKeywordRepository.findByUserHistory(latestHistory);
        HairFilterDto dto = new HairFilterDto();
        dto.setGender(user.getUserGender());

        for (UserKeyword uk : keywords) {
            String category = uk.getCategory();
            String val = uk.getKeyWord();

            if ("LENGTH".equals(category)) {
                int len = Integer.parseInt(val);
                dto.setMinLen(len);
                dto.setMaxLen(len);
            } else if ("STYLE".equals(category)) {
                dto.setStyle(val);
            }
        }
        return dto;
    }

    /*
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

     */


}
