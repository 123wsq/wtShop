package com.sm.wtshop.app.bean;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.Serializable;

public class PageParamBean implements Serializable {


    /**
     * 目标fragment
     */
    private Fragment fragmentTarget;
    /**
     * 目标fragmentTag
     */
    private String fragmentTag;

    /**
     * 传参
     */
    private Bundle bundle;

    /**
     * 是否可以返回  默认是true 表示又返回的功能
     */
    private boolean isBack = true;

    /**
     * 登录拦截  默认表示是需要拦截的
     */
    private boolean isLoginIntercept = true;


    public Fragment getFragmentTarget() {
        return fragmentTarget;
    }

    public void setFragmentTarget(Fragment fragmentTarget) {
        this.fragmentTarget = fragmentTarget;
    }

    public String getFragmentTag() {
        return fragmentTag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }

    public boolean isLoginIntercept() {
        return isLoginIntercept;
    }

    public void setLoginIntercept(boolean loginIntercept) {
        isLoginIntercept = loginIntercept;
    }

    public PageParamBean() {
    }

    @Override
    public String toString() {
        return "PageParamBean:[" +
                "fragmentTarget:"+fragmentTarget+", " +
                "fragmentTag:"+fragmentTag+", " +
                "bundle:"+bundle.toString()+", " +
                "isBack:"+isBack+", " +
                "isLoginIntercept:"+isLoginIntercept+", " +
                "]";
    }
}
