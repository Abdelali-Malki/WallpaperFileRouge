package com.wallpaper71.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wallpaper71.R;
import com.wallpaper71.adapter.WallpaperFavAdapter;
import com.wallpaper71.model.WallpaperDataAll;
import com.wallpaper71.presenter.FavoritePresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavorite extends Fragment implements PagerView.PageFav{
    private static FragmentView instant ;
    private View view = null;

    ProgressDialog progressDialog;


    @BindView(R.id.favRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    SharedPreferences shared;
    ArrayList allWallpaperDataList;
    WallpaperFavAdapter wallpaperListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.from(getContext()).inflate(R.layout.fav_view, container, false);
        ButterKnife.bind(this,view);

        init();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                init();
            }
        });
        return view;
    }
    public void init(){
        FavoritePresenter favoritePresenter=new FavoritePresenter(this,getContext());
        favoritePresenter.getFavData();
    }


    public List<WallpaperDataAll> getList() {

        shared =getActivity().getSharedPreferences("dataSave", Context.MODE_PRIVATE);
        List<WallpaperDataAll> arrayItems = null;
        String serializedObject = shared.getString("favData", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<WallpaperDataAll>>() {
            }.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        Log.d("arrayItems",arrayItems.get(0).getPhoto());
        return arrayItems;

    }
    @Override
    public void setupUI() {

        progressDialog=new ProgressDialog(getContext());
        allWallpaperDataList=new ArrayList<>();
        wallpaperListAdapter=new WallpaperFavAdapter(allWallpaperDataList,getContext());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(wallpaperListAdapter);
    }

    @Override
    public void wallpaperData(List<WallpaperDataAll> wallpaperDataAll) {
        allWallpaperDataList.clear();
        allWallpaperDataList.addAll(wallpaperDataAll);
        // Toast.makeText(getContext(), "ok"+allWallpaperDataList.size(), Toast.LENGTH_SHORT).show();
        wallpaperListAdapter.notifyDataSetChanged();
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            init();
        }
        // execute your data loading logic.
    }
}
