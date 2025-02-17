package com.example.BusanHackathonProject.dto.rankingDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RankingDto {
    List<CompanyRankingDto> companyRankingList;
    List<UserRankingDto> userRankingList;
    List<ServiceRankingDto> serviceRankingList;
}
