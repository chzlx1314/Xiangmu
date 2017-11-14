package com.example.asus.jingdong.view.IViews;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.asus.jingdong.view.fragment.HomePageFragment;

/**
 * 类描述
 */
public class BannerImageLoaders extends com.youth.banner.loader.ImageLoader{
    public BannerImageLoaders(HomePageFragment fragment1) {
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}