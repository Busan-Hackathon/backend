package com.example.BusanHackathonProject.dto.rankingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PointRankingDto {
    private Long id;
    private Long ranking;
    private String name;
    private Integer point;
    public PointRankingDto(Long id, String name, Integer point){
        this.id = id;
        this.name = name;
        this.point =point;
    }
}
