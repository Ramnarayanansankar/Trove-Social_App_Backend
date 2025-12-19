package com.trove.response;

public class SignUpResponse extends Response{

    private String message;
    private int id;

    public SignUpResponse(){
    }

    public SignUpResponse(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
