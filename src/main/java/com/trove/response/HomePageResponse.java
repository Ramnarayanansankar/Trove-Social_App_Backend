package com.trove.response;

public class HomePageResponse extends Response{

    private String firstName;
    private String email;
    private String photoUrl;
    private int id;

    public HomePageResponse() {
    }

    public HomePageResponse(String message) {
        super(message);
    }

    public HomePageResponse(String firstName, String email, String photoUrl, int id) {
        this.firstName = firstName;
        this.email = email;
        this.photoUrl = photoUrl;
        this.id = id;
    }

    public HomePageResponse(String message, String firstName, String email, String photoUrl, int id) {
        super(message);
        this.firstName = firstName;
        this.email = email;
        this.photoUrl = photoUrl;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
