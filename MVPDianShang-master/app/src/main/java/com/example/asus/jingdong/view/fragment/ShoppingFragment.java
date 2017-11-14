package com.example.asus.jingdong.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.CarBean;
import com.example.asus.jingdong.presenter.GetGoodsCarPresentes;
import com.example.asus.jingdong.view.IViews.IGoodsSelectCartView;
import com.example.asus.jingdong.view.adapter.MyBaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 类描述    购物车页面
 */
public class ShoppingFragment extends Fragment implements IGoodsSelectCartView {
    @BindView(R.id.gwc_ex_listview)
    ExpandableListView EX_listview;
    @BindView(R.id.checkAll)
    CheckBox checkAll;
    @BindView(R.id.price)
    TextView total_price;
    @BindView(R.id.checked_shop)
    TextView checked_shop;
    Unbinder unbinder;
    private MyBaseExpandableListAdapter adapter;
    private List<CarBean.DataBean> mlist = new ArrayList<>();
    private View view;
    GetGoodsCarPresentes presentes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shopping_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presentes = new GetGoodsCarPresentes(this);
        presentes.getCarData();
        return view;
    }


    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void onCartSelectSucceed(CarBean bean) {
        mlist.addAll(bean.getData());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Listener(mlist);
            }
        });

    }

    //执行相关事件
    private void Listener(List<CarBean.DataBean> mlist) {
        //为二级列表设置适配器,list就是从数据库查询出来的数据
        adapter = new MyBaseExpandableListAdapter(getContext(), mlist);
        EX_listview.setAdapter(adapter);

        //遍历所有group,将所有项设置成默认展开
        int groupCount = EX_listview.getCount();
        for (int i = 0; i < groupCount; i++) {
            EX_listview.expandGroup(i);
        }
        //全选按钮
        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //代用适配器中的自定义方法,循环遍历二级列表,设置全选或全不选
                adapter.setCheckedAll();
                //得到查询到的选中项目的总条目数,和总价格
                String[] split = adapter.getShopNumber().split("\\|");
                //设置选中的商品个数
                checked_shop.setText("(" + split[1] + ")");
                //设置价格
                total_price.setText("合计:￥" + split[0]);
            }
        });
        //刷新选中的个数,和判断是否全部选中
        adapter.getNumberAndIsCheckAll(new MyBaseExpandableListAdapter.NumberAndIsCheckAll() {
            @Override
            public void getNumber(View view, String shop, boolean ischecked) {
                //split[0]=价格, split[1]个数
                String[] split = shop.split("\\|");
                //设置选中的商品个数
                checked_shop.setText("(" + split[1] + ")");
                //设置价格
                total_price.setText("合计:￥" + split[0]);
                //设置商品全部选中的时候,全选按钮也自动选中
                checkAll.setChecked(ischecked);
            }
        });

        //设置当所有商品全部选中的时候,全选按钮也设置选中状态
        checkAll.setChecked(adapter.selectShopAll());
        //刷新适配器
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCartDelectFail(String e) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
