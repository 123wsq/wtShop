package com.example.wsq.library.listener;

import com.example.wsq.library.bean.CityInfoBean;

public interface OnSelectCityCallBack {

    /**
     *
     * @param type  返回类型  1 省  2市 3 区县
     * @param bean  数值
     */
    void onSelect(int type, CityInfoBean bean);
}
