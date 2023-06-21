package com.wallpaper71.config.ads;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.wallpaper71.R;
import com.wallpaper71.config.app_config.ConfigProvider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdsShow {
    Activity activity;
    AdView mAdView;
    ConfigProvider configProvider;
    InterstitialAd mInterstitialAd ;
    public AdsShow(Activity activity){
        this.activity=activity;
         configProvider=new ConfigProvider(activity);
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public void showBannerAds(int top,int botom,String unitId){
        LinearLayout linearLayout =activity.findViewById(R.id.layAllHome);
        if (configProvider.adsShow.equals("ON")&&configProvider.bannerAds.equals("on")){


            setMargins(linearLayout,0,top,0,botom);
           //linearLayout..setMargins
        mAdView = new AdView(activity);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(unitId);

        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        if(mAdView.getAdSize() != null || mAdView.getAdUnitId() != null)
            mAdView.loadAd(adRequest);
        ((LinearLayout)activity.findViewById(R.id.adViewLay)).addView(mAdView);
        }else {
            setMargins(linearLayout,0,top,0,0);
            activity.findViewById(R.id.adViewLay).setVisibility(View.GONE);
        }
    }

    public void showFullScreen() {
        Log.d("adsSta",configProvider.fullAdsId+"");
        if (configProvider.adsShow.equals("ON")&&configProvider.fullAds.equals("on")) {
            MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(activity, configProvider.fullAdsId, adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd;
                            mInterstitialAd.show(activity);
                            // Log.i(TAG, "onAdLoaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            // Log.i(TAG, loadAdError.getMessage());
                            mInterstitialAd = null;
                        }
                    });

        }
    }
}
