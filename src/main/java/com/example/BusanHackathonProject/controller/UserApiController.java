package com.example.BusanHackathonProject.controller;

import com.example.BusanHackathonProject.dto.AddUserRequest;
import com.example.BusanHackathonProject.dto.MainDto;
import com.example.BusanHackathonProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserApiController {

    private final UserService userService;

    @PostMapping("/addUser")
    public String signup(@ModelAttribute("member") AddUserRequest request){
        log.info("요청 이메일: " + request.getEmail() + ", 비밀번호: " + request.getPassword());
        userService.save(request);
        return "redirect:/login";
    }
    @GetMapping("/home")
    public String mainPage(Model model){
        MainDto mainDto = userService.mainPage();
        model.addAttribute("homepage", mainDto);
        log.info("메인페이지 이동!!");
        return "home";
    }
}
