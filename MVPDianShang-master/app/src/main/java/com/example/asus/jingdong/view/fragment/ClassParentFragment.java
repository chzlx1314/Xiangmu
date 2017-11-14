package com.example.asus.jingdong.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.GoodsClassData;
import com.example.asus.jingdong.presenter.GetGoodsClassPresentes;
import com.example.asus.jingdong.view.IViews.IGetGoodsClassViews;
import com.example.asus.jingdong.view.adapter.RecyclerViewParentAdapter;

import java.util.List;
/**
 * 类描述    分类右边的RecyclerView页面
 */

public class ClassParentFragment extends Fragment implements IGetGoodsClassViews {


    private View view;

    private String goodsId;
    private GetGoodsClassPresentes presenter;

    private RecyclerView recyclerView;
    private RecyclerViewParentAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsId = getArguments().getString("goodsId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_class_parent, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.good_parent_list);

        presenter = new GetGoodsClassPresentes(this);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.getMoreData(goodsId);


    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void onGetDataSucceed(GoodsClassData bean) {

        /**
         * 获得回调的 bean数据
         *
         */

        if (bean != null) {

            List<GoodsClassData.DataBean> goodsList;
            goodsList = bean.getData();
            adapter = new RecyclerViewParentAdapter(goodsList, getContext());



            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);


                }
            });
        }


    }

    @Override
    public void onGetDataFail(String e) {

    }
}
