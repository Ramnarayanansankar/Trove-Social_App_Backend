package com.trove.response;

import java.util.List;

public class UserHomepageResponse{

    private int totalcount;
    private List<String> media;

    public UserHomepageResponse(){}

    public UserHomepageResponse(int totalcount, List<String> media) {
        this.totalcount = totalcount;
        this.media = media;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }

}
