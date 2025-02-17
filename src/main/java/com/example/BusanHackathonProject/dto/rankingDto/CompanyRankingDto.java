package com.example.BusanHackathonProject.dto.rankingDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyRankingDto {

    private String companyName;
    private Integer donationMoney;
}
