package com.wallpaper71.fragment;

import com.wallpaper71.model.CollectionData;
import com.wallpaper71.model.ColorListData;
import com.wallpaper71.model.GroupListData;
import com.wallpaper71.model.WallpaperDataAll;
import com.wallpaper71.model.WallpaperDataProduct;

import java.util.List;

public interface PagerView {

    void setupUI();
    void showMessage(String message);

    void collectionData (List<CollectionData> allCollection);
    void wallpaperData (List<WallpaperDataAll> wallpaperDataAll, WallpaperDataProduct wallpaperDataProduct);
    void groupList (List<GroupListData> groupListData);
    void colorList (List<ColorListData> groupListData);
    void showProgressDialog();
    void hideProgressDialog();
    void showSwipeRefresh();
    void hideSwipeRefresh();
    void wallByGroup(WallpaperDataProduct wallpaperDataProduct);
    public interface PageFav{
        void setupUI();
        void wallpaperData (List<WallpaperDataAll> wallpaperDataAll);
        void showSwipeRefresh();
        void hideSwipeRefresh();
    }
}
