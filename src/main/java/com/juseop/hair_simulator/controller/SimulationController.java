package com.juseop.hair_simulator.controller;

import com.juseop.hair_simulator.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class SimulationController {

    private final FileService fileService;

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @PostMapping("/main")
    public String main(@RequestParam("imageFile") MultipartFile file,
                       RedirectAttributes rttr) {

        if (file.isEmpty()) {
            rttr.addFlashAttribute("msg", "업로드된 사진이 없습니다. 다시 선택해주세요!");
            return "redirect:/main";
        }

        try {
            String saveFileName = fileService.saveFile(file);
            rttr.addAttribute("fileName", saveFileName);
            return "redirect:/simulate";
        } catch (IOException e) {
            rttr.addFlashAttribute("msg", "파일 저장 중 오류가 발생했습니다.");
            return "redirect:/main";
        }

    }

    @GetMapping("/simulate")
    public String simulatePage(@RequestParam("fileName") String fileName, Model model) {
        // 1. 주소창의 ?fileName=... 값을 @RequestParam으로 쏙 뽑아옵니다.
        // 2. 그 값을 그대로 다시 Model에 담아서 simulate.html로 보냅니다.
        model.addAttribute("fileName", fileName);

        // 3. (중요) 하단 갤러리 에러 방지를 위해 빈 리스트라도 일단 넣어둡니다.
        // 나중에 여기에 진짜 스타일 사진 리스트를 채우면 됩니다! ㅋㅋㅋ
        model.addAttribute("styleSamples", new ArrayList<String>());

        return "simulate";
    }
}