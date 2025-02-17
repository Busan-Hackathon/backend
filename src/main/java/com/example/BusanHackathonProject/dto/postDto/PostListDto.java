package com.example.BusanHackathonProject.dto.postDto;

import com.example.BusanHackathonProject.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostListDto {
    private Long id;
    private String title;
    private String content;
    private String author;

}
