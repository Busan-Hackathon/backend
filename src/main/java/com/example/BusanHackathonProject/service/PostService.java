package com.example.BusanHackathonProject.service;

import com.example.BusanHackathonProject.domain.Category;
import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.dto.postDto.PostDetailDto;
import com.example.BusanHackathonProject.dto.postDto.PostListDto;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.dto.rankingDto.PointRankingDto;
import com.example.BusanHackathonProject.dto.rankingDto.RankingDto;
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
                .category(post.getCategory().toString())
                .date(post.getCreatedAt().toString())
                .author(post.getAuthor().getName())
                .build();


    }

    public RankingDto rankingList() {
        // 🔥 1. 모든 유저를 포인트 기준으로 내림차순 정렬
        List<User> users = userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getPoint).reversed())
                .toList();

        int totalUsers = users.size();
        Long ra = 1L;
        // 🔥 2. 순위별 리스트 생성
        List<PointRankingDto> pointRankingDto1to30 = (totalUsers >= 1)
                ? users.stream().limit(30)
                .map((user) -> new PointRankingDto(user.getId(), user.getUsername(), user.getPoint()))
                .collect(Collectors.toList())
                : List.of();

        List<PointRankingDto> pointRankingDto31to60 = (totalUsers >= 31)
                ? users.stream().skip(30).limit(30)
                .map((user) -> new PointRankingDto(user.getId(), user.getUsername(), user.getPoint()))
                .collect(Collectors.toList())
                : List.of();

        List<PointRankingDto> pointRankingDto61to90 = (totalUsers >= 61)
                ? users.stream().skip(60).limit(30)
                .map((user) -> new PointRankingDto(user.getId(), user.getUsername(), user.getPoint()))
                .collect(Collectors.toList())
                : List.of();
        for(PointRankingDto p  : pointRankingDto1to30)
        {
            p.setRanking(ra++);
        }
        if(totalUsers >= 31){
            for(PointRankingDto p  : pointRankingDto31to60)
            {
                p.setRanking(ra++);
            }
        }
        if(totalUsers >= 61){
            for(PointRankingDto p : pointRankingDto61to90){
                p.setRanking(ra++);
            }
        }

        // 🔥 3. RankingDto에 저장하여 반환
        return RankingDto.builder()
                .ranking1to30(pointRankingDto1to30)
                .ranking31to60(pointRankingDto31to60)
                .ranking61to90(pointRankingDto61to90)
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
