package com.trove.controller;

import com.trove.request.PhotoRequest;
import com.trove.service.FileStorageService;
import com.trove.service.PhotoService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/auth")
public class PhotoController {

    private final PhotoService photoService;
    private final FileStorageService fileStorageService;


    public PhotoController(PhotoService photoService, FileStorageService fileStorageService) {
        this.photoService = photoService;
        this.fileStorageService = fileStorageService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/upload")
    public String uploadPhoto(@RequestParam("file")MultipartFile file) {

        if (file.isEmpty()) {
            return "File is Empty !! Cannot be Processed";
        } else {
            return fileStorageService.storeFile(file);
        }

    }

//    This API Endpoint Gets the Photo from the Local Storage with the photorequest and
//    Gives the response as a image file(such as .jpg,.png)


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/getPhoto")
    public ResponseEntity<Resource> getPhoto(@RequestBody PhotoRequest photoRequest) {

        try {

            Resource resource = photoService.loadFileAsResource(photoRequest.getPhotoUrl());
            if (resource == null) {
                return ResponseEntity.notFound().build();
            }
            MediaType mediaType = photoService.getMediaTypeForFileName(photoRequest.getPhotoUrl());

            if (mediaType == null) {
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}