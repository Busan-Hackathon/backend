package com.example.BusanHackathonProject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users") // SQL 예약어 충돌 방지를 위해 "users" 추천
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // ✅ Spring Data JPA의 Auditing 활성화
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String introduce;
    // ✅ 기본값 "USER" 설정

    @Column(nullable = false)
    private Integer donationMoney = 0;
    @Column(nullable = false)
    private Integer donationTime = 0;

    @Column(nullable = false)
    private String role = "USER";

    /**
     * Spring Security에서 권한 정보 반환
     * 예: "ROLE_USER", "ROLE_ADMIN"
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + this.role);
    }

    /**
     * Spring Security에서 '사용자명'으로 인식할 필드
     * 여기서는 email을 사용
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * 계정 만료 여부
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료되지 않았다고 가정
    }

    /**
     * 계정 잠금 여부
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠기지 않았다고 가정
    }

    /**
     * 자격 증명(비밀번호) 만료 여부
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 만료되지 않았다고 가정
    }

    /**
     * 계정 활성화 여부
     */
    @Override
    public boolean isEnabled() {
        return true; // 활성 상태라고 가정
    }
}
