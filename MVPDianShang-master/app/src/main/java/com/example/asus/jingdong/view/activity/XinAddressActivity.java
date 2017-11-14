package com.example.asus.jingdong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bean.AddRessBean;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.utils.Urls;

import okhttp3.FormBody;

/**
 * 类描述  新建地址
 */
public class XinAddressActivity extends AppCompatActivity implements NetDataCallBack<AddRessBean> {


    TextView addfanhui;
    EditText addname;
    EditText addphone;
    EditText adddiqu;
    Button addBao;
    private String name, phone, diqu;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xinaddress_activity);
        initView();
    }

    private void loadData() {
        uid = String.valueOf(149);
        name = addname.getText().toString().trim();
        phone = addphone.getText().toString().trim();
        diqu = adddiqu.getText().toString().trim();
        OkHttpUrl okHttpUrl = new OkHttpUrl();
        FormBody body = new FormBody.Builder()
                .add("uid", uid)
                .add("addr", diqu)
                .add("mobile", phone)
                .add("name", name)
                .build();
        okHttpUrl.getLoadDataPost(Urls.CART_RSECSEE, this, AddRessBean.class, body);
    }

    private void initView() {

        addname = (EditText) findViewById(R.id.addname);
        adddiqu = (EditText) findViewById(R.id.adddiqu);
        addphone = (EditText) findViewById(R.id.addphone);
        addfanhui = (TextView) findViewById(R.id.addfanhui);
        addBao = (Button) findViewById(R.id.add_bao);


        addBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        addfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void success(AddRessBean addRessBean) {
        String msg = addRessBean.getMsg();
        int code = Integer.parseInt(addRessBean.getCode());
        if (code == 0) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(XinAddressActivity.this,AddressActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("phone",phone);
        intent.putExtra("diqu",diqu);
        startActivity(intent);
        finish();



    }

    @Override
    public void faild(int positon, String str) {
        Toast.makeText(this, "网络发生错误", Toast.LENGTH_SHORT).show();
    }
}
