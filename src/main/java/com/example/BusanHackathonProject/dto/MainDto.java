package com.example.BusanHackathonProject.dto;

import com.example.BusanHackathonProject.dto.postDto.PostListDto;
import com.example.BusanHackathonProject.dto.rankingDto.PointRankingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MainDto {
    List<PointRankingDto> pointRankingList;
    List<PostListDto> postListDtoList;
}
