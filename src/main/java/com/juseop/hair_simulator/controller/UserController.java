package com.juseop.hair_simulator.controller;

import com.juseop.hair_simulator.domain.User;
import com.juseop.hair_simulator.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/join")
    public String joinPage(){
        return "join";
    }

    @PostMapping("/join")
    public String joinService(@RequestParam("userId") String userId,
                              @RequestParam("userPassword") String userPassword,
                              @RequestParam("userName") String userName,
                              RedirectAttributes rttr){

        User user = new User(userId,userPassword, userName);
        userRepository.save(user);

        rttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");

        return "redirect:/login";
    }

}
