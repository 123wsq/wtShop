package com.sm.wtshop.app.fragment.tab;


import android.Manifest;
import android.view.View;

import com.example.wsq.library.tools.RequestParamParse;
import com.example.wsq.library.utils.RSAUtils;
import com.example.wsq.library.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.sm.wtshop.app.R;
import com.sm.wtshop.app.base.BaseActivity;
import com.sm.wtshop.app.base.BaseFragment;
import com.sm.wtshop.app.mvp.presenter.BasePresenter;
import com.sm.wtshop.app.mvp.view.BaseView;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

public class TabFragment1 extends BaseFragment{

    public static final String TAG = TabFragment1.class.getName();


    public static TabFragment1 getInstance() {
        return new TabFragment1();
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
        Map<String, String> param = new HashMap<>();
        param.put("token","abcdelksdjklajs");
        param.put("user_id","1234545");
        String encrypt = RequestParamParse.onEncrypt(param);
        Logger.d(encrypt);
        String result = RequestParamParse.onDecrypt(encrypt);
        Logger.d(result);

        String[] permissions ={Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_CONTACTS};
        onRequestPermission(permissions, new BaseActivity.OnPermissionListener() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onFail(String permission) {

                switch (permission){
                    case Manifest.permission.ACCESS_FINE_LOCATION:
                        onShowDialog("权限提示","您没有授权定位，在后面的位置信息中可能出现没法预料的错误，如想重新打开请在设置页面中打开！");
                        break;
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        onShowDialog("权限提示","您没有sd卡的权限，在后面的位置信息中可能出现没法预料的错误，如想重新打开请在设置页面中打开！");
                        break;
                    case Manifest.permission.CALL_PHONE:
                        onShowDialog("权限提示","您没有直接拨打电话的权限，在后面的位置信息中可能出现没法预料的错误，如想重新打开请在设置页面中打开！");
                        break;
                    case Manifest.permission.WRITE_CONTACTS:
                        onShowDialog("权限提示","您没有编写联系人的权限，在后面的位置信息中可能出现没法预料的错误，如想重新打开请在设置页面中打开！");
                        break;
                }
            }
        });
    }


    @OnClick({})
    public void onClick(View view) {

        switch (view.getId()) {

        }
    }


}
