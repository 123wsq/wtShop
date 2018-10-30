package com.sm.wtshop.app.mvp.model.impl;


import com.example.wsq.library.okhttp.HttpRequest;
import com.example.wsq.library.okhttp.OkhttpUtil;
import com.example.wsq.library.okhttp.callback.OnMvpCallBack;
import com.example.wsq.library.tools.RequestParamParse;
import com.example.wsq.library.utils.SharedTools;
import com.example.wsq.library.utils.ToastUtils;
import com.sm.wtshop.app.activity.MainActivity;
import com.sm.wtshop.app.constant.ResponseKey;
import com.sm.wtshop.app.mvp.callback.OnResponseCallBack;
import com.sm.wtshop.app.mvp.model.inter.RequestHttpModel;

import com.sm.wtshop.app.mvp.view.BaseView;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.List;
import java.util.Map;


public class RequestHttpModelImpl implements RequestHttpModel {

    private HttpRequest request;

    public RequestHttpModelImpl() {
        request = new HttpRequest();
    }



    private void onPinParam(BaseView view, Map<String, String> param) throws Exception {

        MobclickAgent.onEvent(view.getContext(), "request_count");
//        param.put(ResponseKey.timestamp, System.currentTimeMillis()+"");
        String encrypt = RequestParamParse.onEncrypt(param);
        param.clear();
        param.put(ResponseKey.sign, encrypt);

    }


    @Override
    public void onSendHttp(BaseView view, String url, String method, Map<String, String> param, OnResponseCallBack<Map<String, Object>> callBack) throws Exception {
        onSendHttp(view, url, method, param, false, callBack);
    }

    @Override
    public void onSendHttp(BaseView view, String url, String method, Map<String, String> param, boolean isEncrypt, OnResponseCallBack<Map<String, Object>> callBack) throws Exception {
        onSendHttp(view, url, method, param, false, isEncrypt, callBack);
    }

    @Override
    public void onSendHttp(BaseView view, String url, String method, Map<String, String> param, boolean isIntercept, boolean isEncrypt, OnResponseCallBack<Map<String, Object>> callBack) throws Exception {

        if (OkhttpUtil.METHOD_POST.equals(method)){
            onSendPostHttp(view, url, param, isIntercept, isEncrypt, callBack);
        }else {
            onSendGetHttp(view, url, param, isIntercept, isEncrypt, callBack);
        }
    }

    @Override
    public void onSendGetHttp(final BaseView view, String url, Map<String, String> param, boolean isIntercept, boolean isEncrypt, final OnResponseCallBack<Map<String, Object>> callBack) throws Exception {

        if (isEncrypt)
            onPinParam(view, param);
        if (view != null)
            view.showLoadding();
        request.onSendGetRequest(url, param, isIntercept, new OnMvpCallBack<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> data) {
                    callBack.onResponse(data);
            }

            @Override
            public void onFailure(String msg) {
                if (view != null)
                    ToastUtils.onToast(msg);
            }

            @Override
            public void onOutTime(String msg) {
                if (view != null)
                    ToastUtils.onToast(msg);
            }

            @Override
            public void onComplete() {
                if (view != null)
                    view.dismissLoadding();
            }
        });
    }

    @Override
    public void onSendPostHttp(final BaseView view, String url, Map<String, String> param, boolean isIntercept, boolean isEncrypt, final OnResponseCallBack<Map<String, Object>> callBack) throws Exception {
        if (isEncrypt)
            onPinParam(view, param);
        if (view != null)
            view.showLoadding();
        request.onSendPostRequest(url, param, isIntercept,  new OnMvpCallBack<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> data) {
                callBack.onResponse(data);
            }

            @Override
            public void onFailure(String msg) {
                if (view != null)
                    ToastUtils.onToast(msg);
            }

            @Override
            public void onOutTime(String msg) {
                if (view != null)
                    ToastUtils.onToast(msg);
            }

            @Override
            public void onComplete() {
                if (view != null)
                    view.dismissLoadding();
            }
        });
    }
}
