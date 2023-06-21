package com.wallpaper71.view.wall_preview;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class SaveImgeStorage   {

    //====
    Button button;
    ProgressDialog progressdialog;
    public static final int Progress_Dialog_Progress = 0;

    URL url;
    URLConnection urlconnection ;
    int FileSize;
    InputStream inputstream;
    OutputStream outputstream;
    byte dataArray[] = new byte[1024];
    long totalSize = 0;

    String GetPath ;


    Activity activity;
    String imageName;
    String folderName;
    String urlStr;
    ProgressBar progressBar;


    public SaveImgeStorage(Activity activity,String urlStr, String imageName,String folderName,ProgressBar progressBar){
        this.activity=activity;
        this.imageName=imageName;
        this.folderName=folderName;
        this.urlStr=urlStr;
        this.progressBar=progressBar;

        DownloadImage2(urlStr);

    }




    void DownloadImage2(String ImageUrl) {

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Log.d("ImageUrl",ImageUrl);
            //Toast.makeText(activity, ImageUrl, Toast.LENGTH_SHORT).show();
            //new ImageDownloadWithProgressDialog().execute(ImageUrl);
            getImage(ImageUrl);
           // new ImageDownloadWithProgressDialog.execute("ImageUrl");
        }




   /*     if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 113);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 114);

        } else {
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a Video "), 420);
        }*/
    }
    void showToast(String msg){
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show();
    }
    public void getImage(String url) {
        progressBar.setVisibility(View.VISIBLE);
        new DownloadFileFromURLTask().execute(url);

        progressdialog = new ProgressDialog(activity);
        progressdialog.setMessage("Downloading Image From Server...");
       // progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressdialog.setCancelable(true);
        progressdialog.show();
        //Toast.makeText(activity, url, Toast.LENGTH_SHORT).show();
        /*final Bitmap[] bm = new Bitmap[1];
        Glide.with(activity)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.color.colorPrimary)
                        .dontAnimate().skipMemoryCache(true))

                .thumbnail( 0.1f )

                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        //imageView.setImageBitmap(resource);
                        Log.d("resource123",resource+"");
                        bm[0] = resource;
                        saveReceivedImage(resource);

                       // progressdialog.dismiss();
                    }
                });*/
    }


    private class DownloadFileFromURLTask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... f_url) {

            InputStream input=null;
            Bitmap bmp = null;

            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                input = new BufferedInputStream(url.openStream(),
                        8192);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                byte data[] = new byte[20480];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    // bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    // writing data to file
                    outputStream.write(data, 0, count);
                }
                byte[] imageData = outputStream.toByteArray();
                bmp = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                outputStream.close();
                // flushing output
                //  output.flush();

                // closing streams
                // output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return bmp;
        }
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            Log.d("printt",progress[0]);
            if (2<=Integer.parseInt(progress[0])){
                progressdialog.dismiss();
            }
            progressBar.setProgress(Integer.parseInt(progress[0]));

        }

        @Override
        protected void onPostExecute(Bitmap file_bm) {
            if (file_bm==null){
                Log.i("printt2", "null");
            }else {
                Log.i("printt2", file_bm.toString());
            }


            saveReceivedImage(file_bm);

            progressBar.setProgress(0);
            progressBar.setVisibility(View.GONE);
           // Toast.makeText(activity, "Check Download/"+folderName+" Folder ", Toast.LENGTH_SHORT).show();

        }

    }



    private void saveReceivedImage(Bitmap bitmap1){
        try {

            Bitmap bitmap = bitmap1;
            //File path = new File(getContext().getFilesDir(), "MyAppName" + File.separator + "Images");

            //File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ "/"+folderName); //Creates app specific folder
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ "/"+"Telshare"); //Creates app specific folder
            Log.d("path11",path.toString());

            Toast.makeText(activity, "Check Download/"+folderName+" Folder ", Toast.LENGTH_SHORT).show();
            if(!path.exists()){
                path.mkdirs();
            }
            Log.d("Bitmap1",path.toString());

            File outFile = new File(path, imageName + "1.png");
            FileOutputStream outputStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();

            openFolder(path.toString());

        } catch (FileNotFoundException e) {
            Log.e("TAG", "Saving received message failed with", e);
        } catch (IOException e) {
            Log.e("TAG", "Saving received message failed with", e);
        }
    }

    public void openFolder(String location)
    {
        Log.d("location12",location);
        // location = "/sdcard/my_folder";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri mydir = Uri.parse("file://"+location);
        intent.setDataAndType(mydir,location);    // or use */*
        activity.startActivity(intent);
    }
}
