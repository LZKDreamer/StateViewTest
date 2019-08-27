package com.lzk.stateviewtest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private Button mAgainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("shiZi","sub before super.onCreate");
        super.onCreate(savedInstanceState);
        Log.d("shiZi","sub onCreate");

        mAgainBtn=findViewById(R.id.main_again_btn);
        mAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading("");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showEmpty("");
                    }
                },3000);
            }
        });
        showLoading("loading");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showError("Oh,something error");
            }
        },3000);
    }

    @Override
    public void reload() {
        super.reload();
        showLoading("loading");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showNormal();
            }
        },5000);
    }
}
