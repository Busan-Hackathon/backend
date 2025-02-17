package com.example.BusanHackathonProject.controller;

import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.service.ScrapService;
import com.example.BusanHackathonProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ScrapController {

    private final UserService userService;
    private final ScrapService scrapService;


    @PostMapping("/scrap/{postId}")
    public String addScrap(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userService.findUser(userDetails.getUsername());
        scrapService.addScrap(user.getId(), postId);
        return "redirect:/post/" + postId;
    }
    @PostMapping("/scrap/remove/{postId}")
    public String removeScrap(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails, Model model){

    User user = userService.findUser(userDetails.getUsername());
        scrapService.removeScrap(user.getId(), postId);
        return "redirect:/post/" + postId; // ✅ 게시글 상세 페이지로 리디렉션
    }
    @GetMapping("/scrap/list")
    public String getScrapList(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUser(userDetails.getUsername());
        List<Post> scrapList = scrapService.getScrapList(user.getId());
        model.addAttribute("scrapList", scrapList);
        return "scrapList"; // ✅ scrapList.html 페이지로 이동
    }

}
