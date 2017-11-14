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
import com.example.asus.jingdong.model.bean.HomePageBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述    分类页面右边title的适配器
 */
public class ViewPagerOneAdapter extends RecyclerView.Adapter<ViewPagerOneAdapter.ViewHolder> {
    Context context;
    List<HomePageBean.DataBean> mlist;


    public ViewPagerOneAdapter(Context context, List<HomePageBean.DataBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_one_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomePageBean.DataBean bean = mlist.get(position);
        holder.viewpagerTitle.setText(bean.getName());
        Glide.with(context).load(bean.getIcon()).load(holder.viewpagerImage);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewpager_image)
        ImageView viewpagerImage;
        @BindView(R.id.viewpager_title)
        TextView viewpagerTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
