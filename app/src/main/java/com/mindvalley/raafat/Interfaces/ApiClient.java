package com.mindvalley.raafat.Interfaces;

import com.mindvalley.raafat.Models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Raafat Alhoumaidy on 10/4/2018 @1:56 PM.
 */
public interface ApiClient {

    @GET("/raw/wgkJgazE")
    public Call<List<Photo>> getPhotos();


}
