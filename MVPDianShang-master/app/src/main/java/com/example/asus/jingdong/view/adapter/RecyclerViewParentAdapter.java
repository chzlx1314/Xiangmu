package com.example.asus.jingdong.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.GoodsClassData;
import com.example.asus.jingdong.presenter.GetGoodsClassPresentes;
import com.example.asus.jingdong.view.IViews.IGetGoodsClassViews;
import com.example.asus.jingdong.view.activity.GoodsListActivity;

import java.util.ArrayList;
import java.util.List;
/**
 * 类描述    右侧分类列表 的父级列表 适配器
 */
public class RecyclerViewParentAdapter extends RecyclerView.Adapter<RecyclerViewParentAdapter.MyViewHolder> {

    private List<GoodsClassData.DataBean> goodsList = new ArrayList<>();
    private List<GoodsClassData.DataBean.ListBean> childList = new ArrayList<>();

    private Context context;
    private LayoutInflater inflater;

    public RecyclerViewParentAdapter(List<GoodsClassData.DataBean> goodsList, Context context) {
        this.goodsList = goodsList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_class_parent, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.name.setText(goodsList.get(position).getName());

        holder.presenter.getMoreData(goodsList.get(position).getCid());

    }

    @Override
    public int getItemCount() {
        return goodsList == null?0:goodsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements IGetGoodsClassViews {

        GetGoodsClassPresentes presenter;
        View itemView;

        TextView name;
        RecyclerView rec;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            presenter = new GetGoodsClassPresentes(this);
            name = (TextView) itemView.findViewById(R.id.textView);
            rec = (RecyclerView) itemView.findViewById(R.id.rec);
        }


        @Override
        public Context context() {
            return context;
        }

        @Override
        public void onGetDataSucceed(final GoodsClassData bean) {
            if (bean != null) {
//
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (GoodsClassData.DataBean bean1:goodsList){
                            childList.addAll(bean1.getList());
                        }
                       RecyclerViewChildAdapter adapter = new RecyclerViewChildAdapter(childList, context);
                        rec.setLayoutManager(new GridLayoutManager(context, 3));
                        rec.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.setOnGoodsItemClickListener(new RecyclerViewChildAdapter.OnGoodsItemClickListener() {
                            @Override
                            public void onClick(int pos,String pscid) {
                                Intent intent = new Intent(context, GoodsListActivity.class);
                                intent.putExtra("pscid",pscid);
                                context.startActivity(intent);
                                Toast.makeText(context(), "进入详情页面", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        }

        @Override
        public void onGetDataFail(String e) {

        }
    }

}