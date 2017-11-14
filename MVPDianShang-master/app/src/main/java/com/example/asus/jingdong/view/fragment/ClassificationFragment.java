package com.example.asus.jingdong.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.GoodsClassBean;
import com.example.asus.jingdong.model.bean.HomePagerData;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.presenter.CheckPermissionUtils;
import com.example.asus.jingdong.presenter.GetGoodsClassPresenter;
import com.example.asus.jingdong.utils.Urls;
import com.example.asus.jingdong.view.IViews.BannerImageLoaderss;
import com.example.asus.jingdong.view.IViews.IGetGoodsClassView;
import com.example.asus.jingdong.view.activity.SearchActivity;
import com.example.asus.jingdong.view.adapter.ClassListViewAdapter;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类描述    分类主页面
 */
public class ClassificationFragment extends Fragment implements IGetGoodsClassView, NetDataCallBack<HomePagerData> {
    private static final int REQUEST_CODE = 111;
    Unbinder unbinder;
    private View view;
    private ListView listView;
    private FrameLayout fm_parent;

    private GetGoodsClassPresenter presenter;

    private List<GoodsClassBean.DataBean> goodsList;
    private ClassListViewAdapter adapter;
    private EditText topBar;
    private Banner banner;
    private List<HomePagerData.DataBean> list_banner = new ArrayList<>();
    private List<String> mlist = new ArrayList<>();
    private RadioButton cattegoryimage;


    //可见列表项的数量
    private int visibleCount = 0;
    //列表正中 占得条目数
    private int ce = 0;
    //实际列表是否超出屏幕
    private boolean isOut = true;
    private String cids;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_classify, container, false);

        //初始化权限
        initPermission();
        unbinder = ButterKnife.bind(this, view);
        return view;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.class_list);//左侧分类列表

        fm_parent = (FrameLayout) view.findViewById(R.id.fraagment_container);

        banner = (Banner) view.findViewById(R.id.banner);

        presenter = new GetGoodsClassPresenter(this);

        cattegoryimage = (RadioButton) view.findViewById(R.id.cattegory_image);

        topBar = (EditText) view.findViewById(R.id.goods_list_top_bar);

        topBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);

            }
        });
        cattegoryimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        LoadData();


    }


    private void LoadData() {
        OkHttpUrl okHttpUrl = new OkHttpUrl();
        okHttpUrl.getdata(Urls.HOMEPAGER_URL, this, HomePagerData.class);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //调用 presenter 获得数据的方法
        presenter.getData();


    }

    public void changeFragment(String goodsId) {
        ClassParentFragment fragment = new ClassParentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId", goodsId);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fraagment_container, fragment);

        transaction.commit();
    }

    public void setFirstFragment() {
        int cid = goodsList.get(0).getCid();
        cids = String.valueOf(cid);
        ClassParentFragment fragment = new ClassParentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId", cids);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fraagment_container, fragment);

        transaction.commit();
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void onGetDataSucceed(final GoodsClassBean bean) {

        /**
         * 获得回调的 bean数据
         *
         */

        goodsList = bean.getData();
        goodsList.addAll(goodsList);
        if (goodsList != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new ClassListViewAdapter(goodsList, context());

                    listView.setAdapter(adapter);

                    setFirstFragment();//默认右侧设置第一层fragment

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            int cid = goodsList.get(position).getCid();
                            String goodsId = String.valueOf(cid);

                            adapter.setSelected(position); //点击时改变该条目的背景

                            changeFragment(goodsId); //改变右侧的fragment


                            //计算滑动
                            if (visibleCount == 0) {
                                visibleCount = listView.getChildCount();
                                if (visibleCount == goodsList.size())
                                    isOut = false;
                                else {
                                    ce = visibleCount / 2;
                                }
                            }

                            //如果点击条目高于 此列表的正中 则 列表整体上移 把条目移到此正中
                            if (position <= (parent.getFirstVisiblePosition() + ce)) {   //上移
                                listView.smoothScrollToPosition(position - ce);
                            }
                            //如果点击条目低于 此列表的正中 则 列表整体下移 把此条目移到正中
                            else {   //下移
                                if ((parent.getLastVisiblePosition() + ce + 1) <= parent.getCount()) {
                                    listView.smoothScrollToPosition(position + ce);
                                }
                                //如果点击条目位于底部 则 列表整不做移动操作
                                else {
                                    listView.smoothScrollToPosition(parent.getCount() - 1);
                                }

                            }


                        }
                    });
                }
            });


        }


    }

    @Override
    public void onGetDataFail(final String e) {

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
        banner.setImageLoader(new BannerImageLoaderss(this));
        //设置显示样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置圆点位置  左右中
        banner.setIndicatorGravity(BannerConfig.CENTER);

        //banner设置方法全部调用完毕时最后调用
        banner.start();

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
