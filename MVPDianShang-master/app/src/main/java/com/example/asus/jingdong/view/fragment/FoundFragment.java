package com.example.asus.jingdong.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.jingdong.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类描述    发现页面
 */
public class FoundFragment extends Fragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager pager;
    Unbinder unbinder;
    private String[] titles = {"第一波", "第二波", "第三波"};
    private FoundFragmentItem[] myfragments;
    private List<String> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.found_fragnment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview();
        return view;
    }

    private void initview() {

        for (String bean:titles){
            list.add(bean);
        }
        for (String title :list){
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }

        myfragments = new FoundFragmentItem[list.size()];
        pager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(myfragments[position] == null){
                    FoundFragmentItem myfragment = FoundFragmentItem.getInstance(position);
                    myfragments[position] = myfragment;
                }
                return myfragments[position];
            }

            @Override
            public int getCount() {
                return myfragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }
        });
        tabLayout.setupWithViewPager(pager);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
