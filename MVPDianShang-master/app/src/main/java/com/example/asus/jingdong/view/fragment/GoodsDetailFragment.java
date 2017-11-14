package com.example.asus.jingdong.view.fragment;


import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.GoodsDetailsData;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.utils.Urls;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类描述    详情页面的第一个子页面
 */
public class GoodsDetailFragment extends Fragment implements NetDataCallBack<GoodsDetailsData> {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_goods_name)
    TextView goods_name;
    @BindView(R.id.tv_new_price)
    TextView new_price;
    Unbinder unbinder;
    @BindView(R.id.tv_old_price)
    TextView tvOldPrice;
    private View view;
    private List<String> mlist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview();
        LoadData();
        return view;
    }

    private void LoadData() {
        OkHttpUrl okHttpUrl = new OkHttpUrl();
        String pid = getArguments().getString("goods_id");
        okHttpUrl.getdata(Urls.GOODS_DETALIS + pid, this, GoodsDetailsData.class);
    }

    private void initview() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void success(final GoodsDetailsData goodsDetailsData) {

        String images = goodsDetailsData.getData().getImages();
        String[] split = images.split("\\|");
        for (String bean : split) {
            mlist.add(bean);

        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 设置轮播图
                 */
                banner.setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load(path).into(imageView);

                    }
                });
                banner.setImages(mlist);
                banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
                banner.setDelayTime(2000);
                banner.start();

                goods_name.setText(goodsDetailsData.getData().getTitle());
                new_price.setText("" + goodsDetailsData.getData().getBargainPrice());
                tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//数字划线效果
                tvOldPrice.setText("" + goodsDetailsData.getData().getPrice());



            }
        });

    }

    @Override
    public void faild(int positon, String str) {

    }
}