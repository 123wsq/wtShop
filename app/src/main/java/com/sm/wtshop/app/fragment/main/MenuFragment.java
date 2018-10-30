package com.sm.wtshop.app.fragment.main;

import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.wsq.library.utils.SharedTools;
import com.orhanobut.logger.Logger;
import com.sm.wtshop.app.R;
import com.example.wsq.library.tools.ViewSize;
import com.sm.wtshop.app.base.BaseFragment;
import com.sm.wtshop.app.constant.ResponseKey;
import com.sm.wtshop.app.mvp.presenter.BasePresenter;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class MenuFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    public static final String TAG = MenuFragment.class.getName();

    public static final String INTERFACE_WITHP = TAG + _INTERFACE_WITHP;

    @BindView(R.id.rg_menu) RadioGroup rg_menu;
    @BindViews({R.id.rb_home, R.id.rb_record, R.id.rb_message, R.id.rb_my}) RadioButton[] rb_Radio;

    private int[] drawableId ={R.drawable.selector_menu_my,R.drawable.selector_menu_my, R.drawable.selector_menu_my, R.drawable.selector_menu_my};




    public static MenuFragment getInstance(){
        return new MenuFragment();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_menu;
    }

    @Override
    protected void initView() {
        for (int i = 0; i < drawableId.length; i++) {
            ViewSize.onSetRadioButtonSize(getContext(), rb_Radio[i], drawableId[i]);
        }

        rg_menu.setOnCheckedChangeListener(this);

        int menu_poi = getArguments().getInt(ResponseKey.fragment_menu_poi, 1);
        rb_Radio[menu_poi - 1].setChecked(true);


    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        try{
        switch (checkedId){
            case R.id.rb_home:
                mFunctionsManage.invokeFunction(INTERFACE_WITHP, 1);
                break;
            case R.id.rb_record:
                mFunctionsManage.invokeFunction(INTERFACE_WITHP, 2);
                break;
            case R.id.rb_message:
                mFunctionsManage.invokeFunction(INTERFACE_WITHP, 3);
                break;
            case  R.id.rb_my:
                mFunctionsManage.invokeFunction(INTERFACE_WITHP, 4);
                break;
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
