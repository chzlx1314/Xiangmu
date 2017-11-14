package com.example.asus.jingdong.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.AddCatBean;
import com.example.asus.jingdong.model.bean.GoodsDetailsData;
import com.example.asus.jingdong.model.net.MyApp;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.presenter.AddCartPresenter;
import com.example.asus.jingdong.utils.Urls;
import com.example.asus.jingdong.utils.UserInfo;
import com.example.asus.jingdong.view.IViews.AmountView;
import com.example.asus.jingdong.view.IViews.IGoodsAddCartView;
import com.example.asus.jingdong.view.fragment.GoodsDetailFragment;
import com.example.asus.jingdong.view.fragment.GoodsIntroduceFragment;
import com.example.asus.jingdong.view.fragment.GoodsRecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述   商品详情界面 实现加入购物车功能
 */
public class GoodsDetailActivity extends AppCompatActivity implements NetDataCallBack<GoodsDetailsData>,IGoodsAddCartView {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager pager;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    private View popView;
    private String[] titles = {"商品", "详情", "评价"};
    private ImageView popBack; //popupwindow 退出按钮
    private Button ok_btn;      //确认加入购物车按钮按钮
    private AmountView amountView; //数量加减器
    private String pid;   //此商品的id
    private ImageView popImg;
    private TextView popName, popPrice;
    private PopupWindow mPopupWindow;
    private List<Fragment> fragments = new ArrayList<>();
    private SharedPreferences sp;
    private int quantity = 1; //加入购物车商品的数量 默认为1
    private AddCartPresenter addCartPresenter;
    private int sellerid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        //得到传过来的商品id
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        sp = MyApp.getUserInfoSp();
        addCartPresenter = new AddCartPresenter(this);
        //初始化 popupwindow
        initPop();
        LoadData();
    }


    private void LoadData() {
        OkHttpUrl okHttpUrl = new OkHttpUrl();
        okHttpUrl.getdata(Urls.GOODS_DETALIS+pid,this, GoodsDetailsData.class);
    }

    /**
     * 弹出popupWindow 可以点击加入购物车
     */

    public void showPopupShopping(View view) {

        setBackgroundAlpha(0.5f);  // 弹出时设置半透明

        //底部弹出
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

    }

    //    跳转到购物车界面
    public void toShoppingCart(View view) {
        Toast.makeText(this, "跳到购物车", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TheHomePageActivity.class);
        intent.putExtra("counts",2);
        startActivity(intent);


    }
    /**
     * 初始化 点击购物车 弹出的 popupWindow
     */
    private void initPop() {

        popView = View.inflate(this, R.layout.pop_add_to_shopping_cart, null);

        popBack = (ImageView) popView.findViewById(R.id.back);

        ok_btn = (Button) popView.findViewById(R.id.button2);

        amountView = (AmountView) popView.findViewById(R.id.amountView);

        popImg = (ImageView) popView.findViewById(R.id.imageView);
        popName = (TextView) popView.findViewById(R.id.tv_goods_name);
        popPrice = (TextView) popView.findViewById(R.id.tv_goods_price);


        mPopupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mPopupWindow.setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#ffffff")));


        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);

        //监听弹出框 消失
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f); //为界面 设置正常透明度
            }
        });

        popBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        /**
         * 确认加入购物车操作
         */
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 如果不是登录状态 跳转到登录界面
                 */

                if (sp.getBoolean(UserInfo.IS_LOGGING, true)) {
                    /**
                     * 如果计数器框中为空 提示用户
                     *
                     * 不为空 则 加入购物车
                     */
                    if (quantity != 0) {
                        addCartPresenter.AddGoodsToCart(sp.getString(UserInfo.USER_ID, ""), String.valueOf(sellerid),pid);
                        mPopupWindow.dismiss();


                    } else {
                        Toast.makeText(GoodsDetailActivity.this, "请选择正确 的商品数量", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoagActivity.class);
                    startActivity(intent);
                }

            }
        });


    }

    /**
     * 关闭当前页面
     * @param view
     */
    public void goBack(View view){
        finish();
    }

    /**
     * 设置屏幕的透明度的方法
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void success(final GoodsDetailsData goodsDetailsData) {
        GoodsDetailsData.DataBean data = goodsDetailsData.getData();
        String images = data.getImages();
        final String[] split = images.split("\\|");
        sellerid = goodsDetailsData.getSeller().getSellerid();
        popName.setText(data.getTitle());
        popPrice.setText(data.getPrice()+"");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                fragments.add(new GoodsDetailFragment());
                fragments.add(new GoodsIntroduceFragment());
                fragments.add(new GoodsRecommendFragment());

                Glide.with(GoodsDetailActivity.this)
                        .load(split[0])
                        .into(popImg);

                pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        Fragment fr = fragments.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString("goods_id", GoodsDetailActivity.this.pid);
                        fr.setArguments(bundle);
                        return fr;
                    }


                    @Override
                    public int getCount() {
                        return fragments.size();
                    }



                    @Override
                    public CharSequence getPageTitle(int position) {
                        return titles[position];
                    }
                });

                tabLayout.setupWithViewPager(pager);

            }
        });

    }

    @Override
    public void faild(int positon, String str) {

    }


    @Override
    public Context context() {
        return this;
    }
    @Override
    public void onCartAddSucceed(AddCatBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GoodsDetailActivity.this, "成功加入购物车", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCartAddFail(String e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GoodsDetailActivity.this, "发生错误", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
