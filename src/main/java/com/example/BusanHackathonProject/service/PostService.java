package com.example.BusanHackathonProject.service;

import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post savePost(PostRequest postRequest){
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .targetMoney(postRequest.getTargetMoney())
                .currentMoney(0).build();
        postRepository.save(post);
        return post;
    }
    public Post deatilPost(Long id){
        return postRepository.findById(id).orElseThrow();
    }
}
