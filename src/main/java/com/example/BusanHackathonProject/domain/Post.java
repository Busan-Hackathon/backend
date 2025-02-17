package com.example.BusanHackathonProject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts") // SQL 예약어 충돌 방지를 위해 "users" 추천
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer targetMoney;

    @Column(nullable = false)
    private Integer currentMoney;





}
