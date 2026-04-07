package com.juseop.hair_simulator.service;

import com.juseop.hair_simulator.domain.User;
import com.juseop.hair_simulator.domain.UserHistory;
import com.juseop.hair_simulator.repository.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TextService {
    private final UserHistoryRepository userHistoryRepository;

    public List<String> saveAndExtract(String text, User user){
        List<String> keywordList = extractKeyword(text);

        String joinedKeywords = String.join(", ", keywordList);

        UserHistory userHistory = new UserHistory(text, joinedKeywords, user);

        userHistoryRepository.save(userHistory);

        return keywordList;
    }

    public List<String> extractKeyword(String text){
        List<String> keywordList = new ArrayList<>();

        // 🚺 여성 헤어 키워드 (20개)
        if (text.contains("단발")) keywordList.add("단발");
        if (text.contains("숏")) keywordList.add("여성숏컷");
        if (text.contains("중단발") || text.contains("거지존")) keywordList.add("중단발");
        if (text.contains("긴머리") || text.contains("롱헤어")) keywordList.add("긴머리");
        if (text.contains("레이어드")) keywordList.add("레이어드컷");
        if (text.contains("허쉬")) keywordList.add("허쉬컷");
        if (text.contains("태슬")) keywordList.add("태슬컷");
        if (text.contains("히메")) keywordList.add("히메컷");
        if (text.contains("보브")) keywordList.add("보브컷");
        if (text.contains("리프") && (text.contains("녀") || text.contains("여"))) keywordList.add("여자리프컷");

        if (text.contains("C컬") || text.contains("씨컬")) keywordList.add("C컬펌");
        if (text.contains("S컬") || text.contains("에스컬")) keywordList.add("S컬펌");
        if (text.contains("빌드")) keywordList.add("빌드펌");
        if (text.contains("엘리자벳")) keywordList.add("엘리자벳펌");
        if (text.contains("그레이스")) keywordList.add("그레이스펌");
        if (text.contains("물결")) keywordList.add("물결펌");
        if (text.contains("히피")) keywordList.add("히피펌");
        if (text.contains("젤리")) keywordList.add("젤리펌");
        if (text.contains("시스루뱅") || text.contains("앞머리")) keywordList.add("여성앞머리");
        if (text.contains("매직") || text.contains("슬릭")) keywordList.add("매직/슬릭컷");

        // 🚹 남성 헤어 키워드 (20개)
        if (text.contains("투블럭")) keywordList.add("투블럭");
        if (text.contains("상고")) keywordList.add("상고컷");
        if (text.contains("댄디")) keywordList.add("댄디컷");
        if (text.contains("포마드")) keywordList.add("포마드");
        if (text.contains("리젠트")) keywordList.add("리젠트컷");
        if (text.contains("가일")) keywordList.add("가일컷");
        if (text.contains("아이비리그")) keywordList.add("아이비리그컷");
        if (text.contains("드롭")) keywordList.add("드롭컷");
        if (text.contains("파일럿")) keywordList.add("파일럿컷");
        if (text.contains("울프")) keywordList.add("울프컷");

        if (text.contains("가르마")) keywordList.add("가르마펌");
        if (text.contains("애즈")) keywordList.add("애즈펌");
        if (text.contains("쉐도우")) keywordList.add("쉐도우펌");
        if (text.contains("볼륨펌")) keywordList.add("남성볼륨펌");
        if (text.contains("스왈로") || text.contains("스핀")) keywordList.add("스핀스왈로펌");
        if (text.contains("다운펌")) keywordList.add("다운펌");
        if (text.contains("시스루댄디")) keywordList.add("시스루댄디");
        if (text.contains("리프") && (text.contains("남") || text.contains("군"))) keywordList.add("남자리프컷");
        if (text.contains("장발") || text.contains("예수머리")) keywordList.add("남자장발");
        if (text.contains("포일") || text.contains("호일")) keywordList.add("호일펌");

        return keywordList;
    }
}
