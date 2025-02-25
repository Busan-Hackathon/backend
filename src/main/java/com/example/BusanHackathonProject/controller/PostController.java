package com.example.BusanHackathonProject.controller;

import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.dto.postDto.PostDetailDto;
import com.example.BusanHackathonProject.dto.postDto.PostListDto;
import com.example.BusanHackathonProject.dto.postDto.PostListRequest;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.dto.rankingDto.RankingDto;
import com.example.BusanHackathonProject.service.PostService;
import com.example.BusanHackathonProject.service.ScrapService;
import com.example.BusanHackathonProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final ScrapService scrapService;

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
        model.addAttribute("pointRanking", postList);
        return "rankingList";
    }
    @GetMapping("/donation")
    public String getDonationList(@ModelAttribute PostListRequest postListRequest, Model model){
        log.info("도네이션 리스트 출력!!");
        List<PostListDto> postList = postService.postListDonation();
        model.addAttribute("donationPost", postList);
        return "donationList";
    }
    @GetMapping("/donation/help/{id}")
    public String donationPost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model){
        User user = userService.findUser(userDetails.getUsername());
        scrapService.addScrap(user.getId(),id);
        PostDetailDto post = postService.detailPost(id);
        model.addAttribute("postDetail", post);
        return "postDetail";

    }
//    @GetMapping("/usehistory")
//    public String getHistoryList(@ModelAttribute PostListRequest postListRequest, Model model){
//        List<Post> postList = postService.postList(postListRequest);
//        model.addAttribute("post", postList);
//        return "useHistory";
//    }
//    @GetMapping("/service")
//    public String getServiceList(@ModelAttribute PostListRequest postListRequest, Model model){
//        List<Post> postList = postService.postList(postListRequest);
//        model.addAttribute("post", postList);
//        return "serviceList";
//    }
//   @GetMapping("/event")
//    public String getEventList(@ModelAttribute PostListRequest postListRequest, Model model){
//        List<Post> postList = postService.postList(postListRequest);
//        model.addAttribute("post", postList);
//        return "eventList";
//   }
   @GetMapping("/announce")
    public String getAnnounceList(@ModelAttribute PostListRequest postListRequest, Model model) {
       List<PostListDto> postList = postService.postListAnnounce();
       model.addAttribute("announcePost", postList);
       return "announce";
   }
}
