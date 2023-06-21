package com.wallpaper71.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.wallpaper71.R;
import com.wallpaper71.config.ads.AdsShow;
import com.wallpaper71.config.app_config.ConfigProvider;
import com.wallpaper71.model.WallpaperDataAll;
import com.wallpaper71.presenter.DetailsPresenter;
import com.wallpaper71.view.wall_preview.FullScreenView;
import com.wallpaper71.view.wall_preview.SaveImgeStorage;
import com.wallpaper71.view.wall_preview.SliderAdapter;
import com.wallpaper71.view.wall_preview.WallpapeDetails;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revosleap.blurrylayout.BlurryLayout;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wallpaper71.config.BaseUrl.baseUrl;
import static com.wallpaper71.config.BaseUrl.imageShareLisk;
import static com.wallpaper71.config.BaseUrl.productImageDir;
import static com.wallpaper71.config.BaseUrl.productImageDir2;

public class WallpaperShow extends AppCompatActivity implements WallpapeDetails {


    public static final int progress_bar_type = 0;
    private ProgressDialog pDialog;


    @BindView(R.id.set_wall_btn)
    ImageView setWallBtn;

    @BindView(R.id.blurLayout)
    BlurryLayout blurLayout;

    @BindView(R.id.viewPagerWallSlider)
    ViewPager2 viewPager2;


    @BindView(R.id.set_fab_btn)
    ImageView setFabBtn;

    @BindView(R.id.full_img)
    ImageView full_img;

    @BindView(R.id.share_img)
    ImageView share_img;

    @BindView(R.id.layBack)
    LinearLayout layBack;

    @BindView(R.id.saveImg)
    ImageView saveImg;

    @BindView(R.id.lottieAnimationView)
    LottieAnimationView lottieAnimationView;

    @BindView(R.id.progressBar1)
    ProgressBar progressBar;

