package com.example.asus.jingdong.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.HomePageBean;
import com.example.asus.jingdong.model.bean.HomePagerData;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.presenter.CheckPermissionUtils;
import com.example.asus.jingdong.utils.Urls;
import com.example.asus.jingdong.view.IViews.BannerImageLoaders;
import com.example.asus.jingdong.view.activity.SearchActivity;
import com.example.asus.jingdong.view.adapter.HomePagerAdapte;
import com.example.asus.jingdong.view.adapter.HomePagerMiaoAdapte;
import com.example.asus.jingdong.view.adapter.ViewPagerAdapter;
import com.example.asus.jingdong.view.adapter.ViewPagerOneAdapter;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yalantis.phoenix.PullToRefreshView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类描述    首页
 */
public class HomePageFragment extends Fragment implements NetDataCallBack<HomePagerData> {
    private static final int REQUEST_CODE = 111;
    private static final long REFRESH_DELAY = 23;
    @BindView(R.id.cattegory_image)
    RadioButton cattegoryImage;
    @BindView(R.id.qiehuan)
    RadioButton qiehuan;
    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.goods_list_top_bar)
    EditText goodsListTopBar;
    @BindView(R.id.recyclerview_one)
    RecyclerView recyclerviewOne;
    @BindView(R.id.pull_to_refresh)
    PullToRefreshView mPullToRefreshView;
    private ViewPagerAdapter madapter;
    private List<View> mlists = new ArrayList<>();

    private List<String> mlist = new ArrayList<>();
    private List<HomePagerData.DataBean> list_banner = new ArrayList<>();
    private List<HomePagerData.TuijianBean.ListBean> list_tuijian = new ArrayList<>();
    private List<HomePageBean.DataBean> list = new ArrayList<>();
    private List<HomePagerData.MiaoshaBean.ListBeanX> mlist_miao = new ArrayList<>();
    private HomePagerAdapte adapte;
    private ViewPagerOneAdapter viewPagerOneAdapter;
    LinearLayoutManager layoutManager;
    private HomePagerMiaoAdapte adaptes;
    private GridLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);


        //初始化权限
        initPermission();
        initview();
        LoadData();
        return view;
    }

    private void LoadData() {
        OkHttpUrl okHttpUrl = new OkHttpUrl();
        okHttpUrl.getdata(Urls.HOMEPAGER_URL, this, HomePagerData.class);
        RequestParams params = new RequestParams(Urls.GOODS_VIEWPAGER);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                HomePageBean bean = new Gson().fromJson(result, HomePageBean.class);
                list.addAll(bean.getData());
                viewPagerOneAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 初始化权限事件
     */
    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(getContext());
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(getActivity(), permissions, 100);
        }
    }

    private void initview() {
        ZXingLibrary.initDisplayOpinion(getActivity());
        LayoutInflater li = LayoutInflater.from(getActivity());
        mlists.add(li.inflate(R.layout.viewpager_one, null));
        mlists.add(li.inflate(R.layout.viewpager_two, null));

        madapter = new ViewPagerAdapter(mlists);
        viewpager.setAdapter(madapter);

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });


        goodsListTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);

                startActivity(intent);

            }
        });

//        recyclerviewOne.setLayoutManager(new GridLayoutManager(getActivity(),5));
//        viewPagerOneAdapter = new ViewPagerOneAdapter(getActivity(),list);
//        recyclerviewOne.setAdapter(viewPagerOneAdapter);


        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewOne.setLayoutManager(layoutManager);
        adaptes = new HomePagerMiaoAdapte(getActivity(), mlist_miao);
        recyclerviewOne.setAdapter(adaptes);
        manager = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerview.setLayoutManager(manager);
        //        //添加Android自带的分割线
//        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapte = new HomePagerAdapte(getActivity(), list_tuijian);
        recyclerview.setAdapter(adapte);

        cattegoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void success(HomePagerData homePagerData) {
        list_banner.addAll(homePagerData.getData());

        for (HomePagerData.DataBean bean : list_banner) {
            mlist.add(bean.getIcon());
        }
        //设置图片集合
        banner.setImages(mlist);
        //设置图片加载器
        banner.setImageLoader(new BannerImageLoaders(this));
        //设置显示样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置圆点位置  左右中
        banner.setIndicatorGravity(BannerConfig.CENTER);

        //banner设置方法全部调用完毕时最后调用
        banner.start();
        mlist_miao.addAll(homePagerData.getMiaosha().getList());
        list_tuijian.addAll(homePagerData.getTuijian().getList());
        adapte.notifyDataSetChanged();
        adaptes.notifyDataSetChanged();
    }

    @Override
    public void faild(int positon, String str) {

    }
}
