package com.trove.controller;

import com.trove.model.SignUp;
import com.trove.request.LoginRequest;
import com.trove.request.SignUpRequest;
import com.trove.response.ErrorResponse;
import com.trove.response.LoginResponse;
import com.trove.response.Response;
import com.trove.service.AppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Base URL
public class AppController {


    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Response> signUp(@RequestBody SignUpRequest signUpRequest, BindingResult result) {
        if (result.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse(result.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            return ResponseEntity.ok().body(appService.doSignUp(signUpRequest));

        }

    }

//    @PostMapping("/login")
//    public ResponseEntity<Response> logIn(@RequestBody LoginRequest loginRequest, BindingResult result) {
//        if (result.hasErrors()) {
//            ErrorResponse errorResponse = new ErrorResponse(result.getFieldError().getDefaultMessage());
//            return ResponseEntity.badRequest().body(errorResponse);
//        } else {
//            return ResponseEntity.ok().body(appService.doLogin(loginRequest));
//
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginRequest loginRequest, BindingResult result) {
        // 1. Handle Validation Errors
        if (result.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse(result.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // 2. Call the service
        // NOTE: appService.doLogin must now return a 'User' object, not a String.
        SignUp user = appService.doLogin(loginRequest);

        // 3. Check if login was successful
        if (user != null) {
            // Create the response object with the message AND the first name
            LoginResponse response = new LoginResponse("Login Successful", user.getFirstName());
            return ResponseEntity.ok().body(response);
        } else {
            // Handle invalid credentials
            ErrorResponse errorResponse = new ErrorResponse("Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
