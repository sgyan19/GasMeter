package com.cqgas.gasmeter.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;

import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.core.UserMeter;

import java.util.List;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class UserMeterBaseAdapter extends BaseArrayAdapter<UserMeter, UserMeterBaseAdapter.ItemHolder> {
    private Context mContext;

    public UserMeterBaseAdapter(Context context, List<UserMeter> data){
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
        holder.mAddressText = (TextView) v.findViewById(R.id.user_meter_item_address);
        holder.mNumberText = (TextView) v.findViewById(R.id.user_meter_item_number);
        holder.mlastMonthDataText = (TextView) v.findViewById(R.id.user_meter_item_last_data);
        holder.mDataText = (TextView) v.findViewById(R.id.user_meter_item_data);
        holder.mActive = (Button) v.findViewById(R.id.user_meter_item_enter);
        holder.mLineText = (TextView) v.findViewById(R.id.user_meter_item_index);
    }

    @Override
    protected void initParamsHolder(int position, ItemHolder holder, UserMeter item) {

    }

    @Override
    protected void bundleValue(int position, ItemHolder holder, UserMeter item) {
        Log.d("sun", item.toString());
        holder.mUserText.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_name), item.getName()));
        holder.mAddressText.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_address), item.getAddress()));
        holder.mNumberText.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_number),item.getNumber()));
        holder.mlastMonthDataText.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_last_data),item.getLastMonthQuantity()));
        holder.mDataText.setText(String.format(mContext.getResources().getString(R.string.user_meter_item_data),item.getQuantity()));
        holder.mLineText.setText(String.valueOf(position + 1));
    }

    public static class ItemHolder{
        TextView mUserText;
        TextView mNumberText;
        TextView mAddressText;
        TextView mlastMonthDataText;
        TextView mDataText;
        TextView mLineText;
        Button mActive;
    }
}
