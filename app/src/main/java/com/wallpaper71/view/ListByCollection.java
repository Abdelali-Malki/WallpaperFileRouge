package com.wallpaper71.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.wallpaper71.R;
import com.wallpaper71.adapter.WallpaperListAdapter;
import com.wallpaper71.model.WallpaperDataAll;
import com.wallpaper71.model.WallpaperDataProduct;
import com.wallpaper71.presenter.WallpaperPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListByCollection extends AppCompatActivity implements WallpaperView {
    ProgressDialog progressDialog;
    List<WallpaperDataAll> allWallpaperDataList;
    WallpaperListAdapter wallpaperListAdapter;

    @BindView(R.id.itemsRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    WallpaperPresenter wallpaperPresenter;
    String collectionName,collectionId,dataFrom,_url,from,action_type;
    boolean refresh=false;
    String nextPage="";
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_by_collection);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        action_type=intent.getStringExtra("action_type");
        from=intent.getStringExtra("from");
        dataFrom=intent.getStringExtra("data_from");
        collectionName=intent.getStringExtra("name");
        collectionId=intent.getStringExtra("id");
        //Toast.makeText(this, ""+collectionId, Toast.LENGTH_SHORT).show();
        setTitle(collectionName);
        wallpaperPresenter=new WallpaperPresenter(this);

         //Log.d("collectionId",collectionId);
//         Log.d("collectionName",collectionName);
         //wallpaperPresenter.saveSta()


        if (dataFrom.equals("collection")){
            wallpaperPresenter.saveSta(action_type,collectionId,from);
           _url= "collections/"+collectionId;
        }else if (dataFrom.equals("color")){

            wallpaperPresenter.saveSta(collectionId,"0",from);
            _url="color/"+collectionId;
        }else if (dataFrom.equals("group")){
            wallpaperPresenter.saveSta(collectionId,"0",from);
            _url="products/"+collectionId;
        }else if (dataFrom.equals("search")){
            wallpaperPresenter.saveSta(action_type,collectionId,from);
            _url="search/"+collectionName;
        }
       // Log.d("allWall_url2",_url+"");
        init();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refresh=true;
                init();
            }
        });



        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                   // Toast.makeText(ListByCollection.this, _url, Toast.LENGTH_SHORT).show();
                   // allWallpaperDataList.addAll(allWallpaperDataList); _url="products/"+collectionId;
                //    wallpaperListAdapter.notifyDataSetChanged();recent/3/1
                    refresh=false;
                    page++;
                    mSwipeRefreshLayout.setRefreshing(true);

                    if (dataFrom.equals("collection")){
                        wallpaperPresenter.getWallpaperDataByCollectio(_url+"/30"+"/"+page);
                    }else if (dataFrom.equals("group")){
                        if (collectionId.equals("popular")){
                            wallpaperPresenter.getWallpaperDataByGroup(_url+"/30"+"/30"+"/"+page);
                        }else {
                            wallpaperPresenter.getWallpaperDataByGroup(_url+"/30"+"/"+page);
                        }

                    }else if (dataFrom.equals("search")){
                        wallpaperPresenter.getWallpaperDataByGroup(_url+"/30"+"/"+page);
                    }

                }
            }
        });
    }

    public void init(){
        page=1;
        if (dataFrom.equals("collection")){
            Log.d("allWall_url2",_url+"");
            wallpaperPresenter.getWallpaperDataByCollectio(_url);
        }else if (dataFrom.equals("color")){
            wallpaperPresenter.getWallpaperDataByCollectio(_url);
            //wallpaperPresenter.getWallpaperDataByGroup(_url);
        }else if (dataFrom.equals("group")){
            wallpaperPresenter.getWallpaperDataByGroup(_url);
        }else if (dataFrom.equals("search")){
            wallpaperPresenter.getWallpaperDataByGroup(_url);
        }
    }

    @Override
    public void setupUI() {
      //  AdsShow adsShow=new AdsShow(this);
      //  adsShow.showBannerAds(0,150,"");

        allWallpaperDataList=new ArrayList<>();
        wallpaperListAdapter=new WallpaperListAdapter(allWallpaperDataList,this,action_type);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(wallpaperListAdapter);

    }





    @Override
    public void showSwipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);


    }

    @Override
    public void hideSwipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void wallpaperData(List<WallpaperDataAll> wallpaperDataAll, WallpaperDataProduct wallpaperDataProduct) {
        //allWallpaperDataList.clear();
        if (refresh){
            allWallpaperDataList.clear();
        }
        allWallpaperDataList.addAll(wallpaperDataAll);
        Log.d("wallpaperDataAll12",wallpaperDataAll.size()+" dfd");
      //  Collections.shuffle(allWallpaperDataList);
        // Toast.makeText(getContext(), "ok"+allWallpaperDataList.size(), Toast.LENGTH_SHORT).show();
        wallpaperListAdapter.notifyDataSetChanged();
    }

    @Override
    public void wallByGroup(WallpaperDataProduct wallpaperDataProduct) {
       // allWallpaperDataList.clear();

        if (refresh){
            allWallpaperDataList.clear();
        }
        nextPage= wallpaperDataProduct.getNextPageUrl();
        allWallpaperDataList.addAll(wallpaperDataProduct.getData());
        // Toast.makeText(getContext(), "ok"+allWallpaperDataList.size(), Toast.LENGTH_SHORT).show();
        wallpaperListAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //  handler.removeCallbacksAndMessages(Update);

                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}