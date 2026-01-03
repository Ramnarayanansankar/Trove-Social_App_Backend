package com.trove.response;

import java.util.List;

public class UserFeedResponse {

    private int totalCount;
    private List<PostsResponseUserFeed> posts;
    private int startIndex;
    private int endIndex;
    private boolean hasMore;

    public UserFeedResponse() {
    }

    public UserFeedResponse(int totalCount, List<PostsResponseUserFeed> posts, int startIndex, int endIndex, boolean hasMore) {
        this.totalCount = totalCount;
        this.posts = posts;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.hasMore = hasMore;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<PostsResponseUserFeed> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsResponseUserFeed> posts) {
        this.posts = posts;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
