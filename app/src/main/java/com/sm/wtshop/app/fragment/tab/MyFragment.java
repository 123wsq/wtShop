package com.sm.wtshop.app.fragment.tab;


import android.view.View;
import com.sm.wtshop.app.R;
import com.sm.wtshop.app.base.BaseFragment;
import com.sm.wtshop.app.mvp.presenter.BasePresenter;
import com.sm.wtshop.app.mvp.view.BaseView;
import java.util.Map;
import butterknife.OnClick;

public class MyFragment extends BaseFragment{

    public static final String TAG = MyFragment.class.getName();


    public static MyFragment getInstance() {
        return new MyFragment();
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_tab_my;
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
