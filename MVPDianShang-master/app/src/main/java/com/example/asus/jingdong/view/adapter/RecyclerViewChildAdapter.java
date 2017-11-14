package com.example.asus.jingdong.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.GoodsClassData;

import java.util.List;

/**
 * 类描述    右侧分类列表 的zi级列表 适配器
 */

public class RecyclerViewChildAdapter extends RecyclerView.Adapter<RecyclerViewChildAdapter.MyViewHolder>{

    private List<GoodsClassData.DataBean.ListBean> goodsList;
    private Context context;
    private LayoutInflater inflater;

    public RecyclerViewChildAdapter(List<GoodsClassData.DataBean.ListBean> goodsList, Context context) {
        this.goodsList = goodsList;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_class_child,parent,false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(goodsList.get(position).getName());

        //设置tag值
        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pscids = goodsList.get(position).getPscid();
                String pscid = String.valueOf(pscids);
                onGoodsItemClickListener.onClick((Integer) v.getTag(),pscid);
            }
        });

    }

    /**
     * 商品条目点击 回调接口
     */
    public interface OnGoodsItemClickListener{
        void onClick(int pos,String psid);
    }
    private OnGoodsItemClickListener onGoodsItemClickListener;
    public void setOnGoodsItemClickListener(OnGoodsItemClickListener onGoodsItemClickListener){
        this.onGoodsItemClickListener = onGoodsItemClickListener;
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        View itemView;

        TextView name;



        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = (TextView) itemView.findViewById(R.id.textView2);

        }
    }

}
