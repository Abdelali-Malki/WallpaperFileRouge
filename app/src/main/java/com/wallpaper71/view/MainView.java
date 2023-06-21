package com.wallpaper71.view;

import com.wallpaper71.model.ConfigAdsModel;
import com.wallpaper71.model.ConfigAppInfoModel;
import com.wallpaper71.model.GroupListResponce;
import com.wallpaper71.model.SearchData;

import java.util.List;

public interface MainView {

     interface APIListener{

          void showMessage(String message);
          void adsInfo(ConfigAdsModel configAdsModel);
          void appInfo(ConfigAppInfoModel configAppInfoModel);
          void groupList (List<GroupListResponce> groupListData);

          void searchList (List<SearchData> searchData);

     }
}
