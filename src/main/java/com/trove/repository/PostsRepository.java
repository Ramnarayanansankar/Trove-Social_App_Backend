package com.trove.repository;

import com.trove.model.Posts;
import com.trove.response.RecentPostSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {

    @Query(value = """
        SELECT 
            p.media AS media, 
            COUNT(*) OVER() AS totalPosts
        FROM posts p
        WHERE p.id = :userId 
        ORDER BY p.postcreatedtime DESC
        LIMIT 6
        """, nativeQuery = true)
    List<RecentPostSummary> findRecentPosts(@Param("userId") Integer userId);

}
