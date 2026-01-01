package com.trove.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) throws IOException {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
    }

//    This Function is for Storing Single File in the Given Directory

    public String storeFile(MultipartFile file) {
        try {
            // 1. Generate a unique filename (to prevent overwriting)
            // Example: "profile.jpg" -> "123e4567_profile.jpg"
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 2. Resolve the file path
            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            // 3. Save the file (Overwrite if exists)
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 4. Return the filename (so we can save it in the DB)
            return targetLocation.toString();

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file. Please try again!", ex);
        }
    }

   public List<String> storeMultipleFiles(MultipartFile[] files){

        List<String> filePaths = new ArrayList<>();

        Arrays.stream(files).forEach(file ->{
            String path = storeFile(file);
            filePaths.add(path);
        });
        return filePaths;
   }

}
