package com.wallpaper71.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wallpaper71.R;
import com.wallpaper71.config.BaseUrl;
import com.wallpaper71.model.ColorListData;
import com.wallpaper71.view.ListByCollection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wallpaper71.config.BaseUrl.colorImageDir;

public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.WallpapersHolder> {

    List<ColorListData> groupListData;
    Context context;

    public ColorListAdapter(List<ColorListData> groupListData, Context context) {
        this.groupListData = groupListData;
        this.context = context;
    }

    @Override
    public ColorListAdapter.WallpapersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.color_item,parent,false);
        ColorListAdapter.WallpapersHolder mh = new ColorListAdapter.WallpapersHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ColorListAdapter.WallpapersHolder holder, int position) {
        holder.title.setText(groupListData.get(position).getTitle());
        Glide.with(context).load(BaseUrl.baseUrl+colorImageDir+groupListData.get(position).getPhoto()).into(holder.collectionImg);

        Log.d("bUrl",BaseUrl.baseUrl+colorImageDir+groupListData.get(position).getPhoto());
        holder.color_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ListByCollection.class);
                intent.putExtra("name",groupListData.get(position).getTitle());
                intent.putExtra("id",groupListData.get(position).getId()+"");
                intent.putExtra("data_form_link",groupListData.get(position).getId()+"");
                intent.putExtra("data_from","color");
                intent.putExtra("action_type","Color-View");
                intent.putExtra("from","Home");

                context.startActivity(intent);
            }
        });
       /* switch (position){
            case 0:
                Glide.with(context).load("https://i.pinimg.com/474x/e4/ff/26/e4ff26049504adfbbb7b57af2277e5f8.jpg").into(holder.collectionImg);
                break;
             case 1:
                Glide.with(context).load("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMSEhUTExMVFRUXGBcXFxcXGBcaGBUXFxcXFxcVGB0aHSggGholHRcaITEhJSkrLi4uFx8zODMtNygtLisBCgoKBQUFDgUFDisZExkrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrK//AABEIALcBEwMBIgACEQEDEQH/xAAZAAADAQEBAAAAAAAAAAAAAAAAAQIDBAf/xAA6EAACAQEDCgQFAwQBBQAAAAAAAQIRUpGhAxITITFRYXHR8ARBgbEUU2KS4TKiwSJCcuLxBSNDgtL/xAAUAQEAAAAAAAAAAAAAAAAAAAAA/8QAFBEBAAAAAAAAAAAAAAAAAAAAAP/aAAwDAQACEQMRAD8A9UAYUABoKAkADUeQUHQBJAh0HHvqAkhgMBxM4dfc2gtaMlx1bfcCmMWdqBIBteYUH64irxQAxk5y3q9AsorUb0BpuBmenjajegeXhajegLBMzfiI2oiWXgv7kBtG8nxWz0ZmvFQ34PoGV8TFrU/Lc+gGzZTMPi473cx/FR4/a9eAGrQ09Xdxi/FR3v7X0CPio739sugGl/e0SW/VtMl4iO2rr/jLoGnj5uW2y+gGjhr89/n3uBQe997Npk/ER+r7ZDXi1XZL7WBqovjiTmtd99on4tWZ3MT8YrGUu/IGlXv9gMV4xr+zKfb+RARmLc72NQi9a2PXtZcQYE6JbvcHko7vc0HFAYZPJxaq40fNsvQxsoqP8v3KAz0MbKDQxso0ACHkY2ViGghZRo2FQIWRitkUNRW5YlDAjNW5FLJRsq4dRpASoR3K4FFblcNUGgJyqotVN+tFxgtey5Cyi1OvetGlAJcOVB5qCV4wJUR5oIbAM0mMXvKFFJatW3+dYBQLw8/4HTvkBNHvY2uIx0Amms5P+oOcVFxb/VGv+LnFSryTZ2KPf8GXio1TW/Ve6APzpXErR91Hm176Dzd3bATyev8AISjw9wyadfO90x5FtAZPJiHKPP0zQA49PEeniUn3QtAZrLrtrqVDKruhQAJZRb9/uNzXdB3AvQCVlF211G8ot/sN8kPvYAnlF5Uw6gprhehv0uGu9QEPKrug1lo90NJLV5bf4ZKe6n8AQ8sh6eJoL19gIXiYi+IRq5a9vEbfHYBjLLxcX35lR8Wtf56GuveDrvAy+KXdeg/iVu9+ho672Jt7wM4+JSpq2c+g3l1xx6F66bWx3gQ8vw9+gR8T9Lx6Fsmb/qS5/wAALS/S8egnlfplczVUHmAYPLOv6XT1v2D07sO42jEMwDB5d/LkKeVk1+iS9NutG7jyE46nqrgwMXl5fLkS/ES+VK86Fk+9fUdFuw2Ac/xc/kyvJfiMp8t3nRNcB0XdAOf4qfyn934EdKhHcIDnQ6mOn4FLLrcwNQMdKtzHpluYGwGOn+mVw9PwlcBqyjnll/plcC8T9ErgN2NHO/EuxMS8S/lzuA6nsXP+GCMsnldVHF7dzHHKV8pXAbDiYxyjsyHHKuywNZoTSIeUdl49BLKOwwNaeQSXlr74meldhlaR2WBZG3ULOdl3izpWcQNeVet5MupCnJ7YYoM6Vl4Aax9DOe2Pr7Cz5WHeglJ6movVXd5gaQT2MtSMFlZWHyDSSsMDd6wp313mOmnYeHUemlYeHUDaIPkjBZaVh4dQeWl5QeHUDfNW3EM3hy17TmeXl8v26hLLSsa+aA3lTcu/Qpyfa2HM8vOw711F8ROw8OoHRnPuoHJpZ2Xh1AArxDOe8QwHV7x5z5iGkA3LW+Y6slP3fuUAk+LGq7wbAB1ComxgNVAmLfp/JSQANAgTAKBeFO9wRAbep8n7CgtS9Bzepvg/YI6kgHmizfMsloBONVqChdRMCWiaF3kraAJA4FVCgE5gZiKiNIDNQ5lOJTCm/gBnk6UfN+7LUUicls9X7s1AjUJ98OY6d6l7iTo9er2Ay1cLwLzeXqAHJnysO8efOxiuhoAEZ8/l4ocZysYoq8aAnPlYd6DSSsP9vUtIEwJ0krEr0GllYeBpQYGeklYeAaV2JGhSQGKyjsSvHpXYlgVL9XosajQELKS84t+gRykrErvyaNcQAlZR2ZXCz/pldrNKjTAzz2001LY9dNuAlN8ftZs2K8DJzf1faytI35Nf+r6l04sSAir43PoDlLc7maOIknvAhZWVl3MWklulcarmyVWrwAnSSsyu/ILKysS50XU0zRNeVQMlOdmVw9LOw7l1LW3aPN4gZLKzX/jlgCy0vlS9KdS5PiCXEDNZWa2ZOXnu6lrxEvlyvXUGuIeoD08vlywMZOVf0z4rVsNXzGgJWXlYlgA3zADMDLOlYd66jz52HeuoGqGYZ2Usd3jzspZw/wBgNx1OfOyllXf7DzspZV3+wG8QoYqWUs+3UM+dj26gboaMc+djFDWUnYxQGj2vlH+SkZSlKtVDyXmlsFnTsfuQGy9QZlnTsYjzpWMUBpQDNznYxRWfKw8OoFDr/wAkZzsP9vUM52JYdQLQLaRnysSw6izpWXh1A0pxD1M8+Vh4dQ0krEsOoGpnHa/QWfKxLDqLOlVtZOWvl1A1FtXH0I0kvlywFpJV/RL9vUDSQzByl8uWHr5jWVl8ueHUDV+eAEaR/LngS5ysSw6gak+mwjSSsSE8pKxK5dQNmgb4Ix08vlzw6kvLS1f9uXHUuoGkp6/0t8VTqBnp5fLncuoAKoxUGAwSExgU9twJEra/T2KAdAQDQBQAAAAAAdQENMAoA6AAJC83zGKmt7doDQ2TXug6gNBTmJ8kO4AvCnMHHuoUASXdRh35gBMeY6CiNsAzQzRZwKvaAlPW9e78lat5K2vkv5NM4CWu2CGwquOPQAcQJzV2pdBgYUe7u8evc8OoJAAa9zw6j17nh1EgQDlWtVHVzQqys4ooTAdZWcUCcrOKGn5d96gAdZWcUFXZxXUBS2PkA9e54dQ17nh1HUaAl1svDqFZWXeuoxpd6wJzpWXh1FWVl/t/+jTXvADNSlYf7epWc7Mv29Sm+9YqAJylZeHUHN2JYdR04gkAZ7sSw6hnOxLAKcQQCznYdw9Jq/RLAcnqYobFyXsAs92JXfkHP6JXFoE+IGcZtf2SuYaR2JXM1FXugGWldmX2voJ5X6J3M29RAYLK0b/pm00ti57x/Ea/0Tp/j+TWvEdeIGPxCsT+1g8urOU+1mqlxFV7wM/iVuyn2sDRS4sAMkBKzt3t1BKW7FAUhk0luV46S3K8BjJzZDSlwApATSW5BSXACwn+l8iKT3If9dNivAsEiFn7o/cPOnZj934AugZpGdOyvu/Am52V934A1dRE1lZV/wCA/qs4gUCIblZxHnSs4/gChojOdh3hnSssCgJznZYKTssCnsfIMlsXJexOc6NZsrvyTHKOi/ons3R6gb04AY6Z2JXLqNZZ2Z3IDRg2Q8t9MvVfgWmW53PoBecJv/glZaPG5g8vHjd+AG+9gMWniZy8RHyA0iD598dRGnjvJl4iG/EDXO54Ac78THesAAtBUH3qBAOpOUhqcq69nIY2Asm3RckUqiSGAVKqSMB14iQIaYDqCbGiQHncRqTEAA5MFxBDAWU2qnn0wDb54intj6+xQExhTXrvY2ht91AAdQYMQBrBNgkADcnQTnyDvzEwHnMM9hQlgPOfbHnPf7k0D8gaZ3Ehye9kpg1UB6R9spZTmZqoAa171AZABnnPdiGc9wAA857sQc+DwAADSOy8B6ThfQAANJweHUNLweHUYAJ5VbngL4hbmAAP4leaeHUH4hbnh1GAB8Stzw6krxUdzw6gAD+Lj9Vy6gvFx3O5dQABT8VHVtu2B8bHjUAAfxsOI/i4cbgAB/Ex3+4aZeVQAAWXiU8pHiIACWUitvdwZ6/gAArOVNpMqNU73iAAzlsqVBp7AABODWrcPNEADzSagABo69sAAD//2Q==").into(holder.collectionImg);
                break;
            case 2:
                Glide.with(context).load("data:image/jpeg;base64,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").into(holder.collectionImg);
                break;
            case 3:
                Glide.with(context).load("https://img.freepik.com/free-vector/green-abstract-geometric-background_23-2148366726.jpg?size=626&ext=jpg").into(holder.collectionImg);
                break;
            case 4:
                Glide.with(context).load("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMSEhUSEhIVFRUVFxcVFxcXFxUVFRUXFxUXFxUVFxcYHSggHRolHRUVITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OGhAQGi0lHSUtLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAL0BCwMBIgACEQEDEQH/xAAZAAADAQEBAAAAAAAAAAAAAAABAgMABAf/xAAlEAEBAQEAAQMEAgMBAAAAAAAAAQIREgMhURMxQWGR8HGB0fH/xAAZAQEBAQEBAQAAAAAAAAAAAAACAQMABAX/xAAdEQEBAQEBAAMBAQAAAAAAAAAAARECEiExUUFx/9oADAMBAAIRAxEAPwDx/JgkGvM+3JkYZGFDkaQ3AFDgyGhIeJWnI8BmkQhOHBkRpI0MHBiHI0gcM1iLhbkc5HI2O105/rcJxSBI7Vs0ky1bpeKFoAfhbFCwJWsYbHJnwTQcHQkzz5JYWqUsiyheSAawFZ2FsYYKjgRm40jnYMh4GYaZGteYwyNIfMG1rzzoeIjGsRpgcNI0HKFIMgHGZTWnkMweGkEdaTklg8Hh7Has5S8WyeweO13lPrbPcE45LLhQPwLgtZ3mp2BVfALHaN4qdZSwvF1LynY1yewIuheU+B4q8LV0LyThD6JYUZdl4YAulZ/EPweHkajrbwWDBGRCnIjB43EayDI3DZNwdaznS5g+IyGiac5LMmmT5h8wbWs4JMtMq+Js5H0c4T+m0yrKfKac5c/G4v8ATLY7U8pE1P0vM/JdLqXlDjL8C4/S+h8IWN4K84Xi6PhLUL4rbylfYpWXXOF8QuTRqrPInwvFNEsKVn1CWB4noWrrK8xK5C1StwtZXn8OPiaQ3GevXOCeLcVkLrKaV4CGmWkNM11p88saZ+TQ3iFrWcl8TzITKuINrXnkvBh2mR1rgzB7kcrTlG1ZE5htYX9LA+rPx/6Oq57PYlnx93Rwc+n8rq2OW4JfTde4lIso1zWD1fWUeFo4TRbFdYCrKljn0W5WuSX3OVjeScJqHsGxWdmohIalpsaXZYa0OKxv2WhwwcILNdEjcGMye3IMatlq4v4OYrCSGmhrTn4Nw3CyKTI1pAypA8fkZka0kN9xmeVT08wc57Q08bF/H96p9NvUz/z/AIb0fgbXVsywbn59lJAxjt/v8JrtLm8/H8tNT+VLhP1PT9kRP18/ZLw+yl7f9B2nFL4oXMW3aST5KI1ieofWiWVYlTqelvElhSs7Eukqlhbk2NidLYfYcKMbEuF4YSY5qdy0hq3sWh5W4aM3WT2ZIPGrQbUMFMFhsJS5UlUTyboVtD5z08pMU+INaQ+LL7f2rZiFytn7DVW19qjPufG4f08e/B+nRWepL+Cy2e5apRT6D0s/n5Hc9r/gefF+xN6cn3UsT3beIM1xP3pElu/BLn8un1JIjdUpXJ9g6hdz8/LdJCaiO9K70hTjLoKW02y6OMaSlowuqUY2lodbQGwtDRej0ZVZ/bpkMXgysnvGNxh6ij0cBFIlOD2nkLKaUK0hpDzSfkfA1pD5Vv2Jk4kGc9V8vf2/Sfjz8jiSJXOq8vtS3BZuGmviihKEnT2o6v8ApyqTH4GI5vKe32XHF9WITfupv1ULf0Ujg1preGkhdUhqVnyTZ6ndHGXRLpPWj2gcYdfJU7VNF4UZdJ6oDWNjS2B4mGO0fK/DRozJ7WN0tbrllw1NNENhDh4a0so0Th5RlT6bNTDldErXaN0fA4Wn6fE6l33WxUpabwbtgX1DZ1BdrX1i97E9wfS0uOPP2X1NextVPUdHaGfkvl0s1wIWJpbeFNfehqkFLupSm2n05GPV+W1Q6GrCXRSMuusHRKwapRjaVh6FpMgDrWlULXZNDan0ZWePZ6PKPSeRamL6xS0ZSTRo7FlPKfO0TQbGk6V6KXRzUw52r5mz6icHiYcq0vD59RzSj5Dhel+Nm+6XmbOnYWq9T8uX/AXf7DVTE1bzL5dqONBnS4mq6pNbLrXSrImjbS3RbsmoUjLrv8N5F0BbeljO9BonkOy048/V+Wui602qSUpGPXRpQoB1QvTaDoN5Ez10yjCN1nj1+lGCUeoet0cUlNmudL8nlbyJNDdJh+j9GVOaHOkwp1FemzpPppRxpKpI12nNj5Jhevw/kMS8h1p2L7UodS8j427HTvRt/IRtUt27HWw1pLsvQsKRlejbpbQnsXWlkDrr9a6L5NdFPGN6C0bSaDpYx9NpuD0tqhc+xJa10XqyM+uhgNKFUNdPQjWhdA9VputKS6bydjvanWlL0PJMX0p0sodDrsW9KwYlKPkmFO4uET+obO0xr7lo2qZqWq00mLO8q5STbfUTC9ytqnwTza7+FxJ1J8m3a3anaPk7E9TWtC7a7L5LgXr8rXTB0vSxnem1QG1O1Yztw1LpvILVxneozUvkF0WM/UG0G6HkoWs3W6PHJ/irNGoPSAMygPWCN1y6brRhQhtbpQjsX0aDNA0RYp1qUExp6N5NqhmBXJtwejNENFSWmjUsaIWhW61L+VZ24NrBwPJR39Eol0sGt0KFMrPdJawUCZ2iDRrHJaMC00hbHOsuP//Z").into(holder.collectionImg);
                break;
            default:
                Glide.with(context).load("data:image/jpeg;base64,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").into(holder.collectionImg);

        }*/

    }

    @Override
    public int getItemCount() {
        return groupListData.size();
    }

    public class WallpapersHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.collectionImg)
        ImageView collectionImg;

        @BindView(R.id.color_lay)
        LinearLayout color_lay;

        @BindView(R.id.title)
        TextView title;

        public WallpapersHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}