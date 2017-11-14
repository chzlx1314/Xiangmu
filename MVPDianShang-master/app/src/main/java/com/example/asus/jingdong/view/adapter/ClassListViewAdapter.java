package com.example.asus.jingdong.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.GoodsClassBean;

import java.util.List;
/**
 * 类描述  左侧主分类列表 的listview 适配器
 */

public class ClassListViewAdapter extends BaseAdapter {

    private List<GoodsClassBean.DataBean> goodsList;
    private Context context;

    private int selectId = 0;

    public ClassListViewAdapter(List<GoodsClassBean.DataBean> goodsList, Context context) {
        this.goodsList = goodsList;
        this.context = context;
    }

    //    改变条目背景的方法
    public void setSelected(int pos) {
        this.selectId = pos;

        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_class, null);

            holder.tv_name = (TextView) convertView.findViewById(R.id.class_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(goodsList.get(position).getName());

        /**
         * 如果点击的是此条目 则改变背景
         */
        if (position == selectId) {
            holder.tv_name.setTextColor(Color.parseColor("#FFE13F5A"));
            convertView.setBackgroundColor(Color.parseColor("#F0F2F5"));
        } else {
            holder.tv_name.setTextColor(Color.parseColor("#000000"));
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            //ContextCompat.getColor(context,R.color.colorPrimaryDark);

        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_name;
    }
}
