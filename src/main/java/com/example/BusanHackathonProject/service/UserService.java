package com.example.BusanHackathonProject.service;


import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.dto.AddUserRequest;
import com.example.BusanHackathonProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto){
        log.info("유저 이메일 : {}, 유저 비밀번호 : {} ", dto.getEmail(), dto.getPassword());
        return userRepository.save(User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .role("USER")
                .build()).getId();
    }
}
