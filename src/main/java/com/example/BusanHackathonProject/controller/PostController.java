package com.example.BusanHackathonProject.controller;

import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

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

}
