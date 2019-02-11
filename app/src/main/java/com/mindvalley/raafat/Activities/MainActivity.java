package com.mindvalley.raafat.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mindvalley.raafat.Adapters.PhotosAdapter;
import com.mindvalley.raafat.Cache.CacheManager;
import com.mindvalley.raafat.Cache.FileDownloader;
import com.mindvalley.raafat.Cache.ImageLoader;
import com.mindvalley.raafat.MyApplication;
import com.mindvalley.raafat.R;
import com.mindvalley.raafat.Views.ProgressHUD;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView pinboardRV;
    private PhotosAdapter photosAdapter;
    public static final String THUMB_TRANS_KEY = "THUMB_TRANS";
    public static final String PROFILE_TRANS_KEY = "PROFILE_TRANS_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pinboardRV = findViewById(R.id.pinboardRV);

        photosAdapter = new PhotosAdapter(this, MyApplication.getInstance().pinBoard.getPhotos());

        pinboardRV.setHasFixedSize(true);
//        pinboardRV.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        pinboardRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pinboardRV.setAdapter(photosAdapter);
        pinboardRV.requestLayout();

        final GestureDetector mGestureDetector =
                new GestureDetector(this,
                        new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onSingleTapUp(MotionEvent e) {
                                return true;
                            }
                        });

        pinboardRV.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    int pos = rv.getChildAdapterPosition(child);
                    MyApplication.getInstance()
                            .pinBoard
                            .setCurrPhoto(
                                    MyApplication.getInstance()
                                            .pinBoard
                                            .getPhotos()
                                            .get(pos)
                            );
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    android.support.v4.util.Pair<View, String> pair1 = android.support.v4.util.Pair.create((View) child.findViewById(R.id.userProfileImgV), child.findViewById(R.id.userProfileImgV).getTransitionName());
                    android.support.v4.util.Pair<View, String> pair2 = Pair.create((View) child.findViewById(R.id.thumbImgV), child.findViewById(R.id.thumbImgV).getTransitionName());

                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, pair1, pair2);
                    intent.putExtra(THUMB_TRANS_KEY,child.findViewById(R.id.thumbImgV).getTransitionName());
                    intent.putExtra(PROFILE_TRANS_KEY,child.findViewById(R.id.userProfileImgV).getTransitionName());

                    startActivity(intent, optionsCompat.toBundle());

                    return true;
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }
}
