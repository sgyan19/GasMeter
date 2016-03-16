package com.cqgas.gasmeter.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.adapter.UserMeterBaseAdapter;
import com.cqgas.gasmeter.center.BluetoothCenter;
import com.cqgas.gasmeter.center.ReadMeterCenter;
import com.cqgas.gasmeter.core.MeterCore;
import com.cqgas.gasmeter.task.ProgressDialogTask;
import com.cqgas.gasmeter.ui.DataInputDialog;
import com.cqgas.gasmeter.ui.DataInputDialogTitle;

import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class ReadMeterFragment extends BasePageFragment implements BluetoothCenter.ReadMeterCallback,DialogInterface.OnDismissListener,UserMeterBaseAdapter.OnItemClickListener{

    private View mRootView;
    private ListView mListView;
    private UserMeterBaseAdapter mAdaper;
    private MenuItem mFilterItem;
    private ProgressDialog dialog;

    private String addrQuery;

    private List<MeterCore> readMeterResult;

    private List<MeterCore> allMeter;
    private List<MeterCore> unReadMeter;

    private static WeakReference<ReadMeterFragment> instance;
    public static ReadMeterFragment getInstance(){
        if(instance != null){
            return instance.get();
        }
        return null;
    }

    public void resetData(){
        addrQuery = "";
        new GetCoreData("",GetCoreData.FLAG_GET_ALL).execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.read_meter_fragment,container,false);
        mRootView = view;
        getActivity().setTitle(R.string.user_list);
        instance = new WeakReference<>(this);
        addrQuery = "";
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
        addrQuery = "";
        new GetCoreData("",GetCoreData.FLAG_GET_ALL).execute();
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
                if(!BluetoothCenter.isConnect()){
                    Toast.makeText(getActivity(),getResources().getString(R.string.bluetooth_no_connect),Toast.LENGTH_SHORT).show();
                    break;
                }
                new ReadBluetoothTask().execute();
                break;
            case R.id.read_meter_filter_unread:
                boolean checked = mFilterItem.isChecked();
                int flag = checked ? GetCoreData.FLAG_GET_ALL : GetCoreData.FLAG_GET_UNREAD;
                new GetCoreData(addrQuery,flag).execute();
                break;
            case R.id.read_meter_filter_address:
                DataInputDialogTitle dialog = new DataInputDialogTitle(getActivity(),new DataInputDialogTitle.DialogOnClickListener() {
                    @Override
                    public void onClick(String v) {
                        addrQuery = v;
                        new GetCoreData(v,GetCoreData.FLAG_GET_ALL).execute();
                    }
                },"输入过滤地址");
                dialog.show();
                break;
        }
        return true;
    }

    @Override
    public void onReadOneResult(int readCount, int timeoutCount, int allCount, boolean success, List<MeterCore> obj) {
        dialog.setProgress(readCount + timeoutCount);
        dialog.setMessage(String.format("%d成功，%d超时", readCount, timeoutCount));
        if(readCount + timeoutCount == allCount){
            if(mAdaper == null) {
                createAdapter(obj);
            }else{
                mAdaper.notifyDataSetChanged();
            }
            readMeterResult = obj;
            dialog.dismiss();
//            dialog.setCancelable(true);
            Toast.makeText(getActivity(),String.format("抄表完成,%d个成功，%d个超时", readCount, timeoutCount),Toast.LENGTH_SHORT).show();
        }
    }

    private class GetCoreData extends ProgressDialogTask<List<List<MeterCore>>>{
        public static final int FLAG_GET_ALL = 0;
        public static final int FLAG_GET_UNREAD = 1;
        private int flag;
        private String queryAddr;
        public GetCoreData(String addr,int flag){
            super(getActivity());
            this.flag = flag;
            this.queryAddr = addr;
        }

        @Override
        protected void onPreExecute() throws Exception {
            super.onPreExecute();
            showIndeterminate();
        }
        @Override
        public List<List<MeterCore>> call() throws Exception {
            List<List<MeterCore>>  result= null;
            result = ReadMeterCenter.getQueryAddressResult(queryAddr);
            return result;
        }
        @Override
        protected void onException(Exception e) throws RuntimeException {
            super.onException(e);
            if(e instanceof FileNotFoundException)
                Toast.makeText(getActivity(), "Json数据文件不存在", Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onSuccess(List<List<MeterCore>> objects) throws Exception {
            super.onSuccess(objects);
            List<MeterCore> result = null;
            switch (flag){
                case FLAG_GET_ALL:
                    result = objects.get(0);
                    mFilterItem.setChecked(false);
                    break;
                case FLAG_GET_UNREAD:
                    result = objects.get(1);
                    mFilterItem.setChecked(true);
                    break;
            }
            if(mAdaper == null) {
                createAdapter(result);
            }else{
                mAdaper.clear();
                mAdaper.addAll(result);
            }
        }
    }

    private class ReadBluetoothTask extends ProgressDialogTask<List<MeterCore>> {
        public ReadBluetoothTask(){
            super(getActivity());
        }

        @Override
        protected void onPreExecute() throws Exception {
            super.onPreExecute();
            showIndeterminate();
        }

        @Override
        public List<MeterCore> call() throws Exception {
            List<MeterCore> result = null;
            if(mAdaper == null) {
                if(mFilterItem.isChecked()){
                    result = ReadMeterCenter.getUiAll();
                }else{
                    result = ReadMeterCenter.getUiUnRead();
                }
            }else{
                result = mAdaper.getObjects();
            }
            return result;
        }

        @Override
        protected void onSuccess(List<MeterCore> data) throws Exception {
            super.onSuccess(data);
            if(dialog != null){
                dialog.dismiss();
            }
            createProgressDialog();
            dialog.setMax(data.size());
            dialog.show();
            dialog.setProgress(0);
            BluetoothCenter.readMeterV2(data,ReadMeterFragment.this);
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
            e.printStackTrace();
        }
    }

    private class SaveReadMeterTask extends ProgressDialogTask<Void>{
        public SaveReadMeterTask(){
            super(getActivity());
        }

        @Override
        public Void call() throws Exception {
            ReadMeterCenter.readMeter(readMeterResult);
            return null;
        }

        @Override
        protected void onSuccess(Void aVoid) throws Exception {
            super.onSuccess(aVoid);
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            super.onException(e);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(readMeterResult != null && readMeterResult.size() > 0){
            new SaveReadMeterTask().execute();
        }
    }

    private void createProgressDialog(){
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("蓝牙抄表中");
        dialog.setMessage(String.format("%d成功，%d超时", 0, 0));
        dialog.setCancelable(false);
        dialog.setIndeterminate(false);
        dialog.setOnDismissListener(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }
    @Override
    public void onItemClick(int position, MeterCore item) {
        if(BluetoothCenter.isConnect()){
            List<MeterCore> data = new ArrayList<>();
            data.add(mAdaper.getItem(position));
            BluetoothCenter.readMeterV2(data,ReadMeterFragment.this);
        }else{
            Toast.makeText(getActivity(),getResources().getString(R.string.bluetooth_no_connect),Toast.LENGTH_SHORT).show();
        }
    }

    private void createAdapter(List<MeterCore> data){
        mAdaper = new UserMeterBaseAdapter(getActivity(), new ArrayList<MeterCore>());
        mAdaper.addAll(data);
        mAdaper.setOnItemClickListener(this);
        mListView.setAdapter(mAdaper);
    }
}
