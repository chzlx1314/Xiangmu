package com.example.asus.jingdong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.jingdong.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述    添加地址
 */
public class AddressActivity extends AppCompatActivity {

    @BindView(R.id.add_address_name)
    TextView addAddressName;
    @BindView(R.id.add_address_phone)
    TextView addAddressPhone;
    @BindView(R.id.add_address_dizhi)
    TextView addAddressDizhi;
    @BindView(R.id.add_address)
    Button addAddress;
    @BindView(R.id.fanhui)
    TextView fanhui;
    String name,phone,diqu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_activity);
        ButterKnife.bind(this);
        initview();
    }

    public void initview() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        diqu = intent.getStringExtra("diqu");

        addAddressName.setVisibility(View.VISIBLE);
        addAddressName.setText(name);
        addAddressDizhi.setVisibility(View.VISIBLE);
        addAddressDizhi.setText(diqu);
        addAddressPhone.setVisibility(View.VISIBLE);
        addAddressPhone.setText(phone);


        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, XinAddressActivity.class);
                startActivity(intent);
            }
        });
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
