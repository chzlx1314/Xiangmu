package com.example.asus.jingdong.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.jingdong.R;
import com.example.asus.jingdong.model.bane.Loag;
import com.example.asus.jingdong.model.bean.LoagBean;
import com.example.asus.jingdong.model.net.MyApp;
import com.example.asus.jingdong.utils.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述  登陆
 */
public class LoagActivity extends AppCompatActivity implements Loag.ILogin, View.OnClickListener {
    @BindView(R.id.return_login)
    ImageView returnLogin;
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.user_pwd)
    EditText userPwd;
    @BindView(R.id.button_logout)
    Button buttonLogout;
    @BindView(R.id.qq_login)
    ImageView qqLogin;
    @BindView(R.id.phone_register)
    TextView phoneRegister;
    @BindView(R.id.login_bar)
    ProgressBar loginBar;
    @BindView(R.id.clien_name)
    ImageView clienName;
    @BindView(R.id.clien_pwd)
    ImageView clienPwd;
    @BindView(R.id.jian)
    ImageView jian;
    private boolean isOpen = false;
    private String username;
    private String password;
    private Loag loag;
    private int count = 1;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.th_my_loag_avtivity);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        returnLogin.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        phoneRegister.setOnClickListener(this);
        clienPwd.setOnClickListener(this);
        clienName.setOnClickListener(this);
        jian.setOnClickListener(this);

        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        userName.setText(phone);
        loag = new Loag();
        buttonLogout.setOnClickListener(this);

        // 监听文本框内容变化
        userName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 获得文本框中的用户
                String user = userName.getText().toString().trim();
                if ("".equals(user)) {
                    // 用户名为空,设置按钮不可见
                    clienName.setVisibility(View.INVISIBLE);
                } else {
                    // 用户名不为空，设置按钮可见
                    clienName.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 监听文本框内容变化
        userPwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 获得文本框中的用户
                String pwd = userPwd.getText().toString().trim();
                if ("".equals(pwd)) {
                    // 用户名为空,设置按钮不可见
                    clienPwd.setVisibility(View.INVISIBLE);
                } else {
                    // 用户名不为空，设置按钮可见
                    clienPwd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 判断点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_login:
                finish();
                break;
            case R.id.qq_login:
                Toast.makeText(this, "QQ登陆", Toast.LENGTH_SHORT).show();
                break;
            case R.id.phone_register:
                Intent intent = new Intent(LoagActivity.this, RegistereActivity.class);
                startActivity(intent);
                break;
            case R.id.button_logout:
                username = userName.getText().toString().trim();
                password = userPwd.getText().toString().trim();
                loag.initview(username, password);
                loag.setiLogin(this);
                loginBar.setVisibility(View.VISIBLE);
                break;
            case R.id.clien_name:
                userName.setText("");
                break;
            case R.id.clien_pwd:
                userPwd.setText("");
                break;
            case R.id.jian:
                // 密码可见与不可见的切换
                if (isOpen) {
                    isOpen = false;
                } else {
                    isOpen = true;
                }

                // 默认isOpen是false,密码不可见
                changePwdOpenOrClose(isOpen);
                break;


        }

    }
    /**
     * 密码可见与不可见的切换
     *
     * @param flag
     */
    private void changePwdOpenOrClose(boolean flag) {
        // 第一次过来是false，密码不可见
        if (flag) {
            // 密码可见
            // 设置EditText的密码可见
            userPwd.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());
        } else {
            // 密码不接见
            // 设置EditText的密码隐藏
            userPwd.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
        }
    }

    @Override
    public void loginSuccess(String error, final LoagBean bean) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoagActivity.this, TheHomePageActivity.class);
                intent.putExtra("count", count);
                intent.putExtra("username", username.trim());
                SharedPreferences sp = MyApp.getUserInfoSp();
                SharedPreferences.Editor editor =  sp.edit();
                editor.putString(UserInfo.USER_ID, String.valueOf(bean.getData().getUid()));
                editor.putBoolean(UserInfo.IS_LOGGING,true);//存为登录中状态 true
                editor.commit();
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    public void loginFail(String error) {
        loginBar.setVisibility(View.GONE);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }


}
