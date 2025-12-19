package com.trove.response;

public class SignUpResponse extends Response{

    private String message;
    private int photoId;

    public SignUpResponse(){
    }

    public SignUpResponse(String message, int photoId) {
        this.message = message;
        this.photoId = photoId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
