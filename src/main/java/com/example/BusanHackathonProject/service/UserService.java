package com.example.BusanHackathonProject.service;


import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.domain.Scrap;
import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.dto.AddUserRequest;
import com.example.BusanHackathonProject.dto.userDto.MyPageDto;
import com.example.BusanHackathonProject.repository.ScrapRepository;
import com.example.BusanHackathonProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto){
        log.info("유저 이메일 : {}, 유저 비밀번호 : {} ", dto.getEmail(), dto.getPassword());
        return userRepository.save(User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .introduce(dto.getIntroduce())
                .role(dto.getRole())
                .build()).getId();
    }
    public User findUser(String email){
        User user  = userRepository.findByEmail(email).orElseThrow();
        return user;
    }
    public MyPageDto myPageDto(String email) {
        User user = findUser(email);
        List<Post> scrapLists = scrapRepository.findByUser(user).
                stream()
                .map(Scrap::getPost)
                .collect(Collectors.toList());

        return MyPageDto.builder()
                .username(user.getUsername())
                .introduce(user.getIntroduce())
                .scrapList(scrapLists)
                .build();
    }



}
