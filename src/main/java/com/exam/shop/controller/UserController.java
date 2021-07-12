package com.exam.shop.controller;

import com.exam.shop.domain.entity.User;
import com.exam.shop.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @NonNull
    private BCryptPasswordEncoder passwordEncoder;

    private final UserService userService;

    @GetMapping(value = "/user/loginView")
    public String loginView(){
        return "user/login";
    }

    @PostMapping(value = "/user/login")
    public String login(HttpServletRequest request, RedirectAttributes redirectAttributes, @ModelAttribute User userVO){
        log.error("@@@");
        String userPw = userVO.getUserPw();
        System.out.println("userPw = " + userPw);
        userVO = userService.findUserByUserEmail(userVO.getUserEmail()).get();
        if(userVO == null || !passwordEncoder.matches(userPw, userVO.getUserPw())){
            redirectAttributes.addFlashAttribute("rsMsg", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "redirect:/user/loginView";
        }
        request.getSession().setAttribute("userVO", userVO);
        return "redirect:/home";
    }
}
