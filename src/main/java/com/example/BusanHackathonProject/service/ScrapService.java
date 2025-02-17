package com.example.BusanHackathonProject.service;

import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.domain.Scrap;
import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.repository.PostRepository;
import com.example.BusanHackathonProject.repository.ScrapRepository;
import com.example.BusanHackathonProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void addScrap(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        // 이미 스크랩한 경우 중복 저장 방지
        scrapRepository.findByUserAndPost(user, post)
                .ifPresent(scrap -> { throw new RuntimeException("이미 스크랩한 게시물입니다."); });

        Scrap scrap = Scrap.builder()
                .user(user)
                .post(post)
                .build();
        scrapRepository.save(scrap);
    }
    public List<Post> getScrapList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return scrapRepository.findByUser(user)
                .stream()
                .map(Scrap::getPost)
                .collect(Collectors.toList());
    }
    public void removeScrap(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        Scrap scrap = scrapRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new RuntimeException("스크랩이 존재하지 않습니다."));

        scrapRepository.delete(scrap);
    }

}
