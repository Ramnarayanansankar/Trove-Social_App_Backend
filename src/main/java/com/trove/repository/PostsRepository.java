package com.trove.repository;

import com.trove.model.Posts;
import com.trove.response.PostFeedSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {

    @Query(value = """
        SELECT 
            p.postid AS postId,
            p.postcaption AS postCaption,
            p.media AS media,
            p.postcreatedtime AS postCreatedTime,
            COUNT(*) OVER() AS totalCount   -- <--- Calculates total DB rows dynamically
        FROM posts p
        WHERE p.id = :userId 
        ORDER BY p.postcreatedtime DESC
        LIMIT 10                            -- <--- Only return the top 10
        """, nativeQuery = true)
    List<PostFeedSummary> findUserPosts(@Param("userId") Integer userId);

    @Query(value = """
        SELECT 
            p.postid AS postId,
            p.postcaption AS postCaption,
            p.media AS media,
            p.postcreatedtime AS postCreatedTime
        FROM posts p
        WHERE p.postid = :postId
        """, nativeQuery = true)
    PostFeedSummary findPostById(@Param("postId") Integer postId);

}
