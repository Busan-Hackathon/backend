package com.example.BusanHackathonProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class AddUserRequest {

    private String name;
    private String email;
    private String password;
    private String location;
    private String role;
}
