package com.example.BusanHackathonProject.dto.rankingDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRankingDto {

    private String userName;
    private Integer donationMoney;
}
