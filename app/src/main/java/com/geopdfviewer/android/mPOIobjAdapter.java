package com.geopdfviewer.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 54286 on 2018/3/20.
 */

public class mPOIobjAdapter extends RecyclerView.Adapter<mPOIobjAdapter.ViewHolder>{
    private Context mContext;

    private List<mPOIobj> mPOIList;

    private mPOIobjAdapter.OnRecyclerItemLongListener mOnItemLong;

    static class ViewHolder extends RecyclerView.ViewHolder {
        //private OnRecyclerItemLongListener mOnItemLong = null;
        CardView cardView;
        ImageView POIImage;
        TextView POIName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            POIImage = (ImageView) view.findViewById(R.id.POI_image);
            POIName = (TextView) view.findViewById(R.id.POI_txt);



        }
    }
    public mPOIobjAdapter(List<mPOIobj> mPOIList) {
        this.mPOIList = mPOIList;
    }

    @Override
    public mPOIobjAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.pois_item, parent, false);
        final mPOIobjAdapter.ViewHolder holder = new mPOIobjAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                mPOIobj poi = mPOIList.get(position);
                Intent intent = new Intent(mContext, singlepoi.class);
                intent.putExtra("POIC", poi.getM_POIC());
                mContext.startActivity(intent);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLong != null){
                    int position = holder.getAdapterPosition();
                    mPOIobj poi = mPOIList.get(position);
                    mOnItemLong.onItemLongClick(v, poi.getM_POIC());
                    holder.cardView.setCardBackgroundColor(Color.GRAY);
                }
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(mPOIobjAdapter.ViewHolder holder, int position) {
        mPOIobj poi = mPOIList.get(position);
        if (poi.getM_tapenum() == 0 && poi.getM_photonum() == 0){
            holder.POIImage.setImageResource(R.drawable.ic_pin_red1);
        }
        if (poi.getM_tapenum() == 0 && poi.getM_photonum() != 0){
            holder.POIImage.setImageResource(R.drawable.ic_pin_green1);
        }
        if (poi.getM_tapenum() != 0 && poi.getM_photonum() == 0){
            holder.POIImage.setImageResource(R.drawable.ic_pin_blue1);
        }
        if (poi.getM_tapenum() != 0 && poi.getM_photonum() != 0){
            holder.POIImage.setImageResource(R.drawable.ic_pin_yellow1);
        }
        String data;
        data = "POI名称: " + poi.getM_name() + "\n" + "描述: " + poi.getM_description() + "\n" + "时间: " + poi.getM_time() + "\n" + "有" +
                poi.getM_tapenum() + "个录音, 有" + poi.getM_photonum() + "个图片";
        holder.POIName.setText(data);
    }


    @Override
    public int getItemCount() {
        return mPOIList.size();
    }



    public interface OnRecyclerItemLongListener{
        void onItemLongClick(View view,String POIC);
    }
    public void setOnItemLongClickListener(mPOIobjAdapter.OnRecyclerItemLongListener listener){
        //Log.w(TAG, "setOnItemLongClickListener: " );
        this.mOnItemLong =  listener;
    }
}
