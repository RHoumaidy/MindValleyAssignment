package com.mindvalley.raafat.Cache;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Raafat alhmidi on 2/8/2019 @2:20 PM.
 */
public class FileDownloader extends AsyncTask<Void, Integer, Void> {

    private DownloadStateListner downloadStateListner;
    private byte[] fileData;
    private String fileUrl;
    private String key;

    @NonNull
    public static String hashString(String str) {

        String md5 = "MD5";

        try {
            MessageDigest digest = MessageDigest.getInstance(md5);
            digest.update(str.getBytes());
            byte[] cypherMsg = digest.digest();

            StringBuilder builder = new StringBuilder();
            for (byte b : cypherMsg) {
                String h = Integer.toHexString(0xFF & b);
                while (h.length() < 2)
                    h = "0" + h;
                builder.append(h);
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";

    }


    public void setDownloadStateListner(DownloadStateListner downloadStateListner) {
        this.downloadStateListner = downloadStateListner;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        this.key = hashString(fileUrl);
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        CacheManager.CacheItem cacheItem = CacheManager.getInstance().cacheHit(key);
        if (cacheItem != null) {
            fileData = cacheItem.getData();
            cancel(true);
            downloadStateListner.onSuccess(fileData);
        }
    }


    @Override
    protected Void doInBackground(Void... voids) {
        byte[] res = new byte[0];

        try {
            URL url = new URL(fileUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            long lengthOfFile = urlConnection.getContentLengthLong();
            InputStream inputStream = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = inputStream.read(data)) != -1) {
                total += count;
                publishProgress((int) (total * 100 / lengthOfFile));
                byteArrayOutputStream.write(data, 0, count);
                downloadStateListner.onPartialDownload(byteArrayOutputStream.toByteArray());
            }
            fileData = byteArrayOutputStream.toByteArray();
            downloadStateListner.onSuccess(fileData);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            downloadStateListner.onFail(e);
        } catch (IOException e) {
            e.printStackTrace();
            downloadStateListner.onFail(e);

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        downloadStateListner.onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (fileData != null && fileData.length > 0) {
            CacheManager.getInstance().addItem(key, fileData);
        }
    }

    public void downloadFile(String url, DownloadStateListner downloadStateListner) {
        this.setFileUrl(url);
        this.setDownloadStateListner(downloadStateListner);
        this.execute();
    }

    public void cancelThisRequest() {
        this.cancel(true);
        downloadStateListner.onCanceled();
    }

    public static interface DownloadStateListner {
        void onProgressUpdate(int percentage);

        void onPartialDownload(byte[] partialData);

        void onSuccess(byte[] data);

        void onFail(Exception e);

        void onCanceled();

    }
}
