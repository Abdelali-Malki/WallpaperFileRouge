package com.wallpaper71.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wallpaper71.R;
import com.wallpaper71.model.WallpaperDataAll;
import com.wallpaper71.view.WallpaperShow;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wallpaper71.config.BaseUrl.baseUrl;
import static com.wallpaper71.config.BaseUrl.productImageDir2;

public class WallpaperPopularAdapter extends RecyclerView.Adapter<WallpaperPopularAdapter.WallpapersHolder> {

    public static List<WallpaperDataAll> wallpaperList;
    Context context;

    String from;
    public WallpaperPopularAdapter(List<WallpaperDataAll> wallpaperList, Context context, String from) {
        this.wallpaperList = wallpaperList;
        this.context = context;
        this.from = from;
    }

    @Override
    public WallpaperPopularAdapter.WallpapersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.wallpaper_item,parent,false);
        WallpaperPopularAdapter.WallpapersHolder mh = new WallpaperPopularAdapter.WallpapersHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(WallpaperPopularAdapter.WallpapersHolder holder, int position) {

        holder.title.setText(wallpaperList.get(position).getName());


        Glide.with(context)
                .asBitmap()
                .load(baseUrl+productImageDir2+wallpaperList.get(position).getPhoto()+".webp")


                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        //imageView.setImageBitmap(resource);
                        holder.wallpaperImg.setImageBitmap(resource);
                        Log.d("resource123",resource+"");

                        holder.placeholder_wall.stopShimmer();
                        holder.placeholder_wall.setVisibility(View.GONE);
                    }
                });
        holder.wallpaperImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String jsonWallpaperList = gson.toJson(wallpaperList);

                if (wallpaperList.size()>0){
                    Intent intent=new Intent(context, WallpaperShow.class);
                    //  intent.putExtra("name", wallpaperList.get(position).getName());
                    intent.putExtra("_id", wallpaperList.get(position).getId()+"");
                    intent.putExtra("wallpaperList", jsonWallpaperList);
                    intent.putExtra("from", "Home");

                    intent.putExtra("action_type","Product-View");
                    context.startActivity(intent);
                }

            }
        });



     /*   switch (position){
            case 0:
                Glide.with(context).load("https://images.pexels.com/photos/1535162/pexels-photo-1535162.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500").into(holder.wallpaperImg);
                break;
            case 1:
                Glide.with(context).load("https://www.imagesjunction.com/images/img/1920x1080_mobile_hd_wallpapers.jpg").into(holder.wallpaperImg);
                break;
            case 2:
                Glide.with(context).load("https://wallpapercave.com/wp/wp5211914.jpg").into(holder.wallpaperImg);
                break;
            case 3:
                Glide.with(context).load("https://wallpapercave.com/wp/wp4782055.jpg").into(holder.wallpaperImg);
                break;
            case 4:
                Glide.with(context).load("https://wallpapercave.com/wp/wp4765691.jpg").into(holder.wallpaperImg);
                break;
            default:
                Glide.with(context).load("https://wallpapercave.com/wp/wp4897873.jpg").into(holder.wallpaperImg);

        }*/

    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public class WallpapersHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.wallpaperImg)
        ImageView wallpaperImg;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.placeholder_wall)
        ShimmerFrameLayout placeholder_wall;

        public WallpapersHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}

