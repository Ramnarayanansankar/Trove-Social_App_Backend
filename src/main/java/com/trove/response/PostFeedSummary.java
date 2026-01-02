package com.trove.response;

public interface PostFeedSummary {

    Integer getPostId();
    String getPostCaption();
    String getMedia();
    String getPostCreatedTime();
    Integer getTotalCount();
}
