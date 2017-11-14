package com.example.asus.jingdong.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.FoundBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述    发现页面的适配器
 */
public class HeaderBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 1;//底部View个数
    List<FoundBean.DataBean> mlist;
    private Banner banner01,banner02;
    private List<String> mlists = new ArrayList<>();

    public HeaderBottomAdapter(Context context, List<FoundBean.DataBean> mlist) {
        mContext = context;
        this.mlist = mlist;
        mLayoutInflater = LayoutInflater.from(context);
        //初始化数据
        loadData();

    }

    private void loadData() {
        mlists.add("http://f2.kkmh.com/image/170216/8ijckeaej.webp-w640");
        mlists.add("http://f2.kkmh.com/image/170216/p42ug0qn7.webp-w640");
        mlists.add("http://f2.kkmh.com/image/170217/h2ohl0x4o.webp-w640");
    }

    //内容长度
    public int getContentItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView name,are,zhiye,xinxi;
        private ImageView image;

        public ContentViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            are = (TextView) itemView.findViewById(R.id.are);
            zhiye = (TextView) itemView.findViewById(R.id.zhiye);
            xinxi = (TextView) itemView.findViewById(R.id.xinxi);
            image = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }

    //头部 ViewHolder
    public class HeaderViewHolder extends RecyclerView.ViewHolder {



        public HeaderViewHolder(View itemView) {
            super(itemView);
            banner01 = (Banner) itemView.findViewById(R.id.banner01);
        }
    }

    //底部 ViewHolder
    public class BottomViewHolder extends RecyclerView.ViewHolder {


        public BottomViewHolder(View itemView) {
            super(itemView);
            banner02 = (Banner) itemView.findViewById(R.id.banner02);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.rv_header, parent, false));
        } else if (viewType == mHeaderCount) {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.rv_item, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(mLayoutInflater.inflate(R.layout.rv_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            banner01.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);

                }
            });
            banner01.setImages(mlists);
            banner01.setBannerStyle(BannerConfig.NUM_INDICATOR);
            banner01.setDelayTime(2000);
            banner01.start();
        } else if (holder instanceof ContentViewHolder) {

            ((ContentViewHolder)holder).name.setText(mlist.get(position-mHeaderCount).getTitle());
            ((ContentViewHolder) holder).xinxi.setText(mlist.get(position-mHeaderCount).getIntroduction());
            //图文混排改变字体颜色
            SpannableString spannableString = new SpannableString(mlist.get(position-mHeaderCount).getUserAge()+"");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
            spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ((ContentViewHolder) holder).are.setText(spannableString);

            SpannableString spannableString1 = new SpannableString(mlist.get(position-mHeaderCount).getOccupation());
            ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#06D200"));
            spannableString1.setSpan(colorSpan1, 0, spannableString1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ((ContentViewHolder) holder).zhiye.setText(spannableString1);
            //设置动画
            Animation animation = new AlphaAnimation(0.1f,1.0f);
            animation.setDuration(5000);
            ((ContentViewHolder) holder).image.setAnimation(animation);
            Glide.with(mContext).load(mlist.get(position-mHeaderCount).getUserImg()).into(((ContentViewHolder) holder).image);
        } else if (holder instanceof BottomViewHolder) {
            banner02.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);

                }
            });
            banner02.setImages(mlists);
            banner02.setBannerStyle(BannerConfig.NUM_INDICATOR);
            banner02.setDelayTime(2000);
            banner02.start();
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }
}