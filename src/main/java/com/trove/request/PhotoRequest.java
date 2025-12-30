package com.trove.request;

// This is the DTO Class which is Requesting Class for the asking the server to upload the photourl and email for storing it a database.
// This class I have Written for the firebase Integration. Since we are not using now, I have Commented out.
// Feel free to use it when you are using firebase Integration.

public class PhotoRequest {

    private String photoUrl;

    public PhotoRequest() {
    }

    public PhotoRequest(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
