package com.juseop.hair_simulator.service;

import com.juseop.hair_simulator.domain.User;
import com.juseop.hair_simulator.domain.UserHistory;
import com.juseop.hair_simulator.domain.UserKeyword;
import com.juseop.hair_simulator.repository.UserHistoryRepository;
import com.juseop.hair_simulator.repository.UserKeywordRepository;
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
    private final UserKeywordRepository userKeywordRepository;

    public List<String> saveAndExtract(String text, User user){
        UserHistory userHistory = new UserHistory(text, user);
        userHistoryRepository.save(userHistory);

        List<String> keywordList = extractAndSaveCategorized(text, user, userHistory);

        return keywordList;
    }

    private List<String> extractAndSaveCategorized(String text, User user, UserHistory userHistory) {
        List<String> keywordList = new ArrayList<>();

        // --- 1. 🚺/🚹 헤어 스타일 (STYLE) ---
        // 여성 스타일 그물
        if (text.contains("단발")) addAndSave(keywordList, user, userHistory, "STYLE", "wave");
        if (text.contains("숏")) addAndSave(keywordList, user, userHistory, "STYLE", "여성숏컷");
        if (text.contains("중단발") || text.contains("거지존")) addAndSave(keywordList, user, userHistory, "STYLE", "중단발");
        if (text.contains("긴머리") || text.contains("롱헤어")) addAndSave(keywordList, user, userHistory, "STYLE", "long");
        if (text.contains("레이어드")) addAndSave(keywordList, user, userHistory, "STYLE", "레이어드컷");
        if (text.contains("허쉬")) addAndSave(keywordList, user, userHistory, "STYLE", "허쉬컷");
        if (text.contains("태슬")) addAndSave(keywordList, user, userHistory, "STYLE", "태슬컷");
        if (text.contains("히메")) addAndSave(keywordList, user, userHistory, "STYLE", "히메컷");
        if (text.contains("보브")) addAndSave(keywordList, user, userHistory, "STYLE", "보브컷");
        if (text.contains("리프") && (text.contains("녀") || text.contains("여"))) addAndSave(keywordList, user, userHistory, "STYLE", "여자리프컷");
        if (text.contains("C컬") || text.contains("씨컬")) addAndSave(keywordList, user, userHistory, "STYLE", "C컬펌");
        if (text.contains("S컬") || text.contains("에스컬")) addAndSave(keywordList, user, userHistory, "STYLE", "S컬펌");
        if (text.contains("빌드")) addAndSave(keywordList, user, userHistory, "STYLE", "빌드펌");
        if (text.contains("엘리자벳")) addAndSave(keywordList, user, userHistory, "STYLE", "엘리자벳펌");
        if (text.contains("그레이스")) addAndSave(keywordList, user, userHistory, "STYLE", "그레이스펌");
        if (text.contains("물결")) addAndSave(keywordList, user, userHistory, "STYLE", "물결펌");
        if (text.contains("히피")) addAndSave(keywordList, user, userHistory, "STYLE", "히피펌");
        if (text.contains("젤리")) addAndSave(keywordList, user, userHistory, "STYLE", "젤리펌");
        if (text.contains("시스루뱅") || text.contains("앞머리")) addAndSave(keywordList, user, userHistory, "STYLE", "여성앞머리");
        if (text.contains("매직") || text.contains("슬릭")) addAndSave(keywordList, user, userHistory, "STYLE", "매직/슬릭컷");

        // 남성 스타일 그물
        if (text.contains("투블럭")) addAndSave(keywordList, user, userHistory, "STYLE", "투블럭");
        if (text.contains("상고")) addAndSave(keywordList, user, userHistory, "STYLE", "상고컷");
        if (text.contains("댄디")) addAndSave(keywordList, user, userHistory, "STYLE", "댄디컷");
        if (text.contains("포마드")) addAndSave(keywordList, user, userHistory, "STYLE", "pomade");
        if (text.contains("리젠트")) addAndSave(keywordList, user, userHistory, "STYLE", "리젠트컷");
        if (text.contains("가일")) addAndSave(keywordList, user, userHistory, "STYLE", "가일컷");
        if (text.contains("아이비리그")) addAndSave(keywordList, user, userHistory, "STYLE", "아이비리그컷");
        if (text.contains("드롭")) addAndSave(keywordList, user, userHistory, "STYLE", "드롭컷");
        if (text.contains("파일럿")) addAndSave(keywordList, user, userHistory, "STYLE", "파일럿컷");
        if (text.contains("울프")) addAndSave(keywordList, user, userHistory, "STYLE", "울프컷");
        if (text.contains("가르마")) addAndSave(keywordList, user, userHistory, "STYLE", "가르마펌");
        if (text.contains("애즈")) addAndSave(keywordList, user, userHistory, "STYLE", "애즈펌");
        if (text.contains("쉐도우") || text.contains("섀도우")) addAndSave(keywordList, user, userHistory, "STYLE", "shadow");
        if (text.contains("볼륨펌")) addAndSave(keywordList, user, userHistory, "STYLE", "남성볼륨펌");
        if (text.contains("스왈로") || text.contains("스핀")) addAndSave(keywordList, user, userHistory, "STYLE", "curly");
        if (text.contains("다운펌")) addAndSave(keywordList, user, userHistory, "STYLE", "다운펌");
        if (text.contains("시스루댄디")) addAndSave(keywordList, user, userHistory, "STYLE", "시스루댄디");
        if (text.contains("리프") && (text.contains("남") || text.contains("군"))) addAndSave(keywordList, user, userHistory, "STYLE", "남자리프컷");
        if (text.contains("장발") || text.contains("예수머리")) addAndSave(keywordList, user, userHistory, "STYLE", "4");
        if (text.contains("포일") || text.contains("호일")) addAndSave(keywordList, user, userHistory, "STYLE", "호일펌");

        // --- 2. 📏 머리 길이 (LENGTH) ---
        if (text.contains("삭발") || text.contains("반삭") || text.contains("밀었") || text.contains("아주짧")) addAndSave(keywordList, user, userHistory, "LENGTH", "1");
        else if (text.contains("짧은") || text.contains("숏컷") || text.contains("스포츠") || text.contains("크롭")) addAndSave(keywordList, user, userHistory, "LENGTH", "3");
        else if (text.contains("적당한") || text.contains("중간") || text.contains("눈썹") || text.contains("보통")) addAndSave(keywordList, user, userHistory, "LENGTH", "5");
        else if (text.contains("긴") || text.contains("장발") || text.contains("어깨") || text.contains("쇄골")) addAndSave(keywordList, user, userHistory, "LENGTH", "7");
        else if (text.contains("완전 긴") || text.contains("가슴") || text.contains("롱") || text.contains("허리")) addAndSave(keywordList, user, userHistory, "LENGTH", "10");

        // --- 3. 🎨 머리 색깔 (COLOR) ---
        if (text.contains("검정") || text.contains("블랙") || text.contains("어두운")) addAndSave(keywordList, user, userHistory, "COLOR", "black");
        if (text.contains("갈색") || text.contains("브라운")) addAndSave(keywordList, user, userHistory, "COLOR", "brown");
        if (text.contains("노랑") || text.contains("금발")) addAndSave(keywordList, user, userHistory, "COLOR", "gold");
        if (text.contains("빨강") || text.contains("레드")) addAndSave(keywordList, user, userHistory, "COLOR", "red");
        if (text.contains("회색") || text.contains("애쉬")) addAndSave(keywordList, user, userHistory, "COLOR", "ash");
        if (text.contains("백발") || text.contains("화이트") || text.contains("탈색")) addAndSave(keywordList, user, userHistory, "COLOR", "white");

        return keywordList;
    }

    private void addAndSave(List<String> list, User user,UserHistory userHistory, String category, String keyword) {
        list.add(keyword);
        UserKeyword uk = new UserKeyword(category, keyword, user, userHistory);
        userKeywordRepository.save(uk);
    }
}