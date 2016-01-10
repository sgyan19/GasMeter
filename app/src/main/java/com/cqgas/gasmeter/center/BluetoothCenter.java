package com.cqgas.gasmeter.center;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cqgas.gasmeter.MyApplication;
import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.core.MeterCore;
import com.rthc.wdj.bluetoothtoollib.MeterHandler;
import com.rthc.wdj.bluetoothtoollib.SwitchBox;
import com.rthc.wdj.bluetoothtoollib.ValveHandler;
import com.rthc.wdj.bluetoothtoollib.cmd.BleCmd;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 国耀 on 2015/12/20.
 */
public class BluetoothCenter {

    private static boolean isConnect = false;
    private static String mDevicesName = "";
    private static SwitchBox mCore = null;
    private static String[] DevicesTable;



    public static boolean isConnect(){
        if(mCore == null){
            isConnect = false;
        }
        return isConnect;
    }

    public static String getUiDevicesName(){
        return mDevicesName;
    }

    public static void connect() throws Exception{
        isConnect = false;
        if(mCore == null){
            mCore = new SwitchBox(MyApplication.getContext());
        }
        if(TextUtils.isEmpty(mDevicesName)){
            throw new Exception(MyApplication.getContext().getResources().getString(R.string.bluetooth_exception_no_name));
        }
        if(!mCore.scanDevice(mDevicesName,15000)){
            throw new Exception(MyApplication.getContext().getResources().getString(R.string.bluetooth_exception_failed));
        }
        isConnect = true;
    }

    public static void connect(String name) throws Exception{
        isConnect = false;
        if(mCore == null){
            mCore = new SwitchBox(MyApplication.getContext());
        }else{
            mCore.close();
        }
        if(TextUtils.isEmpty(name)){
            throw new Exception(MyApplication.getContext().getResources().getString(R.string.bluetooth_exception_no_name));
        }
        mDevicesName = name;
        if(!mCore.scanDevice(mDevicesName,15000)){
            throw new Exception(MyApplication.getContext().getResources().getString(R.string.bluetooth_exception_failed));
        }
        isConnect = true;
    }

    public static void disConnect(){
        if(mCore != null){
            mCore.close();
        }
        isConnect = false;
    }

    public interface ReadMeterCallback{
        void onReadOneResult(int readCount,int timeoutCount,int allCount,boolean success, List<MeterCore> objs);
    }

    private static int ReadCount;
    private static int TimeOutCount;
    private static int AllCount;
    private static boolean Reading = false;

    // ----并发模式--------------
    private static class BluetoothCallback implements MeterHandler {
        private MeterCore reader;
        private ReadMeterCallback callback;
        private Handler uiHandler;
        private List<MeterCore> all;
        public BluetoothCallback(MeterCore item,List<MeterCore> all,Handler uiHandler,ReadMeterCallback callback){
            reader = item;
            this.callback = callback;
            this.uiHandler = uiHandler;
            this.all = all;
        }

        @Override
        public int callback(float v) {
            reader.cbjl_bcbd = (int)v;
            ReadCount++;
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onReadOneResult(ReadCount,TimeOutCount,AllCount,true,all);
                }
            });
            if((ReadCount + TimeOutCount) == AllCount){
                Reading = false;
            }
            return 0;
        }

        @Override
        public void timeOut() {
            TimeOutCount++;
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onReadOneResult(ReadCount,TimeOutCount,AllCount,false,all);
                }
            });
            if((ReadCount + TimeOutCount) == AllCount){
                Reading = false;
            }
        }
    }

    /**
     * 蓝牙抄表
     */
    public static void readMeter(@NonNull List<MeterCore> data,@NonNull ReadMeterCallback callback) {
        if(Reading) return;
        ReadCount = 0;
        TimeOutCount = 0;
        AllCount = data.size();
        Handler handler = new Handler(Looper.getMainLooper());
        for(MeterCore item : data){
            mCore.readMeter(item.rqb_gh, getDevicesIndex(item.rqb_gh),
                    new BluetoothCallback(item,data,handler,callback));
        }
    }
    // ----串行式
    private static class BluetoothCallbackV2 implements MeterHandler {
        private Iterator<MeterCore> iterator;
        private ReadMeterCallback callback;
        private Handler uiHandler;
        private List<MeterCore> all;
        private MeterCore thisOne;
        public BluetoothCallbackV2(Iterator<MeterCore> iterator,MeterCore meterCore,List<MeterCore> all,Handler uiHandler,ReadMeterCallback callback){
            this.iterator = iterator;
            this.callback = callback;
            this.uiHandler = uiHandler;
            this.all = all;
            thisOne = meterCore;
        }

        @Override
        public int callback(float v) {
            thisOne.cbjl_bcbd = (int)v;
            thisOne.cbjl_cb_qk= MeterCore.NORMAL;
            ReadCount++;
            uiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onReadOneResult(ReadCount, TimeOutCount, AllCount, true, all);
                    if (iterator.hasNext()) {
                        thisOne = iterator.next();
                        mCore.readMeter(thisOne.rqb_gh, getDevicesIndex(thisOne.rqb_gh), BluetoothCallbackV2.this);
                    } else {
                        Reading = false;
                    }
                }
            }, 1000);
            return 0;
        }

        @Override
        public void timeOut() {
            TimeOutCount++;
            uiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onReadOneResult(ReadCount,TimeOutCount,AllCount,false,all);
                    if(iterator.hasNext()){
                        thisOne = iterator.next();
                        mCore.readMeter(thisOne.rqb_gh,getDevicesIndex(thisOne.rqb_gh),BluetoothCallbackV2.this);
                    }else{
                        Reading = false;
                    }
                }
            },1000);
        }
    }

    /**
     * 蓝牙抄表
     */
    public static void readMeterV2(@NonNull List<MeterCore> data,@NonNull ReadMeterCallback callback) {
        if(Reading) return;
        ReadCount = 0;
        TimeOutCount = 0;
        AllCount = data.size();
        Handler handler = new Handler(Looper.getMainLooper());
        Iterator<MeterCore> iterator = data.iterator();
        if(iterator.hasNext()) {
            MeterCore meter = iterator.next();
            mCore.readMeter(meter.rqb_gh, getDevicesIndex(meter.rqb_gh),
                    new BluetoothCallbackV2(iterator,meter,data, handler, callback));
        }
    }

    private static boolean checkMeterId(String meterId) {
        if (meterId.length() != 14 && meterId.length() != 8) {
            return false;
        }
        return true;
    }

    private static int getDevicesIndex(String meterId){
        int result = -1;
        if(DevicesTable == null){
            DevicesTable = MyApplication.getContext().getResources().getStringArray(R.array.moduleArray);
        }
        int id = meterId.length();
        switch(id){
            case 14:
                result = BleCmd.CTR_MODULE_ID_SKYSHOOT;
                break;
            case 8:
                result = BleCmd.CTR_MODULE_ID_JIEXUN;
                break;
            case 16:
                result = BleCmd.CTR_MODULE_ID_LIERDA;
                break;
        }
        return result;
    }
}
