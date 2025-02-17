package com.example.BusanHackathonProject.dto.postDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostDetailDto {
    private String title;
    private String content;
    private String author;
    private Integer targetMoney;
    private Integer currentMoney;
}
