package com.example.asus.jingdong.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bane.Registere;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述  注册
 */
public class RegistereActivity extends AppCompatActivity implements View.OnClickListener, Registere.Registeress {
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.user_pwd)
    EditText userPwd;
    @BindView(R.id.button_logout)
    Button buttonLogout;
    @BindView(R.id.return_login)
    ImageView returnLogin;
    private String susername;
    private String spassword;
    private Registere registere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.registered_activity);
        ButterKnife.bind(this);
        initviews();

    }

    private void initviews() {
        registere = new Registere();
        returnLogin.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_login:
                //弹出一个对话框
                new AlertDialog.Builder(RegistereActivity.this)
                        .setTitle("确认您的选择")
                        .setMessage("返回将清空您正在输入的内容")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
                break;
            case R.id.button_logout:
                susername = userName.getText().toString().trim();
                spassword = userPwd.getText().toString().trim();
                registere.initview(susername, spassword);
                registere.setRegisteress(this);
                break;

        }

    }

    @Override
    public void RegistereSuccess(String error) {
        Toast.makeText(this, error+"两秒后跳转到登陆页面", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegistereActivity.this, LoagActivity.class);
        intent.putExtra("phone", susername);
        startActivity(intent);
        finish();
    }

    @Override
    public void RegistereFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
