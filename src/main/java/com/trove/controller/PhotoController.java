package com.trove.controller;

import com.trove.service.FileStorageService;
import com.trove.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

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


    }



