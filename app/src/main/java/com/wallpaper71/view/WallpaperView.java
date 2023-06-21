package com.wallpaper71.view;

import com.wallpaper71.model.WallpaperDataAll;
import com.wallpaper71.model.WallpaperDataProduct;

import java.util.List;

public interface WallpaperView {
    void setupUI();
    void showSwipeRefresh();
    void hideSwipeRefresh();
    void showMessage(String msg);
    void wallpaperData (List<WallpaperDataAll> wallpaperDataAll, WallpaperDataProduct wallpaperDataProduct);
    void wallByGroup(WallpaperDataProduct wallpaperDataProduct);

}
