package com.example.BusanHackathonProject.service;

import com.example.BusanHackathonProject.domain.User;
import com.example.BusanHackathonProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles(user.getRole())  // 예: "USER"
                .build();
    }
    public void saveUser(String username, String rawPassword, String role) {
        User user = new User();
        user.setName(username);
        user.setPassword(passwordEncoder.encode(rawPassword)); // 비밀번호 해싱
        user.setRole(role);
        userRepository.save(user);
    }

}
