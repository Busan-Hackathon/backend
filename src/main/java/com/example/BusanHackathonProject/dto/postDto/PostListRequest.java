package com.example.BusanHackathonProject.dto.postDto;

import com.example.BusanHackathonProject.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostListRequest {
    private Category category;
}
