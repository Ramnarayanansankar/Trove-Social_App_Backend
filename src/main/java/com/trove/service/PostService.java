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

    public UserFeedResponse getHomepageData(int id) {

        List<PostFeedSummary> rawPosts = postsRepository.findUserPosts(id);

        if (rawPosts.isEmpty()) {
            return new UserFeedResponse(0, 0, List.of());
        }

        int totalCount = rawPosts.get(0).getTotalCount();

        List<PostsResponseUserFeed> postsResponseUserFeedList = rawPosts.stream().map(post -> {
            String rawMedia = post.getMedia();
            List<String> validUrls = new ArrayList<>();

            if (rawMedia != null && !rawMedia.trim().isEmpty()) {
                for (String fullPath : rawMedia.split(",")) {
                    if (fullPath.trim().isEmpty()) continue;
                    String filename = Paths.get(fullPath.trim()).getFileName().toString();

                    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/auth")
                            .path(filename)
                            .toUriString();
                    validUrls.add(url);

                }
            }
            return new PostsResponseUserFeed(post.getPostId(),
                    post.getPostCaption(),
                    validUrls,
                    post.getPostCreatedTime());

        }).collect(Collectors.toList());

        int remaining = totalCount - postsResponseUserFeedList.size();
        if (remaining < 0) remaining = 0;

        return new UserFeedResponse(totalCount, remaining, postsResponseUserFeedList);

    }
}