package com.wallpaper71.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wallpaper71.api_service.AllWallpaperService;
import com.wallpaper71.model.SaveSataResponce;
import com.wallpaper71.model.TotalCountResponce;
import com.wallpaper71.model.WallpaperDataAll;

import com.wallpaper71.view.wall_preview.WallpapeDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revosleap.blurrylayout.BlurryLayout;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPresenter {


    WallpapeDetails pagerView;
    Context context;
    SharedPreferences shared;
    String current_id;
    List<WallpaperDataAll> wallpaperDataAllList;
    AllWallpaperService allWallpaperService=null;
    public DetailsPresenter(WallpapeDetails pagerView, List<WallpaperDataAll> wallpaperDataAllList, String current_id, Context context ){
        this.pagerView=pagerView;
        this.context=context;
        this.wallpaperDataAllList=wallpaperDataAllList;
        this.current_id=current_id;


        if (this.allWallpaperService==null){

            this.allWallpaperService=new AllWallpaperService();

        }

        pagerView.setupUI();



    }

    public void  setWallData(){
        pagerView.showWallpaper(wallpaperDataAllList,current_id);
    }
    public void saveSta(String ProductView,String action_id,String source_page ){
        Log.d("dsfsf",ProductView+" ; "+action_id+" ; "+source_page);
        allWallpaperService
                .getAllCategoryAPI()
                .postSaveSta(ProductView,action_id,"Android",source_page)
                .enqueue(new Callback<SaveSataResponce>() {
                    @Override
                    public void onResponse(Call<SaveSataResponce> call, Response<SaveSataResponce> response) {
                   //     Log.d("getAppInfo",response.body().getStatus()+" dfd");
                        if (response.code()==200){

                            // mainView.appInfo(response.body());
                        }else {

                        }


                    }

                    @Override
                    public void onFailure(Call<SaveSataResponce> call, Throwable t) {
                        Log.d("CategoryResponceff",t.getMessage()+" cat");

                    }
                });


    }
    public void setFab(int _id){
        List<WallpaperDataAll> wallpaperSavedData= getList();


        boolean fabFlag=true;
        if (wallpaperSavedData!=null) {
            for (int i = 0; i < wallpaperSavedData.size(); i++) {

                if (wallpaperSavedData.get(i).getId() == _id) {

                    wallpaperSavedData.remove(i);
                    Log.d("fabId", _id + "");
                    setList("favData", wallpaperSavedData);
                    pagerView.uncaheckFab();
                    fabFlag = false;
                    break;
                }
            }

        if (fabFlag){
            for (int j=0;j<wallpaperDataAllList.size();j++){
                if ((wallpaperDataAllList.get(j).getId()==_id)) {

                    wallpaperSavedData.add(wallpaperDataAllList.get(j));
                    setList("favData",wallpaperSavedData);
                    pagerView.setFab();
                    Log.d("fabId2",_id+"");
                }
            }
        }
        }
    }
    public void getTotalCount(String url){
        allWallpaperService
                .allWallpaperAPI()
                .totalCount(url)
                .enqueue(new Callback<TotalCountResponce>() {
                    @Override
                    public void onResponse(Call<TotalCountResponce> call, Response<TotalCountResponce> response) {
                        Log.d("allWallpaperData223",response+" dfd");
                        if (response.code()==200){
                            Log.d("allWallpaperData223",response+" dfd");

                        }else {


                        }


                    }

                    @Override
                    public void onFailure(Call<TotalCountResponce> call, Throwable t) {
                        Log.d("allWallpaperData223",t.getMessage()+" err");
                    }
                });


    }
    public void checkFab(int _id){
        List<WallpaperDataAll> wallpaperSavedData= getList();


        boolean fabFlag=true;
        if (wallpaperSavedData!=null){
        for (int i=0;i<wallpaperSavedData.size();i++){

            if (wallpaperSavedData.get(i).getId()==_id){

                pagerView.setFab();
                fabFlag=false;
                break;
            }
        }
        if (fabFlag){
            pagerView.uncaheckFab();
        }
        }
    }


    public void setLayout(String url, BlurryLayout blurLayout) {
        final Bitmap[] bm = new Bitmap[1];
        Glide.with(context)
                .asBitmap()
                .load(url)
                .thumbnail( 0.1f )
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                        blurLayout.setBitmapBlur(resource);
                    }
                });



    }
    public void setWallpaper(Activity activity,String url , ProgressBar progressBar,String wall_lock) {
        DownloadFileFromURLTask downloadFileFromURLTask=  new DownloadFileFromURLTask(activity,url,progressBar,wall_lock);
        downloadFileFromURLTask.execute(url);
    }
    private class DownloadFileFromURLTask extends AsyncTask<String, String, Bitmap> {
        Activity activity;
        String url;
        String wall_lock;
        ProgressBar progressBar;
        ProgressDialog progressdialog;
        public DownloadFileFromURLTask(Activity activity,String url,ProgressBar progressBar,String wall_lock){
            this.url = url;
            this.progressBar = progressBar;
            this.activity = activity;
            this.wall_lock = wall_lock;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressdialog = new ProgressDialog(activity);
            progressdialog.setMessage("Downloading Image From Server...");
            // progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressdialog.setCancelable(true);
            progressdialog.show();

            // Do something like display a progress bar
        }
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

            Log.i("wall_lock2",wall_lock);
            if (wall_lock.equals("wall")){
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);

                //Bitmap wallpaper = ((BitmapDrawable) drawable).getBitmap();
                try {

                   // wallpaperManager.setBitmap(file_bm);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(file_bm, null,true,WallpaperManager.FLAG_SYSTEM);

                    }

                    pagerView.showTost("Home Wallpaper Set Successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(wall_lock.equals("lock")){
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);

                //Bitmap wallpaper = ((BitmapDrawable) drawable).getBitmap();
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(file_bm, null,true,WallpaperManager.FLAG_LOCK);
                        pagerView.showTost("Lock Wallpaper Set Successfully");
                    }else {
                        wallpaperManager.setBitmap(file_bm);
                    }
                    // wallpaperManager.setBitmap(resource);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(wall_lock.equals("wall_lock")){
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);

                //Bitmap wallpaper = ((BitmapDrawable) drawable).getBitmap();
                try {
                    wallpaperManager.setBitmap(file_bm);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(file_bm, null,true,WallpaperManager.FLAG_LOCK);
                        pagerView.showTost("Wallpaper Set Successfully");
                    }else {
                        wallpaperManager.setBitmap(file_bm);
                    }
                    // wallpaperManager.setBitmap(resource);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {

            }
          //  saveReceivedImage(file_bm);

            progressBar.setProgress(0);
            progressBar.setVisibility(View.GONE);
            // Toast.makeText(activity, "Check Download/"+folderName+" Folder ", Toast.LENGTH_SHORT).show();

        }


    }

    public void setImage(String url, ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }

    public List<WallpaperDataAll> getList() {
        shared =context.getSharedPreferences("dataSave", Context.MODE_PRIVATE);
        List<WallpaperDataAll> arrayItems = new ArrayList<>();
        String serializedObject = shared.getString("favData", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<WallpaperDataAll>>() {
            }.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
//        Log.d("arrayItems",arrayItems.get(0).getPhoto());
        return arrayItems;
    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        setSharePre(key, json);
    }

    public void setSharePre(String key, String value){
        shared =context.getSharedPreferences("dataSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shared.edit();
        editor.putString(key, value);
        editor.commit();
    }


}
