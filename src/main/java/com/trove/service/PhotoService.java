package com.trove.service;

import com.trove.repository.SignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {

    @Autowired
    private SignUpRepository signUpRepository;

//This Function is going to Check the filepath exists or not
// and whether is readable or not and Return the image from the local

    public Resource loadFileAsResource(String filePath) throws MalformedURLException {

        Path path = Paths.get(filePath);
        Resource resource = new UrlResource(path.toUri());

        if(resource.exists() && resource.isReadable()){
            return resource;
        }else{
            return null;
        }
    }


//    This function is going to check the image file type and return the type of file

    public MediaType getMediaTypeForFileName(String fileName) {
        String lowerCaseName = fileName.toLowerCase();

        if (lowerCaseName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        }
        return null;
    }


}
