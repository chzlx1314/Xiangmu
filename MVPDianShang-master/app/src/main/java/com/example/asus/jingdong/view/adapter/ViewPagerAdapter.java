package com.example.asus.jingdong.view.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 类描述    首页ViewPager的适配器
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> list;

    public ViewPagerAdapter(List<View> mlist){
        list = mlist;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list ==null?0:list.size();
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    //销毁View
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position);
        container.addView(view);
        return view;
    }

}

