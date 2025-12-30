package com.trove.response;

public class LoginResponse {
    private String message;
    private String firstName;
    private String photoUrl;

    public LoginResponse() {
    }

    public LoginResponse(String message, String firstName, String photoUrl) {
        this.message = message;
        this.firstName = firstName;
        this.photoUrl = photoUrl;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
