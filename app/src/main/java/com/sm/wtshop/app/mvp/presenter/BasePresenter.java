package com.sm.wtshop.app.mvp.presenter;



import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class BasePresenter<T> {

    protected WeakReference<T> weakReference;


    //绑定
    public void attachView(T view){

        weakReference =new WeakReference<T>(view);
    }
    //解绑

    public void deachView(){
        weakReference.clear();

    }
    protected T getView(){
        return weakReference.get();
    }





}
