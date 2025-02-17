package com.example.BusanHackathonProject.controller;

import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.dto.postDto.PostListRequest;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public String showForm(Model model){
        model.addAttribute("PostRequest", new Post());
        return "postForm";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute PostRequest postRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "postForm";
        Post savedPost = postService.savePost(postRequest);
        return "redirect:/posts/" + savedPost.getId();
    }

    @GetMapping("/{id}")
    public String getPostDetail(@PathVariable Long id, Model model){
        Post post = postService.deatilPost(id);
        model.addAttribute("post", post);
        return "postDetail";
    }

    @GetMapping("/ranking")
    public String getRankingList(@ModelAttribute PostListRequest postListRequest, Model model){
        List<Post> postList = postService.postList(postListRequest);
        model.addAttribute("post", postList);
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
