package com.example.BusanHackathonProject.config;

import com.example.BusanHackathonProject.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserDetailService userService;

    /**
     * ✅ 정적 리소스 및 H2 콘솔 접근 허용
     */
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console()) // H2 콘솔 접근 허용
                .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**"); // 정적 리소스 허용
    }

    /**
     * 🔥 Spring Security 설정 (로그인/로그아웃/접근 제어)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession()
                        .maximumSessions(1) // 한 계정으로 최대 1명 로그인 가능
                        .expiredUrl("/login?expired")) // 세션 만료 시 이동할 페이지
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
                        .requestMatchers("/", "/home", "/login", "/signup", "/addUser").permitAll() // 로그인 없이 접근 가능
                        .requestMatchers("/images/**", "/css/**", "/js/**").permitAll() // 정적 리소스 접근 허용
                        .requestMatchers("/post/create").authenticated() // 게시글 등록은 로그인 필요
                        .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/login") // 커스텀 로그인 페이지
                        .usernameParameter("email") // 이메일을 ID로 사용
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true) // 로그인 성공 시 홈으로 리다이렉트
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 URL 설정
                        .logoutSuccessUrl("/home") // 로그아웃 후 홈으로 이동
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()) // ⚠️ CSRF 비활성화 (운영 환경에서는 활성화 권장)
                .build();
    }

    /**
     * 🔐 AuthenticationManager를 Security에 등록
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 🔐 비밀번호 암호화 (BCrypt 사용)
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
