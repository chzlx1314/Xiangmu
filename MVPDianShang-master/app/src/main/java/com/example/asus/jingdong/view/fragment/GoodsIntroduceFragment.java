package com.example.asus.jingdong.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.GoodsDetailsData;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.utils.Urls;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类描述    详情页面的第二个子页面（WebView）
 */
public class GoodsIntroduceFragment extends Fragment implements NetDataCallBack<GoodsDetailsData> {

    @BindView(R.id.webView)
    WebView webView;
    Unbinder unbinder;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_introduce, container, false);
        unbinder = ButterKnife.bind(this, view);
        LoadData();
        return view;
    }

    private void LoadData() {
        OkHttpUrl okHttpUrl = new OkHttpUrl();
        String pid = getArguments().getString("goods_id");
        okHttpUrl.getdata(Urls.GOODS_DETALIS+pid, this, GoodsDetailsData.class);
    }

    @Override
    public void success(final GoodsDetailsData goodsDetailsData) {
        String url = goodsDetailsData.getData().getDetailUrl();
        webView.loadUrl(url);

    }

    @Override
    public void faild(int positon, String str) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
