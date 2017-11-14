package com.example.asus.jingdong.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.HomePageBean;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.utils.Urls;
import com.example.asus.jingdong.view.adapter.ViewPagerOneAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述
 */
public class ViewPagerTwo extends AppCompatActivity implements NetDataCallBack<HomePageBean> {
    @BindView(R.id.recyclerview_one)
    RecyclerView recyclerviewOne;
    private List<HomePageBean.DataBean> mlist = new ArrayList<>();
    private ViewPagerOneAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_two);
        ButterKnife.bind(this);
        initview();
        loadData();
    }

    private void loadData() {
        OkHttpUrl okHttpUrl = new OkHttpUrl();
        okHttpUrl.getdata(Urls.GOODS_VIEWPAGER,this,HomePageBean.class);
    }

    private void initview() {
        recyclerviewOne.setLayoutManager(new GridLayoutManager(this,5));
        adapter = new ViewPagerOneAdapter(this,mlist);
        recyclerviewOne.setAdapter(adapter);
    }

    @Override
    public void success(HomePageBean homePageBean) {
        mlist.addAll(homePageBean.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void faild(int positon, String str) {

    }
}
