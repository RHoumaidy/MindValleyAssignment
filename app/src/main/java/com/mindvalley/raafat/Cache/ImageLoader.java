package com.mindvalley.raafat.Cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by Raafat Alhoumaidy on 2/8/2019 @7:49 PM.
 */
public class ImageLoader extends FileDownloader {

    private int errorDrawableRes;
    private int placeHolderDrawableRes;
    private ImageView imageView;
    public ImageLoader load(String url){
        this.setFileUrl(url);
        return this;
    }

    public ImageLoader setErrorDrawableRes(int errorDrawableRes){
        this.errorDrawableRes = errorDrawableRes;
        return this;
    }

    public ImageLoader setPlaceHolderDrawableRes(int placeHolderDrawableRes){
        this.placeHolderDrawableRes = placeHolderDrawableRes;
        return this;
    }

    public ImageLoader into(ImageView _imageView){
        this.imageView = _imageView;
        this.imageView.setImageBitmap(null);
        setDownloadStateListner(new DownloadStateListner() {
            @Override
            public void onProgressUpdate(int percentage) {

            }

            @Override
            public void onPartialDownload(byte[] partialData) {

            }

            @Override
            public void onSuccess(byte[] data) {
                final Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void onFail(Exception e) {
                
            }

            @Override
            public void onCanceled() {

            }
        });
        return this;
    }

    public ImageLoader using(DownloadStateListner downloadStateListner){
       setDownloadStateListner(downloadStateListner);
       return this;
    }


}
