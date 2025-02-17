package com.example.BusanHackathonProject.dto.rankingDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RankingDto {
    List<PointRankingDto> ranking1to30;
    List<PointRankingDto> ranking31to60;
    List<PointRankingDto> ranking61to90;
}
