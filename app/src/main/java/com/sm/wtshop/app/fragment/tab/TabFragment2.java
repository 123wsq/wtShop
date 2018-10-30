package com.sm.wtshop.app.fragment.tab;


import android.view.View;

import com.sm.wtshop.app.R;
import com.sm.wtshop.app.base.BaseFragment;
import com.sm.wtshop.app.mvp.presenter.BasePresenter;
import com.sm.wtshop.app.mvp.view.BaseView;

import butterknife.OnClick;

public class TabFragment2 extends BaseFragment{

    public static final String TAG = TabFragment2.class.getName();


    public static TabFragment2 getInstance() {
        return new TabFragment2();
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_tab;
    }

    @Override
    protected void initView() {

    }


    @OnClick({})
    public void onClick(View view) {

        switch (view.getId()) {

        }
    }


}
