package com.trove.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "posts")
public class Posts {

    @Id
    @Column(name = "postid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column
    private int id;

    @Column(name = "postcaption")
    private String postCaption;

    @Column(name = "posttype")
    private String postType;

    @Column(name = "media", columnDefinition = "TEXT")
    private String Media;

    @CreationTimestamp
    @Column(name = "postcreatedtime", updatable = false)
    private String postCreatedTime;

    public Posts(){}

    public Posts(int postId, int id, String postCaption, String postType, String media, String postCreatedTime) {
        this.postId = postId;
        this.id = id;
        this.postCaption = postCaption;
        this.postType = postType;
        Media = media;
        this.postCreatedTime = postCreatedTime;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostCaption() {
        return postCaption;
    }

    public void setPostCaption(String postCaption) {
        this.postCaption = postCaption;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getMedia() {
        return Media;
    }

    public void setMedia(String media) {
        Media = media;
    }

    public String getPostCreatedTime() {
        return postCreatedTime;
    }

    public void setPostCreatedTime(String postCreatedTime) {
        this.postCreatedTime = postCreatedTime;
    }
}
