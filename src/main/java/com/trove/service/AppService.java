package com.trove.service;

import com.trove.model.SignUp;
import com.trove.repository.SignUpRepository;
import com.trove.request.LoginRequest;
import com.trove.request.SignUpRequest;
import com.trove.response.ErrorResponse;
import com.trove.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppService {

    @Autowired
    private SignUpRepository signUpRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private boolean isUserPresentBasedOnEmail(String email){
        Optional<SignUp> userRetrievedFromDB = signUpRepository.findByEmail(email);
        if (userRetrievedFromDB.isEmpty()) {
            return false;
        }
        return true;
    }
    public Response doSignUp(SignUpRequest signUpRequest){

        if(!isUserPresentBasedOnEmail(signUpRequest.getEmail())){
            SignUp signupuser = new SignUp();
            signupuser.setFirstName(signUpRequest.getFirstName());
            signupuser.setLastName(signUpRequest.getLastName());
            signupuser.setEmail(signUpRequest.getEmail());
            signupuser.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
            signupuser.setPhoneNumber(signUpRequest.getPhoneNumber());
            signupuser.setGender(signUpRequest.getGender());
            signupuser.setDob(signUpRequest.getDob());
            signupuser.setCountry(signUpRequest.getCountry());
            signupuser.setState(signUpRequest.getState());
            signupuser.setCity(signUpRequest.getCity());
            signupuser.setAddress(signUpRequest.getAddress());
            signupuser.setPincode(signUpRequest.getPincode());
            signUpRepository.save(signupuser);
            return new Response("User registered successfully");
        }
        else{
            return new ErrorResponse("Email Id already exist. Try different email Id");
        }
    }

    public SignUp doLogin(LoginRequest loginRequest){

        Optional<SignUp> userOptional = signUpRepository.findByEmail(loginRequest.getEmail());

        if(userOptional.isPresent()){
            SignUp loginUser = userOptional.get();

            if(bCryptPasswordEncoder.matches(loginRequest.getPassword(),loginUser.getPassword())){
                return loginUser;
            }
        } else {
            return null;
        }

    }

}
