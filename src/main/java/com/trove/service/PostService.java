package com.trove.service;

import com.trove.model.Posts;
import com.trove.repository.PostsRepository;
import com.trove.response.PostResponse;
import com.trove.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
