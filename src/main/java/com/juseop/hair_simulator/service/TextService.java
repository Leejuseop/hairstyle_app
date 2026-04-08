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

        // 1. 🚺 여성 헤어 스타일 (스타일 매칭용)
        if (text.contains("단발")) keywordList.add("wave");
        if (text.contains("숏")) keywordList.add("여성숏컷");
        if (text.contains("중단발") || text.contains("거지존")) keywordList.add("중단발");
        if (text.contains("긴머리") || text.contains("롱헤어")) keywordList.add("long");
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

        // 2. 🚹 남성 헤어 스타일 (스타일 매칭용)
        if (text.contains("투블럭")) keywordList.add("투블럭");
        if (text.contains("상고")) keywordList.add("상고컷");
        if (text.contains("댄디")) keywordList.add("댄디컷");
        if (text.contains("포마드")) keywordList.add("pomade");
        if (text.contains("리젠트")) keywordList.add("리젠트컷");
        if (text.contains("가일")) keywordList.add("가일컷");
        if (text.contains("아이비리그")) keywordList.add("아이비리그컷");
        if (text.contains("드롭")) keywordList.add("드롭컷");
        if (text.contains("파일럿")) keywordList.add("파일럿컷");
        if (text.contains("울프")) keywordList.add("울프컷");
        if (text.contains("가르마")) keywordList.add("가르마펌");
        if (text.contains("애즈")) keywordList.add("애즈펌");
        if (text.contains("쉐도우") || text.contains("섀도우")) keywordList.add("shadow");
        if (text.contains("볼륨펌")) keywordList.add("남성볼륨펌");
        if (text.contains("스왈로") || text.contains("스핀")) keywordList.add("curly");
        if (text.contains("다운펌")) keywordList.add("다운펌");
        if (text.contains("시스루댄디")) keywordList.add("시스루댄디");
        if (text.contains("리프") && (text.contains("남") || text.contains("군"))) keywordList.add("남자리프컷");
        if (text.contains("장발") || text.contains("예수머리")) keywordList.add("4"); // 장발은 길이 4로도 매칭
        if (text.contains("포일") || text.contains("호일")) keywordList.add("호일펌");

        // 3. 📏 머리 길이 (hairLength 1~5) - 형 요청대로 아주 촘촘한 그물!
        // [1단계: 아주 짧음]
        if (text.contains("삭발") || text.contains("반삭") || text.contains("밀었") ||
                text.contains("1미리") || text.contains("3미리") || text.contains("6미리") ||
                text.contains("빡빡") || text.contains("스킨헤드") || text.contains("아주짧")) {
            keywordList.add("1");
        }
        // [2단계: 짧음]
        if (text.contains("짧은") || text.contains("숏컷") || text.contains("스포츠") ||
                text.contains("크롭") || text.contains("눈썹 위") || text.contains("훤히") ||
                text.contains("이마 보") || text.contains("깔끔하게") || text.contains("커트")) {
            keywordList.add("2");
        }
        // [3단계: 보통/중간]
        if (text.contains("적당한") || text.contains("중간") || text.contains("보통") ||
                text.contains("눈썹") || text.contains("귀 덮는") || text.contains("코 끝") ||
                text.contains("입술") || text.contains("턱선") || text.contains("기본길이") ||
                text.contains("덮는") || text.contains("내린")) {
            keywordList.add("3");
        }
        // [4단계: 긴 편]
        if (text.contains("긴") || text.contains("길게") || text.contains("장발") ||
                text.contains("어깨") || text.contains("쇄골") || text.contains("넘어가는") ||
                text.contains("중단발") || text.contains("거지존") || text.contains("묶이는")) {
            keywordList.add("4");
        }
        // [5단계: 아주 긴]
        if (text.contains("완전 긴") || text.contains("가슴") || text.contains("허리") ||
                text.contains("롱") || text.contains("엉덩이") || text.contains("등까지") ||
                text.contains("엄청 긴") || text.contains("치렁치렁") || text.contains("허리까지")) {
            keywordList.add("5");
        }

        // 4. 🎨 머리 색깔 (색상 매칭용)
        if (text.contains("검정") || text.contains("블랙") || text.contains("어두운")) keywordList.add("black");
        if (text.contains("갈색") || text.contains("브라운") || text.contains("밤색")) keywordList.add("brown");
        if (text.contains("밝은갈색") || text.contains("초코브라운") || text.contains("밀크브라운")) keywordList.add("light_brown");
        if (text.contains("노랑") || text.contains("금발") || text.contains("골드") || text.contains("블론드")) keywordList.add("gold");
        if (text.contains("빨강") || text.contains("레드") || text.contains("와인")) keywordList.add("red");
        if (text.contains("파랑") || text.contains("블루") || text.contains("애쉬블루")) keywordList.add("blue");
        if (text.contains("초록") || text.contains("카키") || text.contains("그린")) keywordList.add("green");
        if (text.contains("보라") || text.contains("퍼플") || text.contains("바이올렛")) keywordList.add("purple");
        if (text.contains("회색") || text.contains("애쉬") || text.contains("그레이") || text.contains("은색")) keywordList.add("ash");
        if (text.contains("분홍") || text.contains("핑크") || text.contains("로즈")) keywordList.add("pink");
        if (text.contains("오렌지") || text.contains("주황")) keywordList.add("orange");
        if (text.contains("백발") || text.contains("화이트") || text.contains("탈색")) keywordList.add("white");

        return keywordList;
    }
}