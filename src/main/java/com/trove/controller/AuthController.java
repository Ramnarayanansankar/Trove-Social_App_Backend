package com.trove.controller;

import com.trove.exceptions.UserAlreadyExistsException;
import com.trove.model.SignUp;
import com.trove.request.LoginRequest;
import com.trove.request.SignUpRequest;
import com.trove.response.ErrorResponse;
import com.trove.response.LoginResponse;
import com.trove.response.SignUpResponse;
import com.trove.service.AuthService;
import com.trove.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Base URL
public class AuthController {


    private final AuthService authService;
    private final FileStorageService fileStorageService;

    public AuthController(AuthService authService, FileStorageService fileStorageService) {
        this.authService = authService;
        this.fileStorageService = fileStorageService;
    }

//    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            SignUpResponse signUpResponse = new SignUpResponse("User Registered Successfully", signUpRequest.getEmail(), signUpRequest.getPhotoUrl());
            authService.doSignUp(signUpRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);

        } catch (UserAlreadyExistsException e) {
            // Returns 409 CONFLICT if email exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e) {
            // Returns 500 INTERNAL SERVER ERROR for anything else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }

//    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginRequest loginRequest, BindingResult result) {
        // 1. Handle Validation Errors
        if (result.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse(result.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
        // 2. Call the service
        SignUp user = authService.doLogin(loginRequest);

        // 3. Check if login was successful
        if (user != null) {
            // Create the response object with the message, first name and the PhotoURL
            LoginResponse response = new LoginResponse("Login Successful", user.getFirstName(), user.getPhotoUrl());
            return ResponseEntity.ok().body(response);
        } else {
            // Handle invalid credentials
            ErrorResponse errorResponse = new ErrorResponse("Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }



//    This is a Controller Class I have Written for the Firebase Setup. Since it is not free, I am not Configuring it there.
//    Whenever we want to use it, We can remove the comment and use it for the application.

//    @PostMapping("/updatephoto")
//    public ResponseEntity<?> updatePhoto(@RequestBody PhotoUpdateRequest photoUpdateRequest){
//        try{
//            appService.updatePhoto(photoUpdateRequest);
//            return ResponseEntity.ok("Firebase Photo URL Saved Successfully");
//        } catch (RuntimeException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}

