package com.ydzncd.androidtest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ydzncd.androidtest.GWPermissionAty;
import com.ydzncd.androidtest.MainActivity;
import com.ydzncd.androidtest.QWGradleAty;
import com.ydzncd.androidtest.QWRetrofitAty;
import com.ydzncd.androidtest.QWRxjavaAty;
import com.ydzncd.androidtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QWMainAdapter extends RecyclerView.Adapter<QWMainAdapter.NormalTextViewHolder> {
    private LayoutInflater mLayoutInflater;
    private static Context mContext;
    private String[] mTitles;

    public QWMainAdapter(Context context){
        mTitles = context.getResources().getStringArray(R.array.mainTitles);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.mTitleTv.setText(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.length;
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_mainTittle) TextView mTitleTv;

        NormalTextViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick
        void onClick(View view) {
            int position = getAdapterPosition();
            if (position == 0){
                mContext.startActivity(new Intent(mContext, GWPermissionAty.class));
            }
            else if (position == 1){

            }
            else if (position == 2){
                mContext.startActivity(new Intent(mContext, QWGradleAty.class));
            }
            else if (position == 5){
                mContext.startActivity(new Intent(mContext, QWRxjavaAty.class));
            }
            else{
                mContext.startActivity(new Intent(mContext, QWRetrofitAty.class));
            }
        }
    }

}
