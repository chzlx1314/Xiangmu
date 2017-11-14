package com.example.asus.jingdong.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.FoundBean;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.utils.Urls;
import com.example.asus.jingdong.view.adapter.HeaderBottomAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类描述     发现页面的子页面
 */
public class FoundFragmentItem extends Fragment implements NetDataCallBack<FoundBean>, XRecyclerView.LoadingListener {
    @BindView(R.id.rv_list)
    XRecyclerView recyclerview;
    Unbinder unbinder;
    private int id;
    private String path;
    private String url_1 = Urls.FOUND01;
    private String url_2 = Urls.FOUND02;
    private String url_3 = Urls.FOUND03;
    private List<FoundBean.DataBean> mlist = new ArrayList<>();
    private HeaderBottomAdapter adapter;
    GridLayoutManager gridLayoutManager;
    StaggeredGridLayoutManager manager;

    public static FoundFragmentItem getInstance(int id) {
        FoundFragmentItem myFragment = new FoundFragmentItem();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.found_fragnment_item01, container, false);
        unbinder = ButterKnife.bind(this, view);
        id = getArguments().getInt("id");
        if (id == 0) {
            path = url_1;
        } else if (id == 1) {
            path = url_2;
        } else {
            path = url_3;
        }
        initview();
        loadData();
        return view;
    }

    private void loadData() {
        OkHttpUrl okHttpUrl = new OkHttpUrl();
        okHttpUrl.getdata(path,this,FoundBean.class);

    }

    private void initview() {
        if (path == url_1) {
            recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else if (path == url_2) {
            gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerview.setLayoutManager(gridLayoutManager);

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (adapter.isHeaderView(position) || adapter.isBottomView(position)) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        } else {
            manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            //瀑布流一行代码
            recyclerview.setLayoutManager(manager);

        }
        recyclerview.setLoadingListener(this);
        adapter = new HeaderBottomAdapter(getActivity(), mlist);
        recyclerview.setAdapter(adapter);

    }
  

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void success(FoundBean foundBean) {
        mlist.clear();
        mlist.addAll(foundBean.getData());
        adapter.notifyDataSetChanged();
        StopRecyclerView();
    }

    private void StopRecyclerView() {
        recyclerview.loadMoreComplete();
        recyclerview.refreshComplete();
    }

    @Override
    public void faild(int positon, String str) {

    }

    @Override
    public void onRefresh() {
        mlist.clear();
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadData();
    }
}
