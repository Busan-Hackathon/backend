package com.example.BusanHackathonProject.controller;

import com.example.BusanHackathonProject.dto.AddUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class UserViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        log.info("회원가입 폼 전달");
        model.addAttribute("member", new AddUserRequest());
        return "signUp";
    }

}
