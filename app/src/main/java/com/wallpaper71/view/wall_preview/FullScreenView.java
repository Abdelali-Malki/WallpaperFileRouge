package com.wallpaper71.view.wall_preview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wallpaper71.R;
import com.wallpaper71.config.app_config.ConfigProvider;
import com.wallpaper71.model.ConfigAdsModel;
import com.wallpaper71.model.ConfigAppInfoModel;
import com.wallpaper71.model.GroupListResponce;
import com.wallpaper71.model.SearchData;
import com.wallpaper71.model.WallpaperDataAll;
import com.wallpaper71.presenter.DetailsPresenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wallpaper71.presenter.MainPresenter;
import com.wallpaper71.view.MainView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wallpaper71.config.BaseUrl.baseUrl;
import static com.wallpaper71.config.BaseUrl.privacyPolicy;
import static com.wallpaper71.config.BaseUrl.productImageDir;
import static com.wallpaper71.config.app_config.ConfigProvider.full_screen_slider_delay;

public class FullScreenView extends AppCompatActivity implements WallpapeDetails, MainView.APIListener {


    @BindView(R.id.set_fab_btn)
    ImageView setFabBtn;

    @BindView(R.id.layClose)
    LinearLayout layClose;

    @BindView(R.id.lay_all)
    LinearLayout lay_all;

    @BindView(R.id.layAllForClick)
    LinearLayout layAllForClick;

    @BindView(R.id.set_wall_btn)
    TextView setWallBtn;
    @BindView(R.id.playbtn)
    TextView playbtn;

    @BindView(R.id.set_lock_btn)
    TextView setLockBtn;

    @BindView(R.id.viewPagerWallSlider)
    ViewPager2 viewPager2;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar;
    DetailsPresenter wallpaperPresenter;
    int currentPosition  = 0;
    SharedPreferences shared;
    ArrayList<WallpaperDataAll> wallpaperDataAlls;
    String wallpaperListStr;
    String _currentId,action_type,_from;

