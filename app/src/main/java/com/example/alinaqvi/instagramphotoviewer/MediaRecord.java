package com.example.alinaqvi.instagramphotoviewer;

/**
 * Created by alinaqvi on 1/25/15.
 */
public class MediaRecord {
    private String url;
    private String caption;
    private String username;
    private Long likeCount;
    private String userImageUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    @Override
    public String toString() {
        return url + " -- " + caption + " -- " + username + " -- " + likeCount + " -- " + userImageUrl;
    }
}
