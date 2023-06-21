package com.wallpaper71.config.app_config;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigProvider {
    Context context;
    SharedPreferences shareSetting;
  public static String appTitle,aboutUsLink,privacyPolicyLink,appShareLink,adsShow,bannerAds,fullAds,bannerAdsId,fullAdsId;
  public static int ads_interval=0;
  public static int full_screen_slider_delay=5;
    public ConfigProvider(Context context){
        this.context=context;
    }
    public void shareSettingInfo(String apptitle,String about_us_link, String app_share_link,String app_privacy_link,int full_screen_slider_delay){
        shareSetting =context.getSharedPreferences("app_config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shareSetting.edit();
        editor.putString("app_title",apptitle);
        editor.putString("about_us_link",about_us_link);
        editor.putString("app_share_link",app_share_link);
        editor.putString("app_privacy_link",app_privacy_link);
        editor.putInt("full_screen_slider_delay",full_screen_slider_delay);
        editor.commit();
        shareSetting();
    }
    public void shareSettingAds(int adsInterval){
        shareSetting =context.getSharedPreferences("app_config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shareSetting.edit();
        editor.putInt("ads_interval",adsInterval);

        editor.commit();
        shareSetting();
    }
    public void shareSettingAdsInfo(String ads_status,String app_open_unit_id, String add_mob_banner_ads_unit_id,String add_mob_banner_ads_status,String add_mob_interstitial_ads_unit_id,String add_mob_interstitial_ads_status){
        shareSetting =context.getSharedPreferences("app_config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shareSetting.edit();
        editor.putString("ads_show",ads_status);
        editor.putString("app_open_unit_id",app_open_unit_id);
        editor.putString("banner_ads_id",add_mob_banner_ads_unit_id);
        editor.putString("banner_ads_status",add_mob_banner_ads_status);
        editor.putString("full_ads_id",add_mob_interstitial_ads_unit_id);
        editor.putString("full_ads_status",add_mob_interstitial_ads_status);
        editor.commit();
        shareSetting();
    }
    public void shareSetting(){
        shareSetting =context.getSharedPreferences("app_config", Context.MODE_PRIVATE);
        appTitle=shareSetting.getString("app_title","");
        aboutUsLink=shareSetting.getString("about_us_link","");
        appShareLink=shareSetting.getString("app_share_link","");
        privacyPolicyLink=shareSetting.getString("app_privacy_link","");
        adsShow=shareSetting.getString("ads_show","");
        bannerAds=shareSetting.getString("banner_ads_status","");
        bannerAdsId=shareSetting.getString("banner_ads_id","");
        fullAds=shareSetting.getString("full_ads_status","");
        fullAdsId=shareSetting.getString("full_ads_id","");
        ads_interval=shareSetting.getInt("ads_interval",0);
        full_screen_slider_delay=shareSetting.getInt("full_screen_slider_delay",5);
    }


}
