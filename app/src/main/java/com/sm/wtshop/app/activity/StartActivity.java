package com.sm.wtshop.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sm.wtshop.app.R;
import com.sm.wtshop.app.base.BaseActivity;
import com.sm.wtshop.app.mvp.presenter.BasePresenter;
import com.example.wsq.library.utils.IntentUtils;


public class StartActivity extends BaseActivity {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_activity_app_start;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        handler.sendMessageDelayed(new Message(), 1000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            try {
                IntentUtils.onStartActivity(StartActivity.this, WellcomeActivity.class, null);
                overridePendingTransition(R.anim.anim_activity_default_in, R.anim.anim_activity_default_out);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
