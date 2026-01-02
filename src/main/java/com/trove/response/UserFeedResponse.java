package com.trove.response;

import java.util.List;

public class UserFeedResponse {

    private int totalcount;
    private int remainingPosts;
    private List<PostsResponseUserFeed> posts;

    public UserFeedResponse() {
    }

    public UserFeedResponse(int totalcount, int remainingPosts, List<PostsResponseUserFeed> posts) {
        this.totalcount = totalcount;
        this.remainingPosts = remainingPosts;
        this.posts = posts;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getRemainingPosts() {
        return remainingPosts;
    }

    public void setRemainingPosts(int remainingPosts) {
        this.remainingPosts = remainingPosts;
    }

    public List<PostsResponseUserFeed> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsResponseUserFeed> posts) {
        this.posts = posts;
    }
}