    DetailsPresenter wallpaperPresenter;
    int currentPosition  = 0;
    SharedPreferences shared;
    ArrayList<WallpaperDataAll> wallpaperDataAlls;
    String wallpaperListStr;
    String _currentId,_form,action_type;
    Dialog myDialogWallItem;
    ConfigProvider configProvider;
    AdsShow adsShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_show);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        lottieAnimationView.setVisibility(View.GONE);
        adsShow=new AdsShow(this);
        progressBar.setVisibility(View.GONE);
        configProvider=new ConfigProvider(this);




    Intent intent=getIntent();
        wallpaperListStr=intent.getStringExtra("wallpaperList");
        _currentId=intent.getStringExtra("_id");
        _form=intent.getStringExtra("from");
        action_type=intent.getStringExtra("action_type");

        if (wallpaperListStr != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<WallpaperDataAll>>() {
            }.getType();
            wallpaperDataAlls = gson.fromJson(wallpaperListStr, type);
        }


        wallpaperPresenter=new DetailsPresenter(this,wallpaperDataAlls,_currentId,this);
        wallpaperPresenter.setWallData();

        wallpaperPresenter.saveSta(action_type,_currentId,_form);

        setWallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wallItemPopup();


            }
        });
        setFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallpaperPresenter.setFab(wallpaperDataAlls.get(currentPosition).getId());

            }
        });
        layBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (configProvider.ads_interval>4){
                    adsShow.showFullScreen();
                    configProvider.shareSettingAds(0);
                }else {

                }

                finish();

            }
        });
        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveImgeStorage(WallpaperShow.this,baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto(),"Wall71"+wallpaperDataAlls.get(currentPosition).getId(),"wall71",progressBar);
                wallpaperPresenter.saveSta("Product-Download",wallpaperDataAlls.get(currentPosition).getId()+"",_form);


            }
        });
        full_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String jsonWallpaperList = gson.toJson(wallpaperDataAlls);

                Intent intent=new Intent(WallpaperShow.this, FullScreenView.class);
                intent.putExtra("name", wallpaperDataAlls.get(currentPosition).getName());
                intent.putExtra("_id", wallpaperDataAlls.get(currentPosition).getId()+"");
                intent.putExtra("wallpaperList", jsonWallpaperList);

                intent.putExtra("action_type","Product-View-View");
                intent.putExtra("from","Product-View");
                startActivity(intent);


            }
        });

        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        baseUrl+imageShareLisk+wallpaperDataAlls.get(currentPosition).getId());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                wallpaperPresenter.saveSta("Product-Share",wallpaperDataAlls.get(currentPosition).getId()+"",_form);

            }
        });

    }






    @Override
    public void setupUI() {
        shared = getSharedPreferences("dataSave", MODE_PRIVATE);

        configProvider.shareSettingAds(configProvider.ads_interval+1);
        Log.d("ads_interval",configProvider.ads_interval+"");
       // wallpaperPresenter.setList("favData",wallpaperDataAlls);
    }

    @Override
    public void showWallpaper(List<WallpaperDataAll> wallpaperDataAll,String current_id) {
        final int[] _imgId = {0};

        for (int i=0;i<wallpaperDataAll.size();i++){
            if (String.valueOf(wallpaperDataAll.get(i).getId()).equals(current_id)){
                _imgId[0] =i;
                currentPosition=i;
                wallpaperPresenter.setLayout( baseUrl+productImageDir2+wallpaperDataAll.get(_imgId[0]).getPhoto()+".webp",  blurLayout);
                wallpaperPresenter.checkFab(wallpaperDataAll.get(_imgId[0]).getId());
                wallpaperPresenter.getTotalCount(baseUrl+"api/products/hit/total_view_count/"+wallpaperDataAlls.get(currentPosition).getId());
                //wallpaperPresenter.saveSta("Product-View",wallpaperDataAlls.get(currentPosition).getId()+"","Product-View");

                break;
            }
        }
        viewPager2.setAdapter(new SliderAdapter( wallpaperDataAll,this,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);
        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
               // Toast.makeText(WallpaperShow.this, "tt", Toast.LENGTH_SHORT).show();
                float r=1-Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);


        viewPager2.setCurrentItem(_imgId[0]);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                _imgId[0] =position;
                currentPosition=position;
                wallpaperPresenter.checkFab(wallpaperDataAll.get(_imgId[0]).getId());
                wallpaperPresenter.setLayout( baseUrl+productImageDir2+wallpaperDataAll.get(_imgId[0]).getPhoto()+".webp",  blurLayout);
                wallpaperPresenter.getTotalCount(baseUrl+"api/products/hit/total_view_count/"+wallpaperDataAlls.get(currentPosition).getId());
                wallpaperPresenter.saveSta("Product-View",wallpaperDataAlls.get(currentPosition).getId()+"",_form);

            }
        });
    }
    public void wallItemPopup() {

        View view=getLayoutInflater().inflate(R.layout.wall_item_pop,null);

        myDialogWallItem = new Dialog(WallpaperShow.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        myDialogWallItem.setContentView(view);
        final TextView btnSetWall = (TextView) myDialogWallItem.findViewById(R.id.btnSetWall);
        final TextView btnSetLock = (TextView) myDialogWallItem.findViewById(R.id.btnSetLock);
        final TextView btnSetBoth = (TextView) myDialogWallItem.findViewById(R.id.btnSetBoth);
        final TextView btnShare = (TextView) myDialogWallItem.findViewById(R.id.btnShare);
        final TextView btnSave = (TextView) myDialogWallItem.findViewById(R.id.btnSave);
        final TextView closeTxt = (TextView) myDialogWallItem.findViewById(R.id.closeTxt);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // DownloadImage2(WallpaperShow.this,baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto());

                Log.d("dfsfs",baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto());
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        baseUrl+imageShareLisk+wallpaperDataAlls.get(currentPosition).getId());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                wallpaperPresenter.saveSta("Product-Share",wallpaperDataAlls.get(currentPosition).getId()+"",_form);


            }
        });

        closeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDialogWallItem.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogWallItem.dismiss();
                DownloadImage2(WallpaperShow.this,baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto());
              //  new SaveImgeStorage(WallpaperShow.this,baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto(),"Wall71"+wallpaperDataAlls.get(currentPosition).getId(),"wall71",progressBar);
              //  createFolder();
               // requestStoragePermission();
                wallpaperPresenter.saveSta("Product-Download",wallpaperDataAlls.get(currentPosition).getId()+"",_form);
            }
        });
        btnSetBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogWallItem.dismiss();
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        wallpaperPresenter.saveSta("Set-Both",wallpaperDataAlls.get(currentPosition).getId()+"",_form);
                        wallpaperPresenter.setWallpaper(WallpaperShow.this,baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto(),progressBar,"wall_lock");
                       // wallpaperPresenter.setLockScreen(baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto());

                    }
                }, 0);
            }
        });
        btnSetLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogWallItem.dismiss();
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        wallpaperPresenter.saveSta("Set-Lock-Screen",wallpaperDataAlls.get(currentPosition).getId()+"",_form);
                        wallpaperPresenter.setWallpaper(WallpaperShow.this,baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto(),progressBar,"lock");


                    }
                }, 0);
            }
        });

        btnSetWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogWallItem.dismiss();
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wallpaperPresenter.saveSta("Set-Wallpaper",wallpaperDataAlls.get(currentPosition).getId()+"",_form);
                        wallpaperPresenter.setWallpaper(WallpaperShow.this,baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto(),progressBar,"wall");

                      //  wallpaperPresenter.setWallpaper(baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto());

                    }
                }, 0);
            }
        });
        myDialogWallItem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialogWallItem.show();
    }

    public void openFolder(String location)
    {
        // location = "/sdcard/my_folder";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri mydir = Uri.parse("file://"+location);
        intent.setDataAndType(mydir,getPackageCodePath());    // or use */*
        startActivity(intent);
    }
    private void createFolder(){
        //File path = new File(getContext().getFilesDir(), "MyAppName" + File.separator + "Images");
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ "/"+"Wall71/Photos"); //Creates app specific folder
        Log.d("path11",path.toString());

        //Toast.makeText(activity, "Check Download/Telshare Folder ", Toast.LENGTH_SHORT).show();
        if(!path.exists()){
            path.mkdirs();
        }
        //videoCompress();
        //startMediaCompression();
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)

            return ;

        if (ActivityCompat.shouldShowRequestPermissionRationale(WallpaperShow.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(WallpaperShow.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
    }

    @Override
    public void setFab() {
        wallpaperPresenter.saveSta("Product-Heart",wallpaperDataAlls.get(currentPosition).getId()+"",_form);
        setFabBtn.setImageResource(R.drawable.ic_favorite_24px);
        wallpaperPresenter.getTotalCount(baseUrl+"api/products/hit/total_heart_count/"+wallpaperDataAlls.get(currentPosition).getId());
    }

    @Override
    public void uncaheckFab() {
        setFabBtn.setImageResource(R.drawable.ic_favorite_border_24px);
    }


    @Override
    public void setWallpaper(Bitmap bitmap) {

    }
    @Override
    public void previewWallpaper(String url) {

    }

    @Override
    public void shareWallpaper(String url) {

    }

    @Override
    public void saveWallpaper(String url) {

    }

    @Override
    public void showTost(String msg) {
        lottieAnimationView.cancelAnimation();
        lottieAnimationView.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        wallpaperPresenter.getTotalCount(baseUrl+"api/products/hit/total_download_count/"+wallpaperDataAlls.get(currentPosition).getId());

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    void DownloadImage2(Activity activity,String ImageUrl) {

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
           // getImage(ImageUrl);
            progressBar.setVisibility(View.VISIBLE);
           // new DownloadFileFromURLTask().execute(ImageUrl);
            new SaveImgeStorage(WallpaperShow.this,ImageUrl,"Wall71"+wallpaperDataAlls.get(currentPosition).getId(),"wall71",progressBar);

        }




    }




    private class DownloadFileFromURLTask extends AsyncTask<String, String, Bitmap> {

        /**
         * Downloading file in background thread
         * */

        @Override
        protected Bitmap doInBackground(String... f_url) {


//============================================================
            InputStream input=null;
            Bitmap bmp = null;



            Log.i("TAG", "do in background");
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                 input = new BufferedInputStream(url.openStream(),
                         20480);
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

                Log.d("printt33",bmp.toString());
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

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            Log.d("printt",progress[0]);
            progressBar.setProgress(Integer.parseInt(progress[0]));
            //circularProgressBar.setProgress(Integer.parseInt(progress[0]));
            //textProgress1.setText(progress[0]);
            //textProgress2.setText(progress[0]);
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/

        @Override
        protected void onPostExecute(Bitmap file_url) {
            if (file_url==null){
                Log.i("printt2", "null");
            }else {
                Log.i("printt2", file_url.toString());
            }


            // dismiss progressbars after the file was downloaded
          //  downloadLayout.setVisibility(View.GONE);

            // Displaying downloaded image into image view
            // Reading image path from sdcard
        /*    File imgFile = new File("filePath");

            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
                        .getAbsolutePath());
                saveImg.setImageBitmap(myBitmap);
            }
*/

            Toast.makeText(WallpaperShow.this, "Download successful!",
                    Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (configProvider.ads_interval>4){
            adsShow.showFullScreen();
            configProvider.shareSettingAds(0);
        }else {

        }

        finish();

    }

}