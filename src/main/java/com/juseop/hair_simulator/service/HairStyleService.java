package com.juseop.hair_simulator.service;

import com.juseop.hair_simulator.domain.HairStyle;
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
import java.util.stream.Collectors;

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

    record ScoredStyle(HairStyle style, int score) {}

    public List<String> getRecommendedStyleUrls(HairFilterDto filter) {
        // 1. 성별 변환 (HTML "men" -> DB "남성")
        String dbGender = "men".equals(filter.getGender()) ? "남성" : "여성";

        // 2. DB에서 해당 성별 데이터 싹 가져오기
        List<HairStyle> sameGenderStyles = hairStyleRepository.findByHairGender(dbGender);

        return sameGenderStyles.stream()
                .map(style -> new ScoredStyle(style, calculateScore(style, filter)))
                // [수정] 점수 0점인 사진도 생존! 필터링 줄 삭제함.
                .sorted((ss1, ss2) -> Integer.compare(ss2.score(), ss1.score())) // 점수 높은 순 정렬
                .map(ss -> {
                    String dbPath = ss.style().getImagePath();

                    // [꿀팁] replace가 실패하지 않도록 최대한 유연하게 처리
                    // DB 경로의 "C:"이 소문자인지 대문자인지 상관없이 바꿔버려
                    return dbPath.replace("C:/hairstyle_app/data/hairstyle/", "/styles/");
                })
                .collect(Collectors.toList());
    }

    private int calculateScore(HairStyle style, HairFilterDto filter) {
        int totalScore = 0;

        if (style.getHairStyle().equals(filter.getStyle())) {totalScore += 100;}

        int hairLen = style.getHairLength();
        int min = filter.getMinLen();
        int max = filter.getMaxLen();

        if (hairLen >= min && hairLen <= max) {
            totalScore += 10;
        } else {
            double avg = (min + max) / 2.0;
            double diff = Math.abs(hairLen - avg);

            int penaltyScore = (int) (10 - diff);
            totalScore += Math.max(0, penaltyScore);
        }
        return totalScore;
    }
}
