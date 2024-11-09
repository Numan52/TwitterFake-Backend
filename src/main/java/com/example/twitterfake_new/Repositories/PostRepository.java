package com.example.twitterfake_new.Repositories;

import com.example.twitterfake_new.Models.Post;
import com.example.twitterfake_new.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    Page<Post> findByParentPostIdIsNull(Pageable pageable);

    Page<Post> findAllByUserId(Long userId, Pageable pageable);

    Page<Post> findAllByLikedBy(Set<User> likedBy, Pageable pageable);
    @Query("SELECT DISTINCT p " +
            "FROM Post p " +
            "WHERE p.user.id = :userId AND p.parentPost IS NOT NULL")
    Page<Post> findParentPostsRespondedByUser(@Param("userId") Long userId, Pageable pageable);
    Page<Post> findAllByUserIdAndParentPostNull(long userId, Pageable pageable);
}
