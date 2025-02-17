package com.example.BusanHackathonProject.service;

import com.example.BusanHackathonProject.domain.Category;
import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.dto.postDto.PostListRequest;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Post> postList(PostListRequest postListRequest){
        List<Post> posts = postRepository.findByCategory(postListRequest.getCategory())
                .orElseThrow(() -> new RuntimeException("해당 카테고리에 대한 게시글이 없습니다."));

        return posts;

    }
}
