package com.mindvalley.raafat.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindvalley.raafat.Adapters.CategoriesAdapter;
import com.mindvalley.raafat.Cache.ImageLoader;
import com.mindvalley.raafat.MyApplication;
import com.mindvalley.raafat.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Raafat Alhoumaidy on 2/9/2019 @11:57 AM.
 */
public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_layout);
        ImageView previewImgV = findViewById(R.id.previewImgV);
        CircleImageView userProfileimgV = findViewById(R.id.userProfileImgV);
        TextView userNameTxtV = findViewById(R.id.userNameTxtV);
        TextView likesTxtV = findViewById(R.id.likesTxtV);
        ImageView openWebImgV = findViewById(R.id.openWebImgV);
        ImageView fullScreenImgV = findViewById(R.id.fullScreenImgV);
        RecyclerView categoriesRV = findViewById(R.id.categoriesRV);

        previewImgV.setTransitionName(getIntent().getExtras().getString(MainActivity.THUMB_TRANS_KEY));
        userProfileimgV.setTransitionName(getIntent().getExtras().getString(MainActivity.PROFILE_TRANS_KEY));

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, MyApplication.getInstance().pinBoard.getCurrPhoto().getCategories());
        categoriesRV.setAdapter(categoriesAdapter);
        categoriesRV.setHasFixedSize(false);
        categoriesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        userNameTxtV.setText(MyApplication.getInstance().pinBoard.getCurrPhoto().getUser().getName());
        likesTxtV.setText(MyApplication.getInstance().pinBoard.getCurrPhoto().getLikes() + "");
        if (MyApplication.getInstance().pinBoard.getCurrPhoto().isLikedByUser())
            likesTxtV.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_favorite_red_24dp, 0, 0, 0);
        new ImageLoader()
                .load(MyApplication.getInstance().pinBoard.getCurrPhoto().getUrls().getThumb())
                .into(previewImgV)
                .execute();

        new ImageLoader()
                .load(MyApplication.getInstance().pinBoard.getCurrPhoto().getUser().getProfileImages().getMedium())
                .into(userProfileimgV)
                .execute();

        openWebImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MyApplication.getOpenWebPage(MyApplication.getInstance().pinBoard.getCurrPhoto().getLink().getHtml()));
            }
        });

        userProfileimgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MyApplication.getOpenWebPage(MyApplication.getInstance().pinBoard.getCurrPhoto().getUser().getLinks().getHtml()));
            }
        });

        fullScreenImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsActivity.this,PreviewActivity.class));
            }
        });


    }
}
