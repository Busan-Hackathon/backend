package com.example.BusanHackathonProject.repository;

import com.example.BusanHackathonProject.domain.Category;
import com.example.BusanHackathonProject.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);
    Optional<List<Post>> findByCategory(Category category);
}
