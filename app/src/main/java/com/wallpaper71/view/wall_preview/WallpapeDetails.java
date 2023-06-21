package com.wallpaper71.view.wall_preview;

import com.wallpaper71.model.WallpaperDataAll;

import java.util.List;

public interface WallpapeDetails {
    void setupUI();
    void showWallpaper(List<WallpaperDataAll> wallpaperDataAll,String current_id);
    void setFab();
    void uncaheckFab();

    void previewWallpaper(String url);
    void shareWallpaper(String url);
    void saveWallpaper(String url);
    void showTost(String msg);


}