package com.example.BusanHackathonProject.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String content;
    private String category;
}
