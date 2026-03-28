package com.juseop.hair_simulator.controller;

import com.juseop.hair_simulator.domain.User;
import com.juseop.hair_simulator.repository.UserRepository;
import com.juseop.hair_simulator.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userId") String userId,
                               @RequestParam("userPassword") String userPassword,
                               HttpSession session,
                               RedirectAttributes rttr){

        Optional<User> userOptional = userService.login(userId, userPassword);

        if(userOptional.isPresent()){
                session.setAttribute("loginUser", userOptional.get());
                return "redirect:/main";
            }
            else{
                rttr.addFlashAttribute("loginError", "아이디 또는 비밀번호가 틀렸습니다.");
                return "redirect:/login";
            }
        }


    @GetMapping("/join")
    public String joinPage(){
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute User user, RedirectAttributes rttr){

        userService.join(user);

        rttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");

        return "redirect:/login";
    }



}
