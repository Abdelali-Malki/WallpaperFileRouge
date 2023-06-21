package com.wallpaper71.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.wallpaper71.fragment.PagerView;
import com.wallpaper71.model.WallpaperDataAll;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritePresenter {


    PagerView.PageFav pagerView;
    Context context;
    SharedPreferences shared;
    public FavoritePresenter(PagerView.PageFav pagerView,Context context ){
        this.pagerView=pagerView;
        this.context=context;



        pagerView.setupUI();

        pagerView.showSwipeRefresh();
    }



    public void getFavData(){

        pagerView.wallpaperData(getList());
        pagerView.hideSwipeRefresh();

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

        return arrayItems;

    }
}
