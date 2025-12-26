package com.trove.controller;

import com.trove.exceptions.UserAlreadyExistsException;
import com.trove.model.SignUp;
import com.trove.request.LoginRequest;
import com.trove.request.SignUpRequest;
import com.trove.response.ErrorResponse;
import com.trove.response.LoginResponse;
import com.trove.service.AppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Base URL
public class AppController {


    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            appService.doSignUp(signUpRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");

        } catch (UserAlreadyExistsException e) {
            // Returns 409 CONFLICT if email exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e) {
            // Returns 500 INTERNAL SERVER ERROR for anything else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }

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

//    @GetMapping("/checkemail")
//    public ResponseEntity<String> checkEmail(@RequestParam("email") String email){
//
//        boolean exists = appService.checkEmailExists(email);
//
//        if(exists){
//            return ResponseEntity.ok("Email exists in the database.");
//        }
//        else{
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
//        }
//    }
//
//    @GetMapping("/checkphonenumber")
//    public ResponseEntity<String> checkPhoneNumber(@RequestParam("phoneNumber") String phoneNumber){
//
//        boolean exists = appService.checkPhoneNumberExists(phoneNumber);
//
//        if(exists){
//            return ResponseEntity.ok("Phone number exists in the database.");
//        }
//        else{
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone number not found.");
//        }
//    }
}

