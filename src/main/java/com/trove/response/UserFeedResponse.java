package com.trove.response;

import java.util.List;

public class UserFeedResponse {

    private int totalCount;
    private List<PostsResponseUserFeed> imageUrls;

    public UserFeedResponse() {
    }

    public UserFeedResponse(int totalCount, List<PostsResponseUserFeed> imageUrls) {
        this.totalCount = totalCount;
        this.imageUrls = imageUrls;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<PostsResponseUserFeed> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<PostsResponseUserFeed> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
