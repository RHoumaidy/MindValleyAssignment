package com.mindvalley.raafat.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.mindvalley.raafat.Modules.Photo;
import com.mindvalley.raafat.MyApplication;
import com.mindvalley.raafat.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Raafat Alhoumaidy on 2/9/2019 @9:48 AM.
 */
public class SplashActivity extends AppCompatActivity {

    private ImageView logoImgV;
    private Animation logoAnim;

    private Call<List<Photo>> getPhotosRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logoImgV = findViewById(R.id.logoImgV);
        logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo_animation);

        logoImgV.startAnimation(logoAnim);

        getPhotosRequest = MyApplication.getInstance()
                .apiClient.getPhotos();

        getPhotosRequest.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.body() != null) {
                    MyApplication.getInstance().pinBoard.setPhotos(response.body());
                    toMainActivity();
                } else
                    Toast.makeText(SplashActivity.this, "Empty Response !!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Toast.makeText(SplashActivity.this,"Retrying...",Toast.LENGTH_SHORT).show();
                call.clone().enqueue(this);
            }
        });

    }

    private void toMainActivity() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2500);
    }


}
