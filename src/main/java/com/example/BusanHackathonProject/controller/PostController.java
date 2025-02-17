package com.example.BusanHackathonProject.controller;

import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.dto.postDto.PostDetailDto;
import com.example.BusanHackathonProject.dto.postDto.PostListRequest;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.dto.rankingDto.RankingDto;
import com.example.BusanHackathonProject.service.PostService;
import com.example.BusanHackathonProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final UserService userService;
    @GetMapping("/")
    public String showForm(Model model){

        model.addAttribute("PostRequest", new Post());
        return "postForm";
    }

    @PostMapping("/create")
    public String createPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal UserDetails userDetails){
        log.info("등록완료!!");
        User user = userService.findUser(userDetails.getUsername());
        Post savedPost = postService.savePost(postRequest, user);
        return "redirect:/posts/" + savedPost.getId();
    }

    @GetMapping("/{id}")
    public String getPostDetail(@PathVariable Long id, Model model){
        PostDetailDto post = postService.detailPost(id);
        model.addAttribute("postDetail", post);
        return "postDetail";
    }

    @GetMapping("/ranking")
    public String getRankingList(@ModelAttribute PostListRequest postListRequest, Model model){
        RankingDto postList = postService.rankingList();
        model.addAttribute("companyRankingList", postList);
        return "rankingList";
    }
    @GetMapping("/donation")
    public String getDonationList(@ModelAttribute PostListRequest postListRequest, Model model){
        List<Post> postList = postService.postList(postListRequest);
        model.addAttribute("post", postList);
        return "donationList";
    }
    @GetMapping("/usehistory")
    public String getHistoryList(@ModelAttribute PostListRequest postListRequest, Model model){
        List<Post> postList = postService.postList(postListRequest);
        model.addAttribute("post", postList);
        return "useHistory";
    }
    @GetMapping("/service")
    public String getServiceList(@ModelAttribute PostListRequest postListRequest, Model model){
        List<Post> postList = postService.postList(postListRequest);
        model.addAttribute("post", postList);
        return "serviceList";
    }
   @GetMapping("/event")
    public String getEventList(@ModelAttribute PostListRequest postListRequest, Model model){
        List<Post> postList = postService.postList(postListRequest);
        model.addAttribute("post", postList);
        return "eventList";
   }
   @GetMapping("/announce")
    public String getAnnounceList(@ModelAttribute PostListRequest postListRequest, Model model) {
       List<Post> postList = postService.postList(postListRequest);
       model.addAttribute("post", postList);
       return "announce";
   }
}
