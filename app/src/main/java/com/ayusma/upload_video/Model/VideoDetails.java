package com.ayusma.upload_video.Model;


public class VideoDetails {
    private String url,likes, dislike,comments;

    public VideoDetails(String url, String likes, String dislike, String comments) {
        this.url = url;
        this.likes = likes;
        this.dislike = dislike;;
        this.comments = comments;

    }

    public VideoDetails() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislike() {
        return dislike;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
    }

    public String getComments() {
        return comments;
    }


    public void setComments(String comments) {
        this.comments = comments;
    }



}
