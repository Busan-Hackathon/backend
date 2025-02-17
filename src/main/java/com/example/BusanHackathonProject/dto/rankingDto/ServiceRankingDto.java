package com.example.BusanHackathonProject.dto.rankingDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServiceRankingDto {
    private String userName;
    private Integer serviceTime;
}
