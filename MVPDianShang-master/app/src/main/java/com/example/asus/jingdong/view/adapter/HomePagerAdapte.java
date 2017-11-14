package com.example.asus.jingdong.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.HomePagerData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述    首页竖向的RecyclerView的适配器
 */
public class HomePagerAdapte extends RecyclerView.Adapter<HomePagerAdapte.HolderView>{
    Context context;
    List<HomePagerData.TuijianBean.ListBean> list;
    public HomePagerAdapte(Context context, List<HomePagerData.TuijianBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_fragment_item, parent, false);
        HolderView viewHolder = new HolderView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        HomePagerData.TuijianBean.ListBean bean = list.get(position);
        holder.itemName.setText(bean.getTitle().substring(0, 20)+"...");
        holder.itemNameLei.setText(bean.getSubhead().substring(0, 18)+"...");
        holder.itemNamePrice.setText("￥"+bean.getPrice());
        holder.itemNamePingjia.setText(bean.getCreatetime());
        String images = bean.getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.image);

        //设置tag值
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_name_lei)
        TextView itemNameLei;
        @BindView(R.id.item_name_price)
        TextView itemNamePrice;
        @BindView(R.id.item_name_pingjia)
        TextView itemNamePingjia;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
