package com.example.asus.jingdong.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.jingdong.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述    搜索页面的热搜适配器
 */
public class SuoSouAdapter extends RecyclerView.Adapter<SuoSouAdapter.ViewHolder> {
    //模拟数据
    public String [] texts;
    Context context;
    public SuoSouAdapter(Context context, String[] texts) {
        this.context = context;
        this.texts = texts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sousuo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleName.setText(texts[position]);
    }

    @Override
    public int getItemCount() {
        return texts.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_name)
        TextView titleName;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
