package com.trove.controller;

import com.trove.response.CreatePostResponse;
import com.trove.response.PostsResponseUserFeed;
import com.trove.response.Response;
import com.trove.response.UserFeedResponse;
import com.trove.service.AuthService;
import com.trove.service.FileStorageService;
import com.trove.service.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class PostController {

    private final PostService postService;
    private final FileStorageService fileStorageService;
    private final AuthService authService;

    @Value("${file.upload-dir}")
    private String storageDirectory;

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
            CreatePostResponse response = new CreatePostResponse("Post Created Successfully", caption, posttype, filePaths, id );
            postService.savePost(response);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("The Post Created Successfully"));

           }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    This API Endpoint Gets the UserFeedResponse with the UserId.
//    This will retrieve the Details with pages. Pagination is Implemented for this.

    @GetMapping("/postSummary/{userId}")
    public ResponseEntity<UserFeedResponse> getHomepagePostDetails(@PathVariable("userId") int id,
                                                                   @RequestParam(value = "startIndex") int startIndex,
                                                                   @RequestParam(value = "endIndex") int endIndex){
        return ResponseEntity.ok(postService.getHomepageData(id, startIndex, endIndex));
    }

// This API Endpoint Gets the Images with the filename URL and Gives the Response as Image.

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImages(@PathVariable("filename") String filename){
        try{
            Path filepath = Paths.get(storageDirectory).resolve(filename).normalize();

            Resource resource = new UrlResource(filepath.toUri());
            if(resource.exists() && resource.isReadable()){
                String contentType = Files.probeContentType(filepath);
                if (contentType == null) contentType = "application/octet-stream";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            }else{
                return ResponseEntity.notFound().build();
            }

        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/postDetailedView/{postId}")
    public ResponseEntity<PostsResponseUserFeed> getPostDetails(@PathVariable("postId") Integer postId) {
        PostsResponseUserFeed post = postService.getSinglePost(postId);

        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