    int playIntarvel=0;
    int playTime=0;
    CountDownTimer countDownTimer;
    public static final int REQUEST_CODE_EXTERNAL_STORAGE = 5;
    String url;
    String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_view);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }





        MainPresenter mainPresenter =new MainPresenter(this);

      //  mainPresenter.getAppInfo();
        ConfigProvider configProvider=new ConfigProvider(this);
        configProvider.shareSetting();
        playIntarvel=full_screen_slider_delay*1000;
        Log.d("playIntarvel12",full_screen_slider_delay+"dfd"+"");
        //setTitle();

    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_CODE_EXTERNAL_STORAGE);
        } else {
           // retrieveLockScreenWallpaper();
        }*/

     /*   Intent intent=getIntent();
        url= intent.getStringExtra("img_url");*/
        Intent intent=getIntent();
        wallpaperListStr=intent.getStringExtra("wallpaperList");
        _currentId=intent.getStringExtra("_id");
        _from=intent.getStringExtra("from");
        action_type=intent.getStringExtra("action_type");
       // wallpaperPresenter.saveSta(action_type,_currentId+"",_from);

        if (wallpaperListStr != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<WallpaperDataAll>>() {
            }.getType();
            wallpaperDataAlls = gson.fromJson(wallpaperListStr, type);
        }


        wallpaperPresenter=new DetailsPresenter(this,wallpaperDataAlls,_currentId,this);
        wallpaperPresenter.setWallData();

        wallpaperPresenter.saveSta("Product-View-View",wallpaperDataAlls.get(currentPosition).getId()+"",_from);
        setWallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallpaperPresenter.saveSta(" Set-Wallpaper",wallpaperDataAlls.get(currentPosition).getId()+"","Product-View-View");
               // wallpaperPresenter.setWallpaper(baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto());
                wallpaperPresenter.setWallpaper(FullScreenView.this,baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto(),progressBar,"wall");

            }
        });
        setLockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallpaperPresenter.saveSta(" Set-Lock-Screen",wallpaperDataAlls.get(currentPosition).getId()+"","Product-View-View");
                wallpaperPresenter.setWallpaper(FullScreenView.this,baseUrl+productImageDir+wallpaperDataAlls.get(currentPosition).getPhoto(),progressBar,"lock");

            }
        });

        setFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallpaperPresenter.setFab(wallpaperDataAlls.get(currentPosition).getId());

            }
        });
        layClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();

            }
        });
        layAllForClick.setVisibility(View.GONE);
        layAllForClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FullScreenView.this, "okok", Toast.LENGTH_SHORT).show();
                playTime=0;
                countDownTimer.cancel();
                layAllForClick.setVisibility(View.GONE);
                lay_all.setVisibility(View.VISIBLE);





                AlphaAnimation   animation1 = new AlphaAnimation(0.0f, 1.0f);
                animation1.setDuration(1000);
                animation1.setStartOffset(1000);



                lay_all.startAnimation(animation1);


            }
        });

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("getAppInfo22",playIntarvel+" dfd");
               // _imgId[0] =5;
                layAllForClick.setVisibility(View.VISIBLE);
                // playIntarvel=3000;
                 playTime=(wallpaperDataAlls.size()*playIntarvel);
               // wallpaperPresenter.checkFab(wallpaperDataAll.get(_imgId[0]).getId());
              //  wallpaperPresenter.getTotalCount(baseUrl+"api/products/hit/total_view_count/"+wallpaperDataAlls.get(currentPosition).getId());
              //  wallpaperPresenter.saveSta("Product-View",wallpaperDataAlls.get(currentPosition).getId()+"","Product-View-View");
               // currentPosition=currentPosition+1;
                viewPager2.setCurrentItem(currentPosition);


                lay_all.setVisibility(View.INVISIBLE);
                AlphaAnimation   animation1 = new AlphaAnimation(1.0f, 0.0f);
                animation1.setDuration(1000);
                animation1.setStartOffset(1000);
                lay_all.startAnimation(animation1);


                 countDownTimer =  new CountDownTimer(900000000, playIntarvel) {

                    public void onTick(long millisUntilFinished) {
                        viewPager2.setCurrentItem(currentPosition);
                        currentPosition=currentPosition+1;

                        Log.d("playTime22",playTime+"");
                        if (currentPosition+1>wallpaperDataAlls.size()){

                            currentPosition=0;
                            playTime=playTime+(wallpaperDataAlls.size()*playIntarvel);
                           // countDownTimer.onFinish();
                           // countDownTimer.start();
                        }
                        Log.d("currentPosition11",currentPosition+"");
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        layAllForClick.setVisibility(View.GONE);
                        lay_all.setVisibility(View.VISIBLE);
                        Log.d("currentPosition11F",currentPosition+"Finish");
                       // viewPager2.setCurrentItem(currentPosition);
                    }

                }.start();


            }
        });
    }

    @Override
    public void setupUI() {
        shared = getSharedPreferences("dataSave", MODE_PRIVATE);
    }

    @Override
    public void showWallpaper(List<WallpaperDataAll> wallpaperDataAll, String current_id) {
        final int[] _imgId = {0};

        for (int i=0;i<wallpaperDataAll.size();i++){
            if (String.valueOf(wallpaperDataAll.get(i).getId()).equals(current_id)){
                _imgId[0] =i;
                currentPosition=i;
                wallpaperPresenter.checkFab(wallpaperDataAll.get(_imgId[0]).getId());
                wallpaperPresenter.getTotalCount(baseUrl+"api/products/hit/total_view_count/"+wallpaperDataAlls.get(currentPosition).getId());
               // wallpaperPresenter.saveSta("Product-View",wallpaperDataAlls.get(currentPosition).getId()+"","Product-View-View");
                break;
            }
        }
        viewPager2.setAdapter(new FullScreenAdapter( wallpaperDataAll,this,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
       // viewPager2.setOffscreenPageLimit(1);
      //  viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(100));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

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
                wallpaperPresenter.getTotalCount(baseUrl+"api/products/hit/total_view_count/"+wallpaperDataAlls.get(currentPosition).getId());
                wallpaperPresenter.saveSta("Product-View",wallpaperDataAlls.get(currentPosition).getId()+"","Product-View-View");
            }
        });
    }

    @Override
    public void setFab() {
        wallpaperPresenter.saveSta(" Product-Heart",wallpaperDataAlls.get(currentPosition).getId()+"","Product-View-View");
        setFabBtn.setImageResource(R.drawable.ic_favorite_24px);
        wallpaperPresenter.getTotalCount(baseUrl+"api/products/hit/total_heart_count/"+wallpaperDataAlls.get(currentPosition).getId());

    }

    @Override
    public void uncaheckFab() {
        setFabBtn.setImageResource(R.drawable.ic_favorite_border_24px);
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        wallpaperPresenter.getTotalCount(baseUrl+"api/products/hit/total_download_count/"+wallpaperDataAlls.get(currentPosition).getId());

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void adsInfo(ConfigAdsModel configAdsModel) {

    }

    @Override
    public void appInfo(ConfigAppInfoModel configAppInfoModel) {
        ConfigProvider configProvider=new ConfigProvider(this);

        configProvider.shareSettingInfo(configAppInfoModel.getAppName(),baseUrl+privacyPolicy,configAppInfoModel.getAppShareLink(),baseUrl+privacyPolicy,configAppInfoModel.getFullScreenSliderDelay());
        playIntarvel=configAppInfoModel.getFullScreenSliderDelay()*1000;

        Log.d("getAppInfo223",configAppInfoModel.getFullScreenSliderDelay()+" dfd");
    }

    @Override
    public void groupList(List<GroupListResponce> groupListData) {

    }

    @Override
    public void searchList(List<SearchData> searchData) {

    }
}