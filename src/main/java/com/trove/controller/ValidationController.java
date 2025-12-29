package com.trove.controller;

import com.trove.response.Response;
import com.trove.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/checkEmail")
    public ResponseEntity<Response> checkEmail(@RequestParam("email") String email){

        boolean exists = validationService.checkEmailExists(email);

        if(exists){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Email Id exists!"));
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Email Id does not exist!"));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/checkphonenumber")
    public ResponseEntity<String> checkPhoneNumber(@RequestParam("phoneNumber") String phoneNumber){

        boolean exists = validationService.checkPhoneNumberExists(phoneNumber);

        if(exists){
            return ResponseEntity.ok("Phone number exists in the database.");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone number not found.");
        }
    }
}
