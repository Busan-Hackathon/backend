package com.example.BusanHackathonProject.dto.userDto;

import com.example.BusanHackathonProject.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MyPageDto {
    private String username;
    private String introduce;
    private List<Post> scrapList;

}
