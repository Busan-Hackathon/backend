package com.example.BusanHackathonProject.service;

import com.example.BusanHackathonProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.example.BusanHackathonProject.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // DB에서 User 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 찾을 수 없습니다: " + email));

        // 🔥 Spring Security의 UserDetails 객체로 변환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())  // 🔥 Spring Security의 username으로 사용
                .password(user.getPassword())  // 🔥 암호화된 비밀번호
                .roles("USER")  // 🔥 권한 설정 (실제 DB 필드에 따라 조정 가능)
                .build();
    }
}
