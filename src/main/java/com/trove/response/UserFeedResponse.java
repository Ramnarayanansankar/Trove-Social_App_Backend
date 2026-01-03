package com.trove.response;

import java.util.List;

public class UserFeedResponse {

    private int totalCount;
    private List<String> imageUrls;

    public UserFeedResponse() {
    }

    public UserFeedResponse(int totalCount, List<String> imageUrls) {
        this.totalCount = totalCount;
        this.imageUrls = imageUrls;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
