package com.example.BusanHackathonProject.service;

import com.example.BusanHackathonProject.domain.Category;
import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.dto.postDto.PostDetailDto;
import com.example.BusanHackathonProject.dto.postDto.PostListDto;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.dto.rankingDto.CompanyRankingDto;
import com.example.BusanHackathonProject.dto.rankingDto.RankingDto;
import com.example.BusanHackathonProject.dto.rankingDto.ServiceRankingDto;
import com.example.BusanHackathonProject.dto.rankingDto.UserRankingDto;
import com.example.BusanHackathonProject.repository.PostRepository;
import com.example.BusanHackathonProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post savePost(PostRequest postRequest, User user){
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .category(Category.donation)
                .author(user)
                .build();

        if(postRequest.getCategory().equals("donation"))
            post.setCategory(Category.donation);
        if(postRequest.getCategory().equals("announcement"))
            post.setCategory(Category.announcement);
        postRepository.save(post);
        return post;
    }
    public PostDetailDto detailPost(Long id){
        Post post = postRepository.findById(id).orElseThrow();

        return PostDetailDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt().toString())
                .author(post.getAuthor().getName())
                .build();


    }
    public RankingDto rankingList(){

        List<CompanyRankingDto> companyRankingList = userRepository.findByRole("COMPANY")
                .stream()
                .sorted(Comparator.comparing(User::getDonationMoney).reversed()) // 🔥 후원 금액 기준 내림차순 정렬
                .map(user -> CompanyRankingDto.builder()  // 🔥 User -> CompanyRankingDto 변환
                        .companyName(user.getName()) // ✅ User의 name을 companyName으로 매핑
                        .donationMoney(user.getDonationMoney()) // ✅ 후원 금액 매핑
                        .build())
                .collect(Collectors.toList());
        List<UserRankingDto> userRankingList = userRepository.findByRole("USER")
                .stream()
                .sorted(Comparator.comparing(User::getDonationMoney).reversed())
                .map(user -> UserRankingDto.builder()
                        .userName(user.getName())
                        .donationMoney(user.getDonationMoney())
                        .build())
                .collect(Collectors.toList());
        List<ServiceRankingDto> serviceRankingList = userRepository.findByRole("USER")
                .stream()
                .sorted(Comparator.comparing(User::getDonationTime).reversed())
                .map(user -> ServiceRankingDto.builder()
                        .userName(user.getName())
                        .serviceTime(user.getDonationTime())
                        .build())
                .collect(Collectors.toList());

        return RankingDto.builder()
                .companyRankingList(companyRankingList)
                .userRankingList(userRankingList)
                .serviceRankingList(serviceRankingList)
                .build();
    }
    public List<PostListDto> postListDonation( ){
        List<Post> posts = postRepository.findByCategory(Category.donation)
                .orElseThrow(() -> new RuntimeException("해당 카테고리에 대한 게시글이 없습니다."));

        return posts.stream()
                .map(post -> PostListDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent()) // context → content로 수정
                        .author(post.getAuthor().getName()) // 🔥 User 객체에서 username 가져오기
                        .build())
                .collect(Collectors.toList());
    }
    public List<PostListDto> postListAnnounce( ){
        List<Post> posts = postRepository.findByCategory(Category.announcement)
                .orElseThrow(() -> new RuntimeException("해당 카테고리에 대한 게시글이 없습니다."));

        return posts.stream()
                .map(post -> PostListDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent()) // context → content로 수정
                        .author(post.getAuthor().getName()) // 🔥 User 객체에서 username 가져오기
                        .build())
                .collect(Collectors.toList());
    }
}
