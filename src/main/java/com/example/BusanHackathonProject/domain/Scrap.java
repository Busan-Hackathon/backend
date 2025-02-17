package com.example.BusanHackathonProject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scraps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
