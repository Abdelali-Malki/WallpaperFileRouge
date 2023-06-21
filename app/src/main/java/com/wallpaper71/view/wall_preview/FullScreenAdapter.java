package com.wallpaper71.view.wall_preview;

import android.content.Context;
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
import com.revosleap.blurrylayout.BlurryLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wallpaper71.config.BaseUrl.baseUrl;
import static com.wallpaper71.config.BaseUrl.productImageDir2;

public class FullScreenAdapter extends RecyclerView.Adapter<FullScreenAdapter.WallpapersHolder> {

    List<WallpaperDataAll> sliderItems;
    Context context;
    ViewPager2 viewPager2;
    public FullScreenAdapter(List<WallpaperDataAll> sliderItems, Context context, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.context = context;
        this.viewPager2 = viewPager2;
    }

    @Override
    public FullScreenAdapter.WallpapersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_full,parent,false);
        FullScreenAdapter.WallpapersHolder mh = new FullScreenAdapter.WallpapersHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(FullScreenAdapter.WallpapersHolder holder, int position) {

        // holder.imageSlid.setImageResource(sliderItems.get(position).getImage());
        //Glide.with(context).load(productImageDir+sliderItems.get(position).getPhoto()).into(holder.imageSlid);
       // Glide.with(context).load(productImageDir2+sliderItems.get(position).getPhoto()+"2.webp").into(holder.imageSlid);
        // holder.title.setText(sliderItems.get(position).getName());
        //holder.title.setText("Recent");
        holder.imageSlid.setImageResource(0);
        holder.imageSlid.invalidate();
        holder.imageSlid.setImageBitmap(null);
        Glide.with(context)
                .asBitmap()
                .load(baseUrl+productImageDir2+sliderItems.get(position).getPhoto()+"2.webp")

                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                        holder.imageSlid.setImageBitmap(resource);
                        Log.d("resource123",resource+"");

                    }
                });


        setLayout( baseUrl+productImageDir2+sliderItems.get(position).getPhoto()+"0.webp",  holder.blurLayout);
    }

    public void setLayout(String url, BlurryLayout blurLayout) {
        final Bitmap[] bm = new Bitmap[1];
        Glide.with(context)
                .asBitmap()
                .load(url)
                .thumbnail( 0.1f )
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                        blurLayout.setBitmapBlur(resource);
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

        @BindView(R.id.blurLayout)
        BlurryLayout blurLayout;




        public WallpapersHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}