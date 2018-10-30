package com.sm.wtshop.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wsq.library.bean.CityInfoBean;
import com.example.wsq.library.listener.OnSelectCityCallBack;
import com.orhanobut.logger.Logger;
import com.sm.wtshop.app.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CitySelectAdapter extends RecyclerView.Adapter<CitySelectAdapter.ViewHolder> {

    private Context mContext;
    private List<CityInfoBean> mData;
    private OnSelectCityCallBack mCallBack;
    private int mType;
    private int selectPosition = 0;

    public CitySelectAdapter(Context context, List<CityInfoBean> list, OnSelectCityCallBack callBack, int type){
        this.mContext = context;
        this.mData = list;
        this.mCallBack = callBack;
        this.mType = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cityselect_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_city_name.setText(mData.get(position).getName());

        if (position ==selectPosition){
            holder.tv_city_name.setBackgroundColor(mContext.getResources().getColor(R.color.color_left_4));
            holder.tv_city_name.setTextColor(mContext.getResources().getColor(R.color.color_white));
        }else{
            holder.tv_city_name.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
            holder.tv_city_name.setTextColor(mContext.getResources().getColor(R.color.default_color_gray_1));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city_name)
        TextView tv_city_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);



        }
        @OnClick({R.id.tv_city_name})
        public void onClick(View v){
            switch (v.getId()){
                case R.id.tv_city_name:
                    int position = getAdapterPosition();
                    onSetSelectPosition(position);
                    mCallBack.onSelect(mType, mData.get(position));
                    break;
            }
        }
    }

    private void onSetSelectPosition(int position){
        this.selectPosition = position;
        notifyDataSetChanged();
    }
}
