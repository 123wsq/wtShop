package com.sm.wtshop.app.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.example.wsq.library.struct.FunctionWithParamAndResult;
import com.sm.wtshop.app.R;
import com.sm.wtshop.app.base.BaseActivity;
import com.sm.wtshop.app.base.BaseApplication;
import com.sm.wtshop.app.base.BaseFragment;
import com.sm.wtshop.app.bean.PageParamBean;
import com.sm.wtshop.app.constant.ResponseKey;
import com.sm.wtshop.app.fragment.main.MainFragment;
import com.sm.wtshop.app.fragment.main.MenuFragment;
import com.sm.wtshop.app.mvp.presenter.DefaultPresenter;
import com.example.wsq.library.struct.FunctionWithParamOnly;
import com.example.wsq.library.struct.FunctionsManage;
import com.example.wsq.library.utils.SharedTools;
import com.example.wsq.library.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private Fragment curFragment;
    private FragmentManager fragmentManager;
    private List<Fragment> mListFragment;
    private int curSelectPosition = 1; //当前选中的menu
    public static String[] clearUserInfo = {};


    @Override
    protected DefaultPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mListFragment = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();

//        if (!NetworkUtils.isConnected()) {
//            onShowDialog("提示", "请检查您的网络", "确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    NetworkUtils.openWirelessSettings();
//                    dialog.dismiss();
//                }
//            });
//        }

        if (savedInstanceState != null && savedInstanceState.containsKey(ResponseKey.F_TAG)) {
            curSelectPosition = savedInstanceState.getInt(ResponseKey.fragment_menu_poi, 1);
            String[] tags = savedInstanceState.getStringArray(ResponseKey.F_TAG);
            for (int i = 0; i < tags.length; i++) {
                Message msg = new Message();
                msg.obj = tags[i];
                handler.sendMessageDelayed(msg, 500 + tags.length * 10);
            }
        }
        Bundle  bundle = new Bundle();
        bundle.putInt(ResponseKey.fragment_menu_poi, curSelectPosition);
        onEnter(MainFragment.getInstance(), MainFragment.TAG, bundle, false);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String[] tags = new String[mListFragment.size() - 1];
        for (int i = 1; i < mListFragment.size(); i++) {
            tags[i - 1] = mListFragment.get(i).getTag();
        }
        outState.putStringArray(ResponseKey.F_TAG, tags);
        outState.putInt(ResponseKey.fragment_menu_poi, curSelectPosition);

        //在保存数据之后只需要重启activity  不需要打开其所在的fragment
//        super.onSaveInstanceState(outState);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String tag = (String) msg.obj;
            try {
                Map<String, Fragment> fragments = BaseApplication.fragmentMap;
                if (fragments.containsKey(tag)) {
                    Fragment fragment = fragments.get(tag);
                    onEnter(fragment, tag, true);
                } else {
                    throw new Exception("未知的tag:" + tag);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };


    public void setFunctionsForFragment(String tag) {

        FragmentManager fm = getSupportFragmentManager();
        final BaseFragment fragment = (BaseFragment) fm.findFragmentByTag(tag);

        FunctionsManage functionsManage = FunctionsManage.getInstance();

        /**
         * 导航菜单栏事件
         */
        functionsManage.addFunction(new FunctionWithParamOnly<Integer>(MenuFragment.INTERFACE_WITHP) {
            @Override
            public void function(Integer data) {
                curSelectPosition = data;
                if (curFragment instanceof MainFragment) {
                    MainFragment fragment = (MainFragment) curFragment;
                    fragment.onShowFragnentContent(data);
                }
            }
        });

        /**
         * 参数的跳转 需要传入Fragment的ID
         * 数组第一个值必须是FragmentID
         * 第二个之后都是需要传递的参数
         */
        functionsManage.addFunction(new FunctionWithParamOnly<PageParamBean>(BaseFragment._INTERFACE_WITHP) {
            @Override
            public void function(PageParamBean  paramBean) { //第一个参数是id
                if (paramBean.isLoginIntercept()){
                    String token  = SharedTools.getInstance(MainActivity.this).onGetString(ResponseKey.token);
                    if (TextUtils.isEmpty(token)){
                        ToastUtils.onToast("请先登录！");
                        onReLogin();
                        return;
                    }
                }
                try {

                    if (paramBean.getFragmentTarget() ==null ||TextUtils.isEmpty(paramBean.getFragmentTag())){
                        ToastUtils.onToast("请设置需要跳转的fragment");
                        return;
                    }

                    onEnter(paramBean.getFragmentTarget(), paramBean.getFragmentTag(),paramBean.getBundle(), paramBean.isBack());
                } catch (Exception e) {
                    ToastUtils.onToast(e.getMessage());
                    e.printStackTrace();
                }

            }
        });

        functionsManage.addFunction(new FunctionWithParamOnly<Object[]>(BaseFragment.INTERFACE_PERMISSION) {
            @Override
            public void function(Object[] data) {
                //权限申请
                String[] permissions = (String[]) data[1];
                setPermissionListener((OnPermissionListener) data[0]);
                ActivityCompat.requestPermissions(MainActivity.this, permissions,200);


            }
        });
        /**
         * 返回按钮的事件监听
         */
        functionsManage.addFunction(new FunctionWithParamOnly<String>(BaseFragment.INTERFACE_BACK) {
            @Override
            public void function(String tag) { //跳转到目标
                if (TextUtils.isEmpty(tag)) {
                    onKeyBack();
                }else {
                    for (int i = mListFragment.size() - 1; i > 0; i--) {
                        Fragment fragment = mListFragment.get(i);

                        if (fragment.getTag().equals(tag)) {
                            return;
                        } else {
                            fragmentManager.popBackStack();
                            mListFragment.remove(mListFragment.size() - 1);
                            curFragment = mListFragment.get(mListFragment.size() - 1);
                        }
                    }
                }
            }
        });

        fragment.setFunctionsManager(functionsManage);
    }


    private void onEnter(Fragment fragment, String tag) {
        onEnter(fragment, tag, true);
    }
    /**
     * @param fragment
     * @param tag
     * @param isBack   是否支持返回
     */
    private void onEnter(Fragment fragment, String tag, boolean isBack) {
        onEnter(fragment, tag, null, isBack);
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

            mListFragment.add(fragment);
            fTransaction.add(R.id.layout_content, fragment, tag);
            if (isBack) fTransaction.addToBackStack(tag);
            fTransaction.show(fragment).commit();
        } else {
            fTransaction.show(fragment).commit();
        }
        curFragment = fragment;
    }



    private void onKeyBack() {

        fragmentManager.popBackStack();
        if (mListFragment.size() > 1) {
            mListFragment.remove(mListFragment.size() - 1);
            curFragment = mListFragment.get(mListFragment.size() - 1);
        } else {
            onExitApp(false, -1);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                onKeyBack();
                return false;
            }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *
     * @param isCache 清除缓存
     * @param type -1 退出应用
     */
    private void onExitApp(final boolean isCache, final int type) {
        String msg = type == -1 ? "您确定退出应用吗？" : "您确定退出当前用户？";
        onShowDialog("提示", msg, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (isCache) {
                    SharedTools.getInstance(MainActivity.this).onClearUserInfo(clearUserInfo);
                }
                dialog.dismiss();
                if (type == -1) {
                    finish();
                } else if (type == 0) {
                    //   onEnter(LoginFragment.getInstance(), LoginFragment.TAG, true);
                }
            }
        }, "取消");
    }



}
