package com.example.asus.jingdong.view.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.GoodsDetailsBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述    详情页面的适配器
 */
public class CattegoryAdapter extends RecyclerView.Adapter<CattegoryAdapter.ViewHolder> {


    private Context context;
    List<GoodsDetailsBean.DataBean> mlist;

    public CattegoryAdapter(Context context, List<GoodsDetailsBean.DataBean> mlist) {
        this.mlist = mlist;
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.LayoutManager layoutManager = ((RecyclerView) parent).getLayoutManager();
        View view = null;
        if (layoutManager instanceof GridLayoutManager) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cattegory_item01, parent, false);

        } else if (layoutManager instanceof LinearLayoutManager) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cattegory_item02, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 按价格排序
     */
    public void sortByPrice(final boolean isOrder) {

        Collections.sort(mlist, new Comparator<GoodsDetailsBean.DataBean>() {
            @Override
            public int compare(GoodsDetailsBean.DataBean o1, GoodsDetailsBean.DataBean o2) {

                if (isOrder) {
                    return o1.getPrice() - (o2.getPrice());
                } else {
                    return o2.getPrice() - (o1.getPrice());
                }
            }
        });

        notifyDataSetChanged();
    }

    /**
     * 按销量排序
     */
    public void sortBySales(final boolean isOrder) {

        Collections.sort(mlist, new Comparator<GoodsDetailsBean.DataBean>() {
            @Override
            public int compare(GoodsDetailsBean.DataBean o1, GoodsDetailsBean.DataBean o2) {
                if (isOrder) {
                    return o1.getSalenum() - (o2.getSalenum());
                } else {
                    return o2.getSalenum() - (o1.getSalenum());
                }
            }


        });
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GoodsDetailsBean.DataBean bean = mlist.get(position);
        holder.itemName.setText(bean.getTitle().substring(0, 20)+"...");
        holder.itemNameLei.setText(bean.getSubhead().substring(0, 18)+"...");
        holder.itemNamePrice.setText("￥" + bean.getPrice());
        holder.itemNamePrices.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//数字划线效果
        holder.itemNamePrices.setText("￥" + bean.getBargainPrice());
        holder.itemNamePingjia.setText("销量" + bean.getSalenum());

        String images = bean.getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.image);

        //设置tag值
        holder.itemView.setTag(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItemClick.ItemClick(v, (Integer) v.getTag());
            }
        });
    }

    public interface SetItemClick {
        void ItemClick(View view, int position);
    }

    SetItemClick setItemClick;

    public void setSetItemClick(SetItemClick setItemClick) {
        this.setItemClick = setItemClick;
    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.item_name_prices)
        TextView itemNamePrices;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}