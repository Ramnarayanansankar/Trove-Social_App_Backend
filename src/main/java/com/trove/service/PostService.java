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

import static com.trove.utility.AppConstant.*;

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

        return new Response(SAVE_POST_SUCCESS_RESPONSE_MESSAGE);
    }

    public UserFeedResponse getHomepageData(int id, int startIndex, int endIndex) {


        int offset = startIndex;
        int size = endIndex - startIndex + 1;
        boolean hasMore;


        List<PostFeedSummary> rawPosts = postsRepository.findUserPosts(id, size, offset);
        if (rawPosts.isEmpty()) {
            return new UserFeedResponse(0, List.of(), startIndex, endIndex, false);
        }

        Long dbCount = rawPosts.get(0).getTotalCount();
        int totalCount = (dbCount != null) ? dbCount.intValue() : 0;

        List<PostsResponseUserFeed> postList = rawPosts.stream().map(post -> {
            String rawMedia = post.getMedia();
            List<String> validUrls = new ArrayList<>();

            if (rawMedia != null && !rawMedia.trim().isEmpty()) {
                String[] paths = rawMedia.split(",");
                for (String fullPath : paths) {
                    if (fullPath.trim().isEmpty()) continue;
                    String filename = Paths.get(fullPath.trim()).getFileName().toString();

                    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path(POST_IMAGE_URL_PATH)
                            .path(filename)
                            .toUriString();
                    validUrls.add(url);
                }
            }
            return new PostsResponseUserFeed(post.getPostId(), post.getPostCaption(), validUrls, post.getPostCreatedTime());

        }).collect(Collectors.toList());

        if(endIndex == totalCount){
             hasMore = false;
        }else{
             hasMore = true;
        }
        return new UserFeedResponse(totalCount, postList, startIndex, endIndex, hasMore);

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
                        .path(POST_IMAGE_URL_PATH)
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