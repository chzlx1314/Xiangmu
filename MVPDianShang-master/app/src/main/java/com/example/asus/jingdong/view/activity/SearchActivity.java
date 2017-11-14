package com.example.asus.jingdong.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.SouSuoBean;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.utils.Urls;
import com.example.asus.jingdong.view.adapter.HomePagerAdaptes;
import com.example.asus.jingdong.view.adapter.SuoSouAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;


/**
 * 搜索界面
 */
public class SearchActivity extends AppCompatActivity implements NetDataCallBack<SouSuoBean>, XRecyclerView.LoadingListener, View.OnClickListener {

    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerview;
    @BindView(R.id.cattegory_image)
    RadioButton cattegoryImage;
    @BindView(R.id.goods_list_top_bar)
    EditText goodsListTopBar;
    @BindView(R.id.qiehuan)
    RadioButton qiehuan;
    LinearLayoutManager layoutManager;
    @BindView(R.id.suosou_title)
    TextView suosouTitle;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private String trim;
    private int page =1;
    private List<SouSuoBean.DataBean> mlist = new ArrayList<>();
    private HomePagerAdaptes adapte;
    private SuoSouAdapter adapters;
    public String [] texts={"雷神","老人机","正装鞋","热水壶",".地中海","护眼霜","沙发"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initview();
    }


    private void initview() {

        xrecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        //添加Android自带的分割线
        xrecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapte = new HomePagerAdaptes(this, mlist);
        xrecyclerview.setAdapter(adapte);
        xrecyclerview.setLoadingListener(this);

        qiehuan.setOnClickListener(this);
        cattegoryImage.setOnClickListener(this);


        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager);
        adapters = new SuoSouAdapter(this,texts);
        recyclerview.setAdapter(adapters);


    }

    private void loadData() {
        trim = goodsListTopBar.getText().toString().trim();
        OkHttpUrl okHttpUrl = new OkHttpUrl();
        FormBody body = new FormBody.Builder()
                .add("keywords", trim)
                .add("page", String.valueOf(page))
                .build();
        okHttpUrl.getLoadDataPost(Urls.SUOSOU, this, SouSuoBean.class, body);

    }

    @Override
    public void success(SouSuoBean souSuoBean) {
        if(souSuoBean.getData()!=null&&souSuoBean.getData().size()>0){
            mlist.addAll(souSuoBean.getData());
            adapte.notifyDataSetChanged();
            stoprecyclerview();
        }
        adapters.notifyDataSetChanged();
    }
    //停止方法
    private void stoprecyclerview() {
        xrecyclerview.loadMoreComplete();
        xrecyclerview.refreshComplete();
    }

    @Override
    public void faild(int positon, String str) {

    }

    @Override
    public void onRefresh() {
        mlist.clear();
        page=1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        page++;
        loadData();
    }

    /***
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qiehuan:
                //把之前显示的影藏
                xrecyclerview.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
                suosouTitle.setVisibility(View.GONE);
                if (trim != null && trim.length() != 0) {
                    loadData();
                }
                break;
            case R.id.cattegory_image:
                finish();
                break;


        }
    }
}
