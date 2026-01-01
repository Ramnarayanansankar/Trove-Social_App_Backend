package com.trove.response;

import java.util.List;

public class PostResponse extends Response{

    private String caption;
    private String postType;
    private List<String> filePaths;
    private int id;

    public PostResponse(){}

    public PostResponse(String message, String caption, String postType, List<String> filePaths, int id) {
        super(message);
        this.caption = caption;
        this.postType = postType;
        this.filePaths = filePaths;
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
