package com.trove.service;

import com.trove.exceptions.UserAlreadyExistsException;
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

//  Old Logic written for the checking the user is already present or not in the Database

//    private boolean isUserPresentBasedOnEmail(String email){
//        Optional<SignUp> userRetrievedFromDB = signUpRepository.findByEmail(email);
//        if (userRetrievedFromDB.isEmpty()) {
//            return false;
//        }
//        return true;
//    }

// This Logic is written for the checking the user is already present or not in the Database with the help of the email id.
//    Created the User Already Exists Exception which is a Custom Exception to handle the case when the user is already present in the Database.


    public SignUp doSignUp(SignUpRequest signUpRequest){

        if (signUpRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email " + signUpRequest.getEmail() + " is already in use.");
        }
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
        return signUpRepository.save(signupuser);

    }

//    Here we are getting the user credentials and checking if the user is present in the Database or not.
//    If the user is present then we are checking the password is correct or not.
//    If the password is correct then we are returning the user object.
//    If the password is not correct then we are returning null.

    public SignUp doLogin(LoginRequest loginRequest){

        Optional<SignUp> userOptional = signUpRepository.findByEmail(loginRequest.getEmail());

        if(userOptional.isPresent()){
            SignUp loginUser = userOptional.get();

            if(bCryptPasswordEncoder.matches(loginRequest.getPassword(),loginUser.getPassword())){
                return loginUser;
            }
        }
        return null;
    }

//    public boolean checkEmailExists(String email){
//        return signUpRepository.existsByEmail(email);
//    }
//
//    public boolean checkPhoneNumberExists(String phoneNumber){
//        return signUpRepository.existsByPhoneNumber(phoneNumber);
//    }

}
