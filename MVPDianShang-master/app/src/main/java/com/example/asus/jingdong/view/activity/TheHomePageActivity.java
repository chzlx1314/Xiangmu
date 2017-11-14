package com.example.asus.jingdong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.view.fragment.ClassificationFragment;
import com.example.asus.jingdong.view.fragment.FoundFragment;
import com.example.asus.jingdong.view.fragment.HomePageFragment;
import com.example.asus.jingdong.view.fragment.ShoppingFragment;
import com.example.asus.jingdong.view.fragment.TheMyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述  首页
 */
public class TheHomePageActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.radio_rb01)
    RadioButton radioRb01;
    @BindView(R.id.radio_rb02)
    RadioButton radioRb02;
    @BindView(R.id.radio_rb03)
    RadioButton radioRb03;
    @BindView(R.id.radio_rb04)
    RadioButton radioRb04;
    @BindView(R.id.radio_rb05)
    RadioButton radioRb05;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    private Fragment Home_Page, Classification, Found, Shopping, The_My;
    private FragmentManager fm;
    private FragmentTransaction ft;
    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.the_home_page);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        Home_Page = new HomePageFragment();
        Classification = new ClassificationFragment();
        Found = new FoundFragment();
        Shopping = new ShoppingFragment();
        The_My = new TheMyFragment();

        radioRb01.setOnClickListener(this);
        radioRb02.setOnClickListener(this);
        radioRb03.setOnClickListener(this);
        radioRb04.setOnClickListener(this);
        radioRb05.setOnClickListener(this);


        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        Intent intent = getIntent();
        int count = intent.getIntExtra("count", 0);
        int counts = intent.getIntExtra("counts", 0);
        int cid = intent.getIntExtra("cid", 0);

        String username = intent.getStringExtra("username");
        Bundle bundle = new Bundle();
        bundle.putString("name", username);
        The_My.setArguments(bundle);
        /***
         * 根据传的值，加载不同的页面
         */
        if (count == 1) {
            ft.add(R.id.framelayout, The_My, "The_My");
            ft.commit();
            radioRb05.setChecked(true);
        } else if (counts == 2) {
            ft.add(R.id.framelayout, Shopping, "Shopping");
            ft.commit();
            radioRb04.setChecked(true);
        }else if (cid == 12) {
            ft.add(R.id.framelayout, Classification, "Classification");
            ft.commit();
            radioRb02.setChecked(true);
        }else {
            ft.add(R.id.framelayout, Home_Page, "Home_Page");
            ft.commit();
        }

    }

    @Override
    public void onClick(View v) {
        ft = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.radio_rb01:
                if (!Home_Page.isAdded()) {
                    ft.add(R.id.framelayout, Home_Page, "Home_Page");
                }
                ft.show(Home_Page).hide(Classification).hide(Found).hide(Shopping).hide(The_My).commit();
                break;
            case R.id.radio_rb02:
                if (!Classification.isAdded()) {
                    ft.add(R.id.framelayout, Classification, "Classification");
                }
                ft.show(Classification).hide(Home_Page).hide(Found).hide(Shopping).hide(The_My).commit();
                break;
            case R.id.radio_rb03:
                if (!Found.isAdded()) {
                    ft.add(R.id.framelayout, Found, "Found");
                }
                ft.show(Found).hide(Classification).hide(Home_Page).hide(Shopping).hide(The_My).commit();
                break;
            case R.id.radio_rb04:
                if (!Shopping.isAdded()) {
                    ft.add(R.id.framelayout, Shopping, "Shopping");
                }
                ft.show(Shopping).hide(Classification).hide(Found).hide(Home_Page).hide(The_My).commit();
                break;
            case R.id.radio_rb05:
                if (!The_My.isAdded()) {
                    ft.add(R.id.framelayout, The_My, "The_My");
                }
                ft.show(The_My).hide(Classification).hide(Found).hide(Home_Page).hide(Shopping).commit();
                break;
            default:
                break;
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //再按一次退出程序
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(TheHomePageActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}