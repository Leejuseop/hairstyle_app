package com.juseop.hair_simulator.controller;

import com.juseop.hair_simulator.domain.HairStyle;
import com.juseop.hair_simulator.domain.User;
import com.juseop.hair_simulator.dto.TextToFilter;
import com.juseop.hair_simulator.service.FileService;
import com.juseop.hair_simulator.service.HairStyleService;
import com.juseop.hair_simulator.service.TextService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SimulationController {

    private final FileService fileService;
    private final TextService textService;
    private final HairStyleService hairStyleService;

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @PostMapping("/main")
    public String main(@RequestParam("imageFile") MultipartFile file,
                       HttpSession session,
                       RedirectAttributes rttr) {
        User user = (User) session.getAttribute("loginUser");

        if (file.isEmpty()) {
            rttr.addFlashAttribute("msg", "업로드된 사진이 없습니다. 다시 선택해주세요!");
            return "redirect:/main";
        }

        String saveFileName = fileService.removeBackground(file,user.getUserId());

        if (saveFileName != null) {
            rttr.addAttribute("fileName", saveFileName);
            return "redirect:/simulate";
        } else {
            rttr.addFlashAttribute("msg", "누끼 처리 중 오류가 발생했습니다.");
            return "redirect:/main";
        }
    }

    @GetMapping("/simulate")
    public String simulatePage(@RequestParam("fileName") String fileName, Model model) {
        List<String> styleSamples = fileService.getStyleSampleList();
        model.addAttribute("fileName", fileName);
        model.addAttribute("styleSamples", styleSamples);
        return "simulate";
    }

    @PostMapping("/userText")
    public ResponseEntity<TextToFilter> getText(@RequestBody Map<String, String> request,
                                                HttpSession session){
        String text = request.get("rawContent");
        User user = (User) session.getAttribute("loginUser");

        List<String> keywords = textService.saveAndExtract(text, user);

        TextToFilter textToFilter = hairStyleService.latestKeywordToFilter(user);

        return ResponseEntity.ok(textToFilter);

    }
}