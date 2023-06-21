package com.wallpaper71.view.wall_preview;

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
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wallpaper71.R;
import com.wallpaper71.model.WallpaperDataAll;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wallpaper71.config.BaseUrl.baseUrl;
import static com.wallpaper71.config.BaseUrl.productImageDir2;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.WallpapersHolder> {

    List<WallpaperDataAll> sliderItems;
    Context context;
    ViewPager2 viewPager2;
    public SliderAdapter(List<WallpaperDataAll> sliderItems, Context context, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.context = context;
        this.viewPager2 = viewPager2;
    }

    @Override
    public SliderAdapter.WallpapersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.wall_item_lay,parent,false);
        SliderAdapter.WallpapersHolder mh = new SliderAdapter.WallpapersHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SliderAdapter.WallpapersHolder holder, int position) {

       // holder.imageSlid.setImageResource(sliderItems.get(position).getImage());
     /*   Glide.with(context).
                load(productImageDir+sliderItems.get(position).getPhoto()).
                into(holder.imageSlid);*/

        holder.imageSlid.setImageResource(0);

        holder.imageSlid.invalidate();
        holder.imageSlid.setImageBitmap(null);
        Glide.with(context)
                .asBitmap()
                .load(baseUrl+productImageDir2+sliderItems.get(position).getPhoto()+"2.webp")


                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        //imageView.setImageBitmap(resource);
                        holder.imageSlid.setImageBitmap(resource);
                        Log.d("resource123",resource+"");

                        holder.placeholder_wall.stopShimmer();
                        holder.placeholder_wall.setVisibility(View.GONE);
                    }
                });

       // holder.title.setText(sliderItems.get(position).getName());
        holder.title.setText(" "+sliderItems.get(position).getTotalViewCount());
        holder.title2.setText(sliderItems.get(position).getName());
             holder.imageSlid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String jsonWallpaperList = gson.toJson(sliderItems);

                Intent intent=new Intent(context, FullScreenView.class);
                intent.putExtra("name", sliderItems.get(position).getName());
                intent.putExtra("_id", sliderItems.get(position).getId()+"");
                intent.putExtra("wallpaperList", jsonWallpaperList);
                intent.putExtra("action_type","Product-View-View");
                intent.putExtra("from","Product-View");
                context.startActivity(intent);

            /*    Intent intent=new Intent(context, FullScreenView.class);
                intent.putExtra("img_url", sliderItems.get(position).getPhoto());
                context.startActivity(intent);*/
            }
        });




    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class WallpapersHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageSlid)
        ImageView imageSlid;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.title2)
        TextView title2;


        @BindView(R.id.placeholder_wall)
        ShimmerFrameLayout placeholder_wall;



        public WallpapersHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}