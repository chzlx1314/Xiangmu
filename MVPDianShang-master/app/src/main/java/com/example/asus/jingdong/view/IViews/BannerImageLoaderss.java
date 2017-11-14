package com.example.asus.jingdong.view.IViews;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.asus.jingdong.view.fragment.ClassificationFragment;

/**
 * 类描述
 */
public class BannerImageLoaderss extends com.youth.banner.loader.ImageLoader{
    public BannerImageLoaderss(ClassificationFragment fragment1) {
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}