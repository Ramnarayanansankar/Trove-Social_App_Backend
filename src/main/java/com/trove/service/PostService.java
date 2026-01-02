package com.trove.service;

import com.trove.model.Posts;
import com.trove.repository.PostsRepository;
import com.trove.response.PostResponse;
import com.trove.response.RecentPostSummary;
import com.trove.response.Response;
import com.trove.response.UserHomepageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostsRepository postsRepository;

    public Response savePost(PostResponse response) {

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
    public UserHomepageResponse getHomepageData(int id){

        List<RecentPostSummary> results = postsRepository.findRecentPosts(id);

        if(results.isEmpty()){
            return new UserHomepageResponse(0, List.of());
        }
        int totalCount = results.get(0).getTotalPosts();

        List<String> photoUrls = results.stream()
                .map(summary -> {
                    String rawMedia = summary.getMedia();
                    if (rawMedia == null || rawMedia.trim().isEmpty()) return null;

                    String fullpath = rawMedia.split(",")[0].trim();

                    String filename = Paths.get(fullpath).getFileName().toString();

                    return ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/auth/")
                            .path(filename)
                            .toUriString();
                })
                .filter(url -> url != null)
                .collect(Collectors.toList());

        return new UserHomepageResponse(totalCount, photoUrls);
    }

}
