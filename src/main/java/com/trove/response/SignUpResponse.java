package com.trove.response;

public class SignUpResponse extends Response{

    private String message;
    private String email;
    private String photoUrl;

    public SignUpResponse() {
    }

    public SignUpResponse(String message, String email, String photoUrl) {
        this.message = message;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
