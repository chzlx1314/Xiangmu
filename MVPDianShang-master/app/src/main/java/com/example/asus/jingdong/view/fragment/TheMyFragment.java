package com.example.asus.jingdong.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.utils.Urls;
import com.example.asus.jingdong.view.activity.AddressActivity;
import com.example.asus.jingdong.view.activity.LoagActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.yalantis.taurus.PullToRefreshView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类描述    用户页面
 */
public class TheMyFragment extends Fragment {
    private static final int REFRESH_DELAY = 111;
    @BindView(R.id.my_login)
    TextView myLogin;
    Unbinder unbinder;
    @BindView(R.id.my_loginn)
    ImageView myLoginn;
    @BindView(R.id.user_address)
    TextView userAddress;
    private String photo, userphoto, username;
    PullToRefreshView mPullToRefreshView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.th_my_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        photo = (String) getArguments().get("name");
//        userphoto = (String) getArguments().get("userPhoto");
//        username = (String) getArguments().get("usernames");
        if (photo != null) {
            myLogin.setText(photo);

            DisplayImageOptions displayImageOptions = new DisplayImageOptions
                    .Builder()
                    .displayer(new CircleBitmapDisplayer())
                    .build();
            ImageLoader.getInstance().displayImage(Urls.BANNER_URL04, myLoginn, displayImageOptions);
        }

        initview(view);
        return view;
    }

    private void initview(View view) {
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        myLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoagActivity.class);
                startActivity(intent);
            }
        });
        myLoginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoagActivity.class);
                startActivity(intent);
            }
        });

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
        userAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddressActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

