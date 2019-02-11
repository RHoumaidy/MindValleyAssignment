package com.mindvalley.raafat.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.mindvalley.raafat.Cache.FileDownloader;
import com.mindvalley.raafat.Cache.ImageLoader;
import com.mindvalley.raafat.MyApplication;
import com.mindvalley.raafat.R;

import java.util.Locale;

/**
 * Created by Raafat Alhoumaidy on 2/11/2019 @10:19 AM.
 */
public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preview_layout);

        final SubsamplingScaleImageView previewImgV = findViewById(R.id.previewImgV);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final TextView progressTxtV = findViewById(R.id.progressTxtV);
        new ImageLoader()
                .load(MyApplication.getInstance().pinBoard.getCurrPhoto().getUrls().getFull())
                .using(new FileDownloader.DownloadStateListner() {
                    @Override
                    public void onProgressUpdate(int percentage) {
                        progressTxtV.setText(String.format(Locale.US,"%d%%",percentage));
                    }

                    @Override
                    public void onPartialDownload(byte[] partialData) {

                    }

                    @Override
                    public void onSuccess(byte[] data) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                        previewImgV.setImage(ImageSource.bitmap(bitmap));
                        progressBar.setVisibility(View.GONE);
                        progressTxtV.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFail(Exception e) {

                    }

                    @Override
                    public void onCanceled() {

                    }
                });
    }
}
