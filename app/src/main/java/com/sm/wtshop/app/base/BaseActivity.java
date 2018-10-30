package com.sm.wtshop.app.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.wsq.library.view.alertdialog.CustomDefaultDialog;
import com.example.wsq.library.view.loadding.LoadingDialog;
import com.orhanobut.logger.Logger;
import com.sm.wtshop.app.activity.LoginActivity;
import com.sm.wtshop.app.mvp.presenter.BasePresenter;
import com.sm.wtshop.app.mvp.view.BaseView;
import com.example.wsq.library.tools.status.AppStatus;
import com.umeng.analytics.MobclickAgent;

import java.util.Arrays;

import butterknife.ButterKnife;

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements BaseView {

    protected T ipresenter;
    private LoadingDialog dialog;
    private CustomDefaultDialog defaultDialog;
    public OnPermissionListener permissionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);


        setContentView(getLayoutId());
        AppStatus.setStatusBarLightMode(this, Color.WHITE);
        ipresenter = createPresenter();
        if (ipresenter != null) {
            ipresenter.attachView((V) this);
        }
        dialog = new LoadingDialog(this);
        ButterKnife.bind(this);

        initView(savedInstanceState);
    }

    protected abstract T createPresenter();

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ipresenter != null)
            ipresenter.deachView();
    }

    @Override
    public void showLoadding() {
        if (!dialog.isShowing()) dialog.show();
    }

    @Override
    public void dismissLoadding() {
        if (dialog.isShowing()) dialog.dismiss();
    }

    @Override
    public void loadFail(String errorMsg) {

    }

    @Override
    public void onReLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void onShowDialog(String title, String message, String okStr, DialogInterface.OnClickListener listener) {
        CustomDefaultDialog.Builder builder = new CustomDefaultDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setOkBtn(okStr, listener);
        defaultDialog = builder.create();
        defaultDialog.show();

    }

    public void onShowDialog(String title, String message, String okStr, DialogInterface.OnClickListener listener, String cancelStr) {
        CustomDefaultDialog.Builder builder = new CustomDefaultDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setOkBtn(okStr, listener)
                .setCancelBtn(cancelStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        defaultDialog = builder.create();
        defaultDialog.show();

    }

    public void onShowDialog(String title, String message, String okStr, DialogInterface.OnClickListener listener, String cancelStr, DialogInterface.OnClickListener cancelClickListener) {
        CustomDefaultDialog.Builder builder = new CustomDefaultDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setOkBtn(okStr, listener)
                .setCancelBtn(cancelStr, cancelClickListener);

        defaultDialog = builder.create();
        defaultDialog.show();

    }

    public void setPermissionListener(OnPermissionListener listener){
        this.permissionListener = listener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.d("返回码:{"+requestCode+"}, 请求的权限：{"+Arrays.toString(permissions)+"}, 结果： {"+Arrays.toString(grantResults)+"}");
//        PermissionUtils.requestPermissionsResult(getActivity(), requestCode, permissions, grantResults, permissionGrant);
        if (permissionListener != null){

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] ==-1){
                    permissionListener.onFail(permissions[i]);
                    return;
                }
            }

            permissionListener.onFinish();
        }
    }



    public interface OnPermissionListener {
        void onFinish();

        void onFail(String permission);
    }
}
