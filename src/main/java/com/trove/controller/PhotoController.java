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

import static com.trove.utility.AppConstant.UPLOADING_FILE_EMPTY_MESSAGE;

@RestController
@RequestMapping("/api/auth")
public class PhotoController {

    private final PhotoService photoService;
    private final FileStorageService fileStorageService;


    public PhotoController(PhotoService photoService, FileStorageService fileStorageService) {
        this.photoService = photoService;
        this.fileStorageService = fileStorageService;
    }

//    This API Endpoint Uploads the Photo to the Local Storage.
//    This method checks whether the file is present or not and
//    calls the fileStorageService class to store the file.

    @PostMapping("/upload")
    public String uploadPhoto(@RequestParam("file")MultipartFile file) {

        if (file.isEmpty()) {
            return UPLOADING_FILE_EMPTY_MESSAGE;
        } else {
            return fileStorageService.storeFile(file);
        }

    }

//    This API Endpoint Gets the Photo from the Local Storage with the photorequest and
//    Gives the response as a image file(such as .jpg,.png)
//  This method first calls the loadFileAsResource method which is in PhotoService class.
//    It Checks whether the image is present or not.
//    It checks whether the image is having extension or not.
//    Sends the image in Response Body.

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