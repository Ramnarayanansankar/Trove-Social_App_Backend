package com.trove.response;

public class LoginResponse {
    private String message;
    private String firstName;

    public LoginResponse() {
    }

    public LoginResponse(String message, String firstName) {
        this.message = message;
        this.firstName = firstName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
