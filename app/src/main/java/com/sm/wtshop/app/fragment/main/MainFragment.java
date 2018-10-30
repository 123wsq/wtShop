package com.sm.wtshop.app.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sm.wtshop.app.constant.ResponseKey;
import com.sm.wtshop.app.fragment.tab.HomeFragment;
import com.sm.wtshop.app.R;
import com.sm.wtshop.app.base.BaseFragment;
import com.sm.wtshop.app.fragment.tab.MyFragment;
import com.sm.wtshop.app.fragment.tab.TabFragment1;
import com.sm.wtshop.app.fragment.tab.TabFragment2;
import com.sm.wtshop.app.mvp.presenter.BasePresenter;

/**
 * Created by wsq on 2018/1/23.
 */

public class MainFragment extends BaseFragment {


    public static final String TAG = MainFragment.class.getName();

    private Fragment fragments[] = {MenuFragment.getInstance(),
            HomeFragment.getInstance(),
            TabFragment1.getInstance(),
            TabFragment2.getInstance(),
            MyFragment.getInstance(),
            MyFragment.getInstance()};
    private String[] tags = {MenuFragment.TAG, HomeFragment.TAG, TabFragment1.TAG,TabFragment2.TAG, MyFragment.TAG,};

    private FragmentManager fragmentManager;
    private Fragment curFragment;

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_fragment_main;
    }

    @Override
    public void initView() {

        fragmentManager = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt(ResponseKey.fragment_menu_poi, getArguments().getInt(ResponseKey.fragment_menu_poi, 1));
        onEnter(R.id.ll_menu, fragments[0], bundle,tags[0]);

    }

    public void onEnter(int resource, Fragment fragment, Bundle bundle, String tag) {
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(resource, fragment, tag).commit();
    }

    /**
     * @param fragment
     * @param tag
     * @param param    传递的参数
     * @param isBack   是否支持返回
     */
    private void onEnter(Fragment fragment, String tag, Bundle param, boolean isBack) {
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();

        if (curFragment != null) fTransaction.hide(curFragment);

        if (!fragment.isAdded()) {
            if (param != null) fragment.setArguments(param);

            fTransaction.add(R.id.ll_content, fragment, tag);
            if (isBack) fTransaction.addToBackStack(tag);
            fTransaction.show(fragment).commit();
        } else {
            fTransaction.show(fragment).commit();
        }
        curFragment = fragment;
    }


    /**
     * 点击菜单切换tab
     *
     * @param position
     */
    public void onShowFragnentContent(int position) {
        onEnter(fragments[position], tags[position], null, false);

    }


}
