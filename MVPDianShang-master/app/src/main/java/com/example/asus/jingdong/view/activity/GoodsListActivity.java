package com.example.asus.jingdong.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.GoodsDetailsBean;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.utils.Urls;
import com.example.asus.jingdong.view.adapter.CattegoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述   详情页（第一页）
 */
public class GoodsListActivity extends AppCompatActivity implements View.OnClickListener, NetDataCallBack<GoodsDetailsBean> {


    @BindView(R.id.cattegory_image)
    RadioButton cattegoryImage;
    @BindView(R.id.but01)
    RadioButton price_rank_btn;
    @BindView(R.id.rank_box1)
    CheckBox price_box;
    @BindView(R.id.but02)
    RadioButton sales_rank_btn;
    @BindView(R.id.rank_box2)
    CheckBox sales_box;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rec)
    RecyclerView recyclerview;
    @BindView(R.id.go_to_top_img)
    ImageView goToTop;
    @BindView(R.id.qiehuan)
    RadioButton qiehuan;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<GoodsDetailsBean.DataBean> mlist = new ArrayList<>();
    private CattegoryAdapter adapter;
    private EditText topBar;
    private String pscid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        pscid = intent.getStringExtra("pscid");
        initview();
        LoadData();
    }

    private void LoadData() {
        OkHttpUrl okHttpUrl = new OkHttpUrl();
        okHttpUrl.getdata(Urls.GOODS_LIST+pscid, this, GoodsDetailsBean.class);
    }

    private void initview() {
        /***
         * 点击事件
         */
        cattegoryImage.setOnClickListener(this);
        qiehuan.setOnClickListener(this);
        goToTop.setOnClickListener(this);
        sales_rank_btn.setOnClickListener(this);
        price_rank_btn.setOnClickListener(this);

        //布局格式
        layoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerview.setLayoutManager(layoutManager);
        adapter = new CattegoryAdapter(this, mlist);
        recyclerview.setAdapter(adapter);

        topBar = (EditText) findViewById(R.id.goods_list_top_bar);

        topBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsListActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });

        /*
        **
         * radioButton 切换字体颜色
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.but01:
                        setRadioButtonStyle();
                        price_rank_btn.setTextColor(Color.parseColor("#e3125f"));

                        price_box.setBackground(getResources().getDrawable(R.drawable.select_rank));
                        break;
                    case R.id.but02:
                        setRadioButtonStyle();
                        sales_rank_btn.setTextColor(Color.parseColor("#e3125f"));
                        sales_box.setBackground(getResources().getDrawable(R.drawable.select_rank));
                        break;
                }
            }
        });

        /**
         * 设置滑动 到一定程度 显示回到顶部的图标
         */
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取第一个可见位置
                    int firstVisibleItemPosition = linearManager.findFirstVisibleItemPosition();
                    //当滑动到第三个以上时显示置顶图标
                    if (firstVisibleItemPosition >3) {
                        goToTop.setVisibility(View.VISIBLE);
                    } else {
                        goToTop.setVisibility(View.GONE);
                    }
                }
            }
        });
        adapter.setSetItemClick(new CattegoryAdapter.SetItemClick() {
            @Override
            public void ItemClick(View view, int position) {
                int pid = mlist.get(position).getPid();
                String pid_String = Integer.toString(pid);
                Intent intent = new Intent(GoodsListActivity.this,GoodsDetailActivity.class);
                intent.putExtra("pid",pid_String);
                startActivity(intent);
            }
        });

    }

    private void setRadioButtonStyle() {
        price_rank_btn.setTextColor(Color.parseColor("#2a262a"));
        sales_rank_btn.setTextColor(Color.parseColor("#2a262a"));
        price_box.setBackground(getResources().getDrawable(R.drawable.b4i));
        sales_box.setBackground(getResources().getDrawable(R.drawable.b4i));
        price_box.setChecked(true);
        sales_box.setChecked(true);
    }

    /***
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cattegory_image:
                //关闭当前页面
                finish();
                break;
            case R.id.go_to_top_img:
                //返回顶部
                recyclerview.scrollToPosition(0);
                break;
            case R.id.but02:
                /**
                 * 点击radioButton 实现为右侧 checkBox设置背景选择器
                 * 并实现 checkBox 切换
                 */
                if (sales_box.isChecked()) {
                    sales_box.setChecked(false);
                    adapter.sortBySales(false);
                } else {
                    sales_box.setChecked(true);
                    adapter.sortBySales(true);
                }
                break;
            case R.id.but01:
                /**
                 * 点击radioButton 实现为右侧 checkBox设置背景选择器
                 * 并实现 checkBox 切换
                 */
                if (price_box.isChecked()) {

                    price_box.setChecked(false);

                    adapter.sortByPrice(false);
                } else {
                    price_box.setChecked(true);
                    adapter.sortByPrice(true);
                }

                break;
            case R.id.qiehuan:
                //切换布局
                RecyclerView.LayoutManager layoutManager2 = recyclerview.getLayoutManager();
                if (layoutManager2 == null) {
                    return;
                }
                if (layoutManager2 instanceof GridLayoutManager) {
                    recyclerview.setLayoutManager(layoutManager);
                    recyclerview.setAdapter(adapter);
                } else if (layoutManager2 instanceof LinearLayoutManager) {
                    recyclerview.setLayoutManager(gridLayoutManager);
                    recyclerview.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
                break;

        }
    }

    /****
     * 请求数据成功
     * @param goodsDetailsBean
     */
    @Override
    public void success(GoodsDetailsBean goodsDetailsBean) {
        mlist.addAll(goodsDetailsBean.getData());
        adapter.notifyDataSetChanged();

    }

    /**
     * 请求数据失败
     * @param positon
     * @param str
     */
    @Override
    public void faild(int positon, String str) {

    }

}
