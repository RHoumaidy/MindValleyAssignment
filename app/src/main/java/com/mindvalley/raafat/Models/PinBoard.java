package com.mindvalley.raafat.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raafat Alhoumaidy on 2/8/2019 @7:36 PM.
 */
public class PinBoard {

    private List<Photo> photos = new ArrayList<>();
    private Photo currPhoto;

    public Photo getCurrPhoto() {
        return currPhoto;
    }

    public void setCurrPhoto(Photo currPhoto) {
        this.currPhoto = currPhoto;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
