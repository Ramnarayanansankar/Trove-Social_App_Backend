package com.trove.service;

import com.trove.model.Posts;
import com.trove.repository.PostsRepository;
import com.trove.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostsRepository postsRepository;

    public Response savePost(CreatePostResponse response) {

        Posts posts = new Posts();
        posts.setId(response.getId());
        posts.setPostCaption(response.getCaption());
        posts.setPostType(response.getPostType());

        if (response.getFilePaths() != null && !response.getFilePaths().isEmpty()) {
            // This joins all strings in the list with a comma delimiter
            String mediaString = String.join(",", response.getFilePaths());
            posts.setMedia(mediaString);
        } else {
            posts.setMedia(""); // or null, depending on your preference
        }


        postsRepository.save(posts);

        return new Response("Post Created Successfully");
    }

    public UserFeedResponse getHomepageData(int id, int page, int size) {

        int offset = page * size;
        List<PostFeedSummary> results = postsRepository.findUserPosts(id, size, offset);

        if (results.isEmpty()) {
            return new UserFeedResponse(0, List.of());
        }

        int totalCount = results.get(0).getTotalCount();
        List<String> photoUrls = results.stream().map(summary -> {
            String rawMedia = summary.getMedia();

            if (rawMedia == null && rawMedia.trim().isEmpty()) return null;
                String fullPath = rawMedia.split(",")[0].trim();
                String filename = Paths.get(fullPath).getFileName().toString();

            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/auth/images/")
                    .path(filename)
                    .toUriString();
                })
                .filter(url -> url != null)
                .collect(Collectors.toList());

        return new UserFeedResponse(totalCount, photoUrls);

    }

    public PostsResponseUserFeed getSinglePost(Integer postId) {

        PostFeedSummary post = postsRepository.findPostById(postId);

        if (post == null) {
            return null;
        }

        String rawMedia = post.getMedia();
        List<String> validUrls = new ArrayList<>();

        if (rawMedia != null && !rawMedia.trim().isEmpty()) {
            String[] paths = rawMedia.split(",");

            for (String fullPath : paths) {
                if (fullPath.trim().isEmpty()) continue;

                String filename = Paths.get(fullPath.trim()).getFileName().toString();

                String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/auth/images/")
                        .path(filename)
                        .toUriString();

                validUrls.add(url);
            }
        }
        return new PostsResponseUserFeed(
                post.getPostId(),
                post.getPostCaption(),
                validUrls,
                post.getPostCreatedTime()
        );
    }
}