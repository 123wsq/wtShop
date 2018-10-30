package com.sm.wtshop.app.fragment.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.wsq.library.bean.CityInfoBean;
import com.example.wsq.library.dao.CityDao;
import com.example.wsq.library.listener.OnSelectCityCallBack;
import com.example.wsq.library.utils.AppManager;
import com.example.wsq.library.view.city.CityWheelAdapter;
import com.orhanobut.logger.Logger;
import com.sm.wtshop.app.R;
import com.sm.wtshop.app.adapter.CitySelectAdapter;
import com.sm.wtshop.app.base.BaseFragment;
import com.sm.wtshop.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CitySelectFragment extends BaseFragment {

    public static final String TAG = CitySelectFragment.class.getName();

    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.rv_recycler_province) RecyclerView rv_recycler_province;
    @BindView(R.id.rv_recycler_city) RecyclerView rv_recycler_city;
    @BindView(R.id.rv_recycler_county) RecyclerView rv_recycler_county;

    private CitySelectAdapter mProvinceAdapter, mCityAdapter, mCountyAdapter;
    private CityDao cityDao;

    private List<CityInfoBean> mAllData;  //所有的省市县的数据
    private CityInfoBean mCurProvice, mCurCity, mCurCounty; //当前选中的省市县
    private List<CityInfoBean> mProvinceData, mCityData, mCountyData; //显示的数据


    public static CitySelectFragment getInstance(){

        return new CitySelectFragment();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_base_cityselect;
    }

    @Override
    protected void initView() {
        tv_title.setText("选择城市");

        onInitRecyclerView_L(rv_recycler_province,0f);
        onInitRecyclerView_L(rv_recycler_city, 0f);
        onInitRecyclerView_L(rv_recycler_county, 0f);

        onInitData();
    }

    @OnClick({R.id.ll_back})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_back:
                mFunctionsManage.invokeFunction(INTERFACE_BACK,"");
                break;
        }
    }

    private void onInitData(){
        mAllData = new ArrayList<>();
        mProvinceData = new ArrayList<>();
        mCityData = new ArrayList<>();
        mCountyData = new ArrayList<>();

        mProvinceAdapter = new CitySelectAdapter(getActivity(), mProvinceData, cityCallBack, 1);
        rv_recycler_province.setAdapter(mProvinceAdapter);

        mCityAdapter = new CitySelectAdapter(getActivity(), mCityData, cityCallBack, 2);
        rv_recycler_city.setAdapter(mCityAdapter);

        mCountyAdapter = new CitySelectAdapter(getActivity(), mCountyData, cityCallBack, 3);
        rv_recycler_county.setAdapter(mCountyAdapter);

        cityDao = new CityDao(getActivity(), AppManager.getAppPackageName());
        mAllData.addAll(cityDao.getData(-1));
        //
        onGetProvinceData();
    }

    OnSelectCityCallBack cityCallBack = new OnSelectCityCallBack() {
        @Override
        public void onSelect(int type, CityInfoBean bean) {
            switch (type){
                case 1:
                    mCurProvice = bean;
                    onGetCityData(mCurProvice.getId());
                    break;
                case 2:
                    mCurCity = bean;
                    onGetCountyData(mCurCity.getId());
                    break;
                case 3:
                    mCurCounty = bean;
                    break;
            }
        }
    };

    /**
     * 获取省份的数据
     */
    private void onGetProvinceData(){

        for (int i = 0; i < mAllData.size(); i++) {
            CityInfoBean bean = mAllData.get(i);
            int parent_id = bean.getParent_id();
            if (parent_id ==-1){
                mProvinceData.add(bean);
            }
        }
        mProvinceAdapter.notifyDataSetChanged();
        onGetCityData(mProvinceData.get(0).getId());
    }

    /**
     * 获取城市
     */
    private synchronized void onGetCityData(int parent_id){

        mCityData.clear();
        for (int i = 0; i < mAllData.size(); i++) {
            CityInfoBean bean = mAllData.get(i);
            int id = bean.getParent_id();
            if (id == parent_id){
                mCityData.add(bean);
            }
        }
        mCityAdapter.notifyDataSetChanged();
        onGetCountyData(mCityData.get(0).getId());
    }

    /**
     * 获取县区
     * @param parent_id
     */
    private synchronized void onGetCountyData(int parent_id){

        mCountyData.clear();
        for (int i = 0; i < mAllData.size(); i++) {
            CityInfoBean bean = mAllData.get(i);
            int id = bean.getParent_id();
            if (id == parent_id){
                mCountyData.add(bean);
            }
        }
        mCountyAdapter.notifyDataSetChanged();

    }





}
