package com.mindvalley.raafat.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raafat Alhoumaidy on 2/8/2019 @7:08 PM.
 */
public class User {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String userName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_image")
    @Expose
    private ProfileImage profileImages;
    @SerializedName("links")
    @Expose
    private Link links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileImage getProfileImages() {
        return profileImages;
    }

    public void setProfileImages(ProfileImage profileImages) {
        this.profileImages = profileImages;
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }

    public static class ProfileImage{
        @SerializedName("small")
        @Expose
        private String small;
        @SerializedName("medium")
        @Expose
        private String medium;
        @SerializedName("large")
        @Expose
        private String large;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }

    public static class Link{
        @SerializedName("self")
        @Expose
        private String self;
        @SerializedName("html")
        @Expose
        private String html;
        @SerializedName("photos")
        @Expose
        private String photos;
        @SerializedName("likes")
        @Expose
        private String likes;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getPhotos() {
            return photos;
        }

        public void setPhotos(String photos) {
            this.photos = photos;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }
    }
}
