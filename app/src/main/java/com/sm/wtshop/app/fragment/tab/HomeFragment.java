package com.sm.wtshop.app.fragment.tab;


import android.view.View;

import com.sm.wtshop.app.R;
import com.sm.wtshop.app.base.BaseFragment;
import com.sm.wtshop.app.fragment.base.CitySelectFragment;
import com.sm.wtshop.app.mvp.presenter.BasePresenter;
import com.sm.wtshop.app.mvp.view.BaseView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment{

    public static final String TAG = HomeFragment.class.getName();

    @BindView(R.id.banner) Banner banner;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_tab_home;
    }

    @Override
    protected void initView() {

        onInitBanner();
    }




    @OnClick({R.id.tv_select_city})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_select_city:
                paramBean.setFragmentTarget(CitySelectFragment.getInstance());
                paramBean.setFragmentTag(CitySelectFragment.TAG);
                mFunctionsManage.invokeFunction(_INTERFACE_WITHP, paramBean);
                break;
        }
    }

    private void onInitBanner(){
        List<String> mImages = new ArrayList<>();
        mImages.add("https://m.360buyimg.com/babel/jfs/t27343/328/1016158583/95767/12382e3/5bbf0decN25d5afce.jpg");
        mImages.add("https://img1.360buyimg.com/da/jfs/t25075/91/1052826311/103082/763d309e/5b8744cdNdd55da3f.jpg");
        mImages.add("https://img1.360buyimg.com/pop/jfs/t1/8856/5/205/102229/5bc97ccaEd0f4267b/8277214e389eb888.jpg");
        mImages.add("https://m.360buyimg.com/babel/jfs/t27883/166/1383614552/85789/2f51dc0f/5bc8493cN72a021a5.jpg");
        mImages.add("https://img20.360buyimg.com/da/jfs/t18685/238/1455346844/110119/f5bc9082/5acb3b0eN79be7f6e.jpg");
        mImages.add("https://m.360buyimg.com/babel/jfs/t1/3728/40/10022/76135/5bc96a24Eddad38cf/14965075b7f6bdde.jpg");
        mImages.add("https://m.360buyimg.com/babel/jfs/t27292/176/1306970278/90530/ac7f99ac/5bc69e65N4f896805.jpg");
        mImages.add("https://m.360buyimg.com/babel/jfs/t1/3136/12/10001/101434/5bc98023E69ff6cb2/2765cd6a701acc76.jpg");

        onInitBanner(banner, mImages);
    }


}
