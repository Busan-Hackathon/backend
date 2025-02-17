package com.example.BusanHackathonProject.service;


import com.example.BusanHackathonProject.domain.Category;
import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.domain.Scrap;
import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.dto.AddUserRequest;
import com.example.BusanHackathonProject.dto.MainDto;
import com.example.BusanHackathonProject.dto.postDto.PostListDto;
import com.example.BusanHackathonProject.dto.rankingDto.PointRankingDto;
import com.example.BusanHackathonProject.dto.userDto.MyPageDto;
import com.example.BusanHackathonProject.repository.PostRepository;
import com.example.BusanHackathonProject.repository.ScrapRepository;
import com.example.BusanHackathonProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PostRepository postRepository;

    public Long save(AddUserRequest dto){
        log.info("유저 이메일 : {}, 유저 비밀번호 : {} ", dto.getEmail(), dto.getPassword());
        return userRepository.save(User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .introduce(dto.getIntroduce())
                .role(dto.getRole())
                .build()).getId();
    }
    public User findUser(String email){
        User user  = userRepository.findByEmail(email).orElseThrow();
        return user;
    }
    public MyPageDto myPageDto(String email) {
        User user = findUser(email);
        List<Post> scrapLists = scrapRepository.findByUser(user).
                stream()
                .map(Scrap::getPost)
                .collect(Collectors.toList());

        return MyPageDto.builder()
                .username(user.getUsername())
                .introduce(user.getIntroduce())
                .scrapList(scrapLists)
                .build();
    }
    public MainDto mainPage(){
        List<User> users = userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getPoint).reversed())
                .toList();
        int totalUsers = users.size();
        List<PointRankingDto> pointRankingDto1to5 = (totalUsers >= 1)
                ? users.stream().limit(5)
                .map((user) -> new PointRankingDto(user.getId(), user.getUsername(), user.getPoint()))
                .collect(Collectors.toList())
                : List.of();
        Long ra = 1L;
        for(PointRankingDto p  : pointRankingDto1to5)
        {
            p.setRanking(ra++);
        }

        List<Post> posts = postRepository.findByCategory(Category.donation)
                .orElseThrow(() -> new RuntimeException("해당 카테고리에 대한 게시글이 없습니다."));

        List<PostListDto> postListDtos =  posts.stream().limit(3)
                .map(post -> PostListDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent()) // context → content로 수정
                        .author(post.getAuthor().getName()) // 🔥 User 객체에서 username 가져오기
                        .build())
                .collect(Collectors.toList());

        return MainDto.builder()
                .postListDtoList(postListDtos)
                .pointRankingList(pointRankingDto1to5)
                .build();
    }


}
