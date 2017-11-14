package com.example.asus.jingdong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.asus.jingdong.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.time)
    TextView mtime;
    private int time_count = 3;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mtime.setText("("+time_count+")秒");
            if(time_count == 0){
                Intent intent = new Intent(MainActivity.this,TheHomePageActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initview();
        mtime.setOnClickListener(this);
    }

    private void initview() {
        // TODO Auto-generated method stub
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if(time_count > 0){
                    time_count --;
                    handler.sendEmptyMessage(0);
                }
            }
        }, 0, 1000);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,TheHomePageActivity.class);
        time_count = 0;
        startActivity(intent);
        finish();
    }
}

