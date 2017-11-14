package com.example.asus.jingdong.view.adapter;

import android.content.Context;
import android.graphics.Paint;
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
 * 类描述    首页秒杀页面的适配器
 */
public class HomePagerMiaoAdapte extends RecyclerView.Adapter<HomePagerMiaoAdapte.HolderView> {
    Context context;
    List<HomePagerData.MiaoshaBean.ListBeanX> list;


    public HomePagerMiaoAdapte(Context context, List<HomePagerData.MiaoshaBean.ListBeanX> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_fragment_miao_item, parent, false);
        HolderView viewHolder = new HolderView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        HomePagerData.MiaoshaBean.ListBeanX bean = list.get(position);
        holder.miaoPrices.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//数字划线效果
        holder.miaoPrices.setText(bean.getBargainPrice()+"");
        holder.miaoPrice.setText(bean.getPrice()+"");
        String images = bean.getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.miaoImage);

        //设置tag值
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.miao_image)
        ImageView miaoImage;
        @BindView(R.id.miao_price)
        TextView miaoPrice;
        @BindView(R.id.miao_prices)
        TextView miaoPrices;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
