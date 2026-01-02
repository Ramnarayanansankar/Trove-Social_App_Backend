package com.trove.response;

import java.util.List;

public class PostsResponseUserFeed {
    private Integer postId;
    private String caption;
    private List<String> imageUrls;
    private String date;

    public PostsResponseUserFeed(){}

    public PostsResponseUserFeed(Integer postId, String caption, List<String> imageUrls, String date) {
        this.postId = postId;
        this.caption = caption;
        this.imageUrls = imageUrls;
        this.date = date;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
