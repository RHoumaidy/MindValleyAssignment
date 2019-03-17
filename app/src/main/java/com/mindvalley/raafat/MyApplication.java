package com.mindvalley.raafat;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.mindvalley.raafat.Interfaces.ApiClient;
import com.mindvalley.raafat.Models.PinBoard;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Raafat Alhoumaidy on 2/9/2019 @9:37 AM.
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    public Context appContext;
    public ApiClient apiClient;
    public Retrofit retrofit;
    public PinBoard pinBoard;


    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appContext = getApplicationContext();

        pinBoard = new PinBoard();
        retrofit =new Retrofit.Builder()
                .baseUrl("http://pastebin.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiClient = retrofit.create(ApiClient.class);

    }

    public static Intent getOpenWebPage(String url) {
        if (!url.startsWith("http"))
            url = "http://" + url;
        Uri uri = Uri.parse(url);
        if (url.contains("facebook.com")) {
            try {
                ApplicationInfo applicationInfo = getInstance()
                        .getApplicationContext()
                        .getPackageManager()
                        .getApplicationInfo("com.facebook.katana", 0);
                if (applicationInfo.enabled) {
                    // http://stackoverflow.com/a/24547437/1048340
                    uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                }
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

}
