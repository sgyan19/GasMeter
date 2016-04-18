package com.cqgas.gasmeter.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.center.ReadMeterCenter;
import com.cqgas.gasmeter.core.MeterCore;
import com.cqgas.gasmeter.ui.DataInputDialog;
import com.cqgas.gasmeter.utils.DateUtils;

import java.util.List;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class UserMeterBaseAdapter extends BaseArrayAdapter<MeterCore, UserMeterBaseAdapter.ItemHolder> {
    private Context mContext;

    public UserMeterBaseAdapter(Context context, List<MeterCore> data){
        super(context, R.layout.user_meter_item,data);
        mContext = context;
    }

    @Override
    protected ItemHolder createHolder() {
        return new ItemHolder();
    }

    @Override
    protected void initHolder(int position, View v, ItemHolder holder) {
        holder.mUserText = (TextView) v.findViewById(R.id.user_meter_item_name);
        holder.mAverageData = (TextView) v.findViewById(R.id.user_meter_item_average_data);
        holder.mLastRead = (TextView) v.findViewById(R.id.user_meter_item_last_read);
        holder.mLastDate = (TextView) v.findViewById(R.id.user_meter_item_last_read_date);
        holder.mThisRead = (TextView) v.findViewById(R.id.user_meter_item_this_read);
        holder.mThisData = (TextView) v.findViewById(R.id.user_meter_item_this_data);
        holder.mActive = (Button) v.findViewById(R.id.user_meter_item_enter);
        holder.mLineText = (TextView) v.findViewById(R.id.user_meter_item_index);
        holder.mAddrText = (TextView)v.findViewById(R.id.user_meter_item_addr);
        holder.mContainer = (ViewGroup) v.findViewById(R.id.user_meter_item_container);
    }

    @Override
    protected void initParamsHolder(int position, ItemHolder holder, MeterCore item) {
    }

    @Override
    protected void bundleValue(int position, ItemHolder holder, MeterCore item) {
        Log.d("sun", item.toString());
        holder.mUserText.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_name), item.cbjl_hz_mc));
        holder.mAverageData.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_average_data), item.cbjl_pingjun_yql));
        holder.mLastRead.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_last_read), item.cbjl_scbd));
        holder.mLastDate.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_last_read_date), item.rqb_gh));
        holder.mAddrText.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_address),item.cbjl_yqdz_ms));
        holder.mLineText.setText(String.valueOf(position + 1));
        updateIsRead(holder,item);
        holder.data = item;
        final ItemHolder h = holder;
        final MeterCore core = item;
        holder.mActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataInputDialog dialog = new DataInputDialog(mContext, new DataInputDialog.DialogOnClickListener() {
                    @Override
                    public boolean onClick(String v,boolean isTwice,TextView messageShower) {
                        int number = Integer.valueOf(v);
                        if(number < core.cbjl_scbd && !isTwice) {
                            //Toast.makeText(getContext(),"本次表底小于上次表底，请确认是否正确？",Toast.LENGTH_SHORT).show();
                            messageShower.setText("本次表底小于上次表底，请确认是否正确？");
                            return false;
                        }else {
                            core.cbjl_bcbd = number;
                            core.cbjl_cb_qk = MeterCore.NORMAL;
                            ReadMeterCenter.readMeter(core.cbjl_id, number);
                            updateIsRead(h, core);
                        }
                        return true;
                    }
                },MeterCore.getUiThisRead(core));
                dialog.show();
            }
        });
        final int p = position;
        final MeterCore c = item;
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(p,c);
                }
            }
        });
    }

    public void updateIsRead(ItemHolder holder, MeterCore item){
        holder.mThisRead.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_this_read), MeterCore.getUiThisRead(item)));
        holder.mThisData.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_this_data), MeterCore.getUiThisMonthData(item)));
        if(MeterCore.isRead(item)) {
            Drawable able = mContext.getResources().getDrawable(R.drawable.user_meter_item_isread_true);
            holder.mActive.setBackgroundDrawable(able);
            holder.mActive.setText("重抄");
        }else {
            Drawable able = mContext.getResources().getDrawable(R.drawable.user_meter_item_isread_false);
            holder.mActive.setBackgroundDrawable(able);
            holder.mActive.setText("未抄");
        }
    }

    public static class ItemHolder{
        TextView mUserText;     // 户主姓名
        TextView mAverageData;// 平均读数
        TextView mLastRead; // 上次抄表度数
        TextView mLastDate; // 上次抄表日期
        TextView mThisRead; // 本次度数
        TextView mThisData; // 本次用量
        TextView mLineText; // 行序号
        TextView mAddrText;
        ViewGroup mContainer;
        Button mActive;
        MeterCore data;
    }

    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener l){
        listener = l;
    }
    public interface OnItemClickListener{
        void onItemClick(int position,MeterCore item);
    }
}
