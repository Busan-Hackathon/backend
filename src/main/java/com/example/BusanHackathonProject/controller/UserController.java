package com.example.BusanHackathonProject.controller;

import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.dto.userDto.MyPageDto;
import com.example.BusanHackathonProject.repository.UserRepository;
import com.example.BusanHackathonProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal UserDetails userDetails){

        String email = userDetails.getUsername();
        MyPageDto user = userService.myPageDto(email);
        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute("myPage", user);
        return "myPage";
    }
}
