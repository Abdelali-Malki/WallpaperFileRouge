package com.wallpaper71.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
//import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wallpaper71.adapter.SearchListAdapter;
import com.wallpaper71.config.ads.AdsShow;
import com.wallpaper71.config.app_config.ConfigProvider;
import com.wallpaper71.fragment.PagerAdapter;
import com.wallpaper71.model.ConfigAdsModel;
import com.wallpaper71.model.ConfigAppInfoModel;
import com.wallpaper71.model.GroupListResponce;
import com.wallpaper71.model.SearchData;
import com.wallpaper71.presenter.MainPresenter;
import com.ferfalk.simplesearchview.SimpleSearchView;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.wallpaper71.R;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


import static com.wallpaper71.config.BaseUrl.baseUrl;
import static com.wallpaper71.config.BaseUrl.privacyPolicy;



public class MainActivity extends AppCompatActivity implements MainView.APIListener ,  NavigationView.OnNavigationItemSelectedListener {

    private   String TAG = "dialog";
   // private AppBarConfiguration mAppBarConfiguration;
    @BindView(R.id.tab_layout_home) TabLayout tabLayout;
    @BindView(R.id.pagerHome) ViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.searchView) SimpleSearchView searchView;
    @BindView(R.id.layAllHome) LinearLayout layAllHome;

    @BindView(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;
    List <SearchData> searchDataList;
    SearchListAdapter searchListAdapter;


    String stringNoti="null";
    String cat_name="null";

    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);


        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        setSupportActionBar(toolbar);

        MainPresenter mainPresenter =new MainPresenter(this);

        mainPresenter.getAppInfo();
        mainPresenter.getAdsInfo();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        setupViewPager();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //FirebaseMessaging.getInstance().subscribeToTopic("new2");
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        FirebaseMessaging.getInstance().subscribeToTopic("AllUser");

                        // Log and toast

                        Log.d("tokenGet", token);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });



/*
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("update_statut","onInitializationComplete"+initializationStatus);
                mAdView = new AdView(MainActivity.this);
                mAdView.setAdSize(AdSize.BANNER);
                mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

                AdRequest adRequest = new AdRequest.Builder()
                        //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
                if(mAdView.getAdSize() != null || mAdView.getAdUnitId() != null)
                    mAdView.loadAd(adRequest);
                ((LinearLayout)findViewById(R.id.adViewLay)).addView(mAdView);
            }
        });
*/






       /* AdView mAdMobAdView = (AdView) findViewById(R.id.adView);
       // mAdMobAdView.setAdSize(AdSize.BANNER);
      //  mAdMobAdView.setAdUnitId("ca-app-pub-8667166570346134~3660406933");

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdMobAdView.loadAd(adRequest);*/


        searchDataList=new ArrayList<>();
        searchListAdapter=new SearchListAdapter(searchDataList,this);
        LinearLayoutManager linearLayoutManagerColor=new LinearLayoutManager(this);
        linearLayoutManagerColor.setOrientation(LinearLayoutManager.VERTICAL);
        // RecyclerView.LayoutManager mLayoutManagerGroup = new GridLayoutManager(getContext(), 2);
        searchRecyclerView.setLayoutManager(linearLayoutManagerColor);
        searchRecyclerView.setAdapter(searchListAdapter);

        searchView.setOnSearchViewListener(new SimpleSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                searchDataList.clear();

                searchListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSearchViewShownAnimation() {

            }

            @Override
            public void onSearchViewClosedAnimation() {

            }
        });
        searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SimpleSearchView", "Submit:" + query);
                searchDataList.clear();

                searchListAdapter.notifyDataSetChanged();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SimpleSearchView", "Text changed:" + newText);
                mainPresenter.getSearch("search-suggest/"+newText);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {



                    }
                }, 2000);

                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                searchDataList.clear();

                searchListAdapter.notifyDataSetChanged();
                Log.d("SimpleSearchView", "Text cleared");
                return false;
            }


        });
    }

  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
     if (viewPager.getCurrentItem()==0){
         super.onBackPressed();
         finish();
     }else {
         viewPager.setCurrentItem(0);
     }


    }
    public void setupViewPager(){

        //setTitle("");
        List<String> tabList = new ArrayList<>();
        tabList.add("Home");
        tabList.add("Category");
        tabList.add("Favorites");
        for (int i=0;i<tabList.size();i++){
            //tabLayout.addTab(tabLayout.newTab().setText(allCollectionData.get(i).getName()));
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
        }

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#9e2d7b"));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"));

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
      //  viewPager.setOffscreenPageLimit(3);
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void adsInfo(ConfigAdsModel configAdsModel) {
        ConfigProvider configProvider=new ConfigProvider(this);
        configProvider.shareSettingAdsInfo(configAdsModel.getAdsStatus(),configAdsModel.getAppOpenUnitId(),configAdsModel.getAddMobBannerAdsUnitId(),configAdsModel.getAddMobBannerAdsStatus(),configAdsModel.getAddMobInterstitialAdsUnitId(),configAdsModel.getAddMobInterstitialAdsStatus());


        final float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (55 * scale + 0.5f);
        AdsShow adsShow=new AdsShow(this);
        adsShow.showBannerAds(pixels,pixels,configProvider.bannerAdsId);
    }

    @Override
    public void appInfo(ConfigAppInfoModel configAppInfoModel) {
        ConfigProvider configProvider=new ConfigProvider(this);

        configProvider.shareSettingInfo(configAppInfoModel.getAppName(),baseUrl+privacyPolicy,configAppInfoModel.getAppShareLink(),baseUrl+privacyPolicy,configAppInfoModel.getFullScreenSliderDelay());
        setTitle(configProvider.appTitle);
      //  Log.d("appTT",configProvider.appTitle);

    }

    @Override
    public void groupList(List<GroupListResponce> groupListData) {

    }

    @Override
    public void searchList(List<SearchData> searchData) {
        searchDataList.clear();
        searchDataList.addAll(searchData);
        searchListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home1) {
            viewPager.setCurrentItem(0);

        }else  if (id == R.id.nav_cat1) {
            viewPager.setCurrentItem(1);

        }else  if (id == R.id.nav_fav1) {
            viewPager.setCurrentItem(2);

        }else  if (id == R.id.nav_share) {
            ConfigProvider configProvider=new ConfigProvider(this);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    configProvider.appShareLink);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }else  if (id == R.id.nav_rate) {
            ConfigProvider configProvider=new ConfigProvider(this);
            Intent i = new Intent(Intent.ACTION_VIEW);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(configProvider.appShareLink));
            startActivity(browserIntent);
        }else  if (id == R.id.nav_about) {
            ConfigProvider configProvider=new ConfigProvider(this);
            Intent intent=new Intent(MainActivity.this,CodeWebView.class);
            intent.putExtra("url_",configProvider.aboutUsLink);
            startActivity(intent);
        }else  if (id == R.id.nav_privacy) {
            ConfigProvider configProvider=new ConfigProvider(this);
            Intent intent=new Intent(MainActivity.this,CodeWebView.class);
            intent.putExtra("url_",configProvider.privacyPolicyLink);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}