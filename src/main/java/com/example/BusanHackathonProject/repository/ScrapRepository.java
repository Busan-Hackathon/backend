package com.example.BusanHackathonProject.repository;

import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.domain.Scrap;
import com.example.BusanHackathonProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserAndPost(User user, Post post);
    List<Scrap> findByUser(User user);
    void deleteByUserAndPost(User user, Post post);
}
