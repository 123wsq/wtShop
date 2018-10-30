package com.sm.wtshop.app.mvp.model.inter;

import com.example.wsq.library.okhttp.OkhttpUtil;
import com.sm.wtshop.app.mvp.callback.OnResponseCallBack;
import com.sm.wtshop.app.mvp.view.BaseView;
import java.util.Map;

public interface RequestHttpModel {

    /**
     *
     * @param view
     * @param url
     * @param method
     * @param param
     * @param callBack
     * @throws Exception
     */
    void onSendHttp(BaseView view, String url, String method, Map<String, String> param, OnResponseCallBack<Map<String, Object>> callBack) throws Exception;

    /**
     *
     * @param view
     * @param url
     * @param method
     * @param param
     * @param isIntercept
     * @param callBack
     * @throws Exception
     */
    void onSendHttp(BaseView view, String url, String method, Map<String, String> param, boolean isIntercept, OnResponseCallBack<Map<String, Object>> callBack) throws Exception;

    /**
     *
     * @param view
     * @param url
     * @param method
     * @param param
     * @param isIntercept
     * @param isEncrypt
     * @param callBack
     * @throws Exception
     */
    void onSendHttp(BaseView view, String url, String method, Map<String, String> param, boolean isIntercept, boolean isEncrypt, OnResponseCallBack<Map<String, Object>> callBack) throws Exception;

    /**
     *
     * @param view
     * @param url
     * @param param
     * @param isIntercept
     * @param isEncrypt
     * @param callBack
     * @throws Exception
     */
    void onSendGetHttp(BaseView view, String url, Map<String, String> param, boolean isIntercept, boolean isEncrypt, OnResponseCallBack<Map<String, Object>> callBack) throws Exception;

    /**
     *
     * @param view
     * @param url
     * @param param
     * @param isIntercept
     * @param isEncrypt
     * @param callBack
     * @throws Exception
     */
    void onSendPostHttp(BaseView view, String url, Map<String, String> param, boolean isIntercept, boolean isEncrypt, OnResponseCallBack<Map<String, Object>> callBack) throws Exception;

}
