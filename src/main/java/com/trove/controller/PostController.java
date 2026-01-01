package com.trove.controller;

import com.trove.response.PostResponse;
import com.trove.response.Response;
import com.trove.service.AuthService;
import com.trove.service.FileStorageService;
import com.trove.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class PostController {

    private final PostService postService;
    private final FileStorageService fileStorageService;
    private final AuthService authService;

    public PostController(PostService postService, FileStorageService fileStorageService, AuthService authService) {
        this.postService = postService;
        this.fileStorageService = fileStorageService;
        this.authService = authService;
    }

    @PostMapping("/createPost")
    public ResponseEntity<Response> createPost(@RequestParam("files")MultipartFile[] files,
                                               @RequestParam("caption") String caption,
                                               @RequestParam("posttype") String posttype,
                                               @RequestParam("id") int id) {

        try{
            List<String> filePaths = fileStorageService.storeMultipleFiles(files);


            PostResponse response = new PostResponse("Post Created Successfully", caption, posttype, filePaths, id );

            postService.savePost(response);

            return ResponseEntity.status(HttpStatus.OK).body(new Response("The Post Created Successfully"));

           }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
