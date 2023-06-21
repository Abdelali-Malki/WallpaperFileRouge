package com.wallpaper71.presenter;

import android.util.Log;

import com.wallpaper71.api_service.AllWallpaperService;
import com.wallpaper71.model.ConfigAdsModel;
import com.wallpaper71.model.ConfigAppInfoModel;
import com.wallpaper71.model.SaveSataResponce;
import com.wallpaper71.model.SearchData;
import com.wallpaper71.model.SearchDataResponce;
import com.wallpaper71.view.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wallpaper71.config.app_config.BaseText.errorMsg;

public class MainPresenter {

    AllWallpaperService allWallpaperService=null;
    MainView.APIListener mainView;
    public MainPresenter(MainView.APIListener mainView ){
        this.mainView=mainView;

        if (this.allWallpaperService==null){
            this.allWallpaperService=new AllWallpaperService();
        }


    }


    public void getSearch(String url_){
        allWallpaperService
                .getAllCategoryAPI()
                .getSearch(url_)
                .enqueue(new Callback<SearchDataResponce>() {
                    @Override
                    public void onResponse(Call<SearchDataResponce> call, Response<SearchDataResponce> response) {

                        //   List <CollectionData> collectionData=response.body();
                        //  mainView.collectionData(collectionData);

                        if (response.code()==200){

                            List <SearchData> searchData=response.body().getData();
                            Log.d("allWallpaperDataSearch",searchData.size()+" dfd");
                            mainView.searchList(searchData);

                        }else {

                          //  mainView.showMessage("Page Not Found");
                        }


                    }

                    @Override
                    public void onFailure(Call<SearchDataResponce> call, Throwable t) {
                        Log.d("CategoryResponceff",t.getMessage()+" cat");
                        mainView.showMessage(errorMsg);

                    }
                });


    }

    public void getAppInfo(){
        allWallpaperService
                .getAllCategoryAPI()
                .getAppInfo()
                .enqueue(new Callback<ConfigAppInfoModel>() {
                    @Override
                    public void onResponse(Call<ConfigAppInfoModel> call, Response<ConfigAppInfoModel> response) {
                       // Log.d("getAppInfo",response.body().getFullScreenSliderDelay()+" dfd");
                        if (response.code()==200){

                            mainView.appInfo(response.body());
                        }else {

                        }


                    }

                    @Override
                    public void onFailure(Call<ConfigAppInfoModel> call, Throwable t) {
                        Log.d("CategoryResponceff",t.getMessage()+" cat");
                        mainView.showMessage(errorMsg);

                    }
                });


    }
    public void getAdsInfo(){
        allWallpaperService
                .getAllCategoryAPI()
                .getAdsInfo()
                .enqueue(new Callback<ConfigAdsModel>() {
                    @Override
                    public void onResponse(Call<ConfigAdsModel> call, Response<ConfigAdsModel> response) {

                        if (response.code()==200){
                            mainView.adsInfo(response.body());

                        }else {

                        }


                    }

                    @Override
                    public void onFailure(Call<ConfigAdsModel> call, Throwable t) {
                        Log.d("CategoryResponceff",t.getMessage()+" cat");
                        mainView.showMessage(errorMsg);

                    }
                });


    }

    public void saveSta(String ProductView,String action_id,String source_page ){
        allWallpaperService
                .getAllCategoryAPI()
                .postSaveSta(ProductView,action_id,"Android",source_page)
                .enqueue(new Callback<SaveSataResponce>() {
                    @Override
                    public void onResponse(Call<SaveSataResponce> call, Response<SaveSataResponce> response) {
                        Log.d("getAppInfo",response.body().getStatus()+" dfd");
                        if (response.code()==200){

                           // mainView.appInfo(response.body());
                        }else {

                        }


                    }

                    @Override
                    public void onFailure(Call<SaveSataResponce> call, Throwable t) {
                        Log.d("CategoryResponceff",t.getMessage()+" cat");
                        mainView.showMessage(errorMsg);

                    }
                });


    }
}
