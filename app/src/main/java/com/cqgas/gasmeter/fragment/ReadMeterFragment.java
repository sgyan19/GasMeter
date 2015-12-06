package com.cqgas.gasmeter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.adapter.UserMeterBaseAdapter;
import com.cqgas.gasmeter.center.ReadMeterCenter;
import com.cqgas.gasmeter.task.ProgressDialogTask;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class ReadMeterFragment extends BasePageFragment {

    private View mRootView;
    private ListView mListView;
    private UserMeterBaseAdapter mAdaper;
    private MenuItem mFilterItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.read_meter_fragment,container,false);
        mRootView = view;
        getActivity().setTitle(R.string.user_list);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        ActionBar bar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView(View rootView){
        mListView = (ListView)rootView.findViewById(R.id.user_list);
        mAdaper = new UserMeterBaseAdapter(getActivity(), ReadMeterCenter.getUiAll());
        mListView.setAdapter(mAdaper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.read_meter, menu);
        mFilterItem = menu.findItem(R.id.read_meter_filter_unread);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.read_meter_do_read:
                new ReadBluetoothTask(getActivity()).execute();
                break;
            case R.id.read_meter_filter_unread:
                if(mFilterItem.isChecked()){
                    mAdaper.reset(ReadMeterCenter.getUiAll());
                }else{
                    mAdaper.reset(ReadMeterCenter.getUiUnRead());
                }
                mFilterItem.setChecked(!mFilterItem.isChecked());
                break;
        }
        return true;
    }

    private class ReadBluetoothTask extends ProgressDialogTask<Boolean> {
        public ReadBluetoothTask(Context context){
            super(context);
        }

        @Override
        protected void onPreExecute() throws Exception {
            super.onPreExecute();
            showIndeterminate();
        }

        @Override
        public Boolean call() throws Exception {
            return ReadMeterCenter.doBluetoothData();
        }

        @Override
        protected void onSuccess(Boolean queryCore) throws Exception {
            super.onSuccess(queryCore);
            if(queryCore) {
                mAdaper.reset(ReadMeterCenter.getUiAll());
                Toast.makeText(context, "抄表完成", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"抄表失败，请检查蓝牙设备是否连接成功",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            super.onException(e);
            e.printStackTrace();
            Toast.makeText(context,"抄表异常，请检查蓝牙设备是否连接成功",Toast.LENGTH_SHORT).show();
        }
    }
}
