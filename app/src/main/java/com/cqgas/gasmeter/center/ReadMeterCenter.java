package com.cqgas.gasmeter.center;

import com.cqgas.gasmeter.core.MeterCore;
import com.cqgas.gasmeter.utils.DateUtils;
import com.cqgas.gasmeter.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class ReadMeterCenter {

    /**
     * 获取用于UI显示的列表，可能会被UI改动，所以要new一个新容器
     * @return
     */
    public static List<MeterCore> getUiAll(){
        // 测试数据
        List<MeterCore> result = new ArrayList<>();
        MeterCore meter = new MeterCore();
        meter.cbjl_hz_mc = "袁心伦";
        meter.cbjl_scbd = 42;
        meter.cbjl_sccbrq = DateUtils.getTimeInMillis("20150313");
        meter.cbjl_bcbd = 93;
        meter.cbjl_pingjun_yql = "60";
        result.add(meter);

        meter = new MeterCore();
        meter.cbjl_hz_mc = "唐登华";
        meter.cbjl_scbd = 42;
        meter.cbjl_sccbrq = DateUtils.getTimeInMillis("20150223");
        meter.cbjl_bcbd = 93;
        meter.cbjl_pingjun_yql = "60";
        result.add(meter);

        meter = new MeterCore();
        meter.cbjl_hz_mc = "杨世坤";
        meter.cbjl_scbd = 42;
        meter.cbjl_sccbrq = DateUtils.getTimeInMillis("20150703");
        meter.cbjl_bcbd = 0;
        meter.cbjl_pingjun_yql = "60";
        result.add(meter);

        meter = new MeterCore();
        meter.cbjl_hz_mc = "方仲永";
        meter.cbjl_scbd = 42;
        meter.cbjl_sccbrq = DateUtils.getTimeInMillis("20150527");
        meter.cbjl_bcbd = 93;
        meter.cbjl_pingjun_yql = "60";
        result.add(meter);
        return result;
    }

    public static List<MeterCore> getUiUnRead() {
        // 测试数据
        List<MeterCore> result = new ArrayList<>();
        MeterCore meter = new MeterCore();
        meter.cbjl_hz_mc = "杨世坤";
        meter.cbjl_scbd = 42;
        meter.cbjl_sccbrq = DateUtils.getTimeInMillis("20150703");
        meter.cbjl_bcbd = 0;
        meter.cbjl_pingjun_yql = "60";
        result.add(meter);
        return result;
    }

    /**
     * 根据PC获取的对象，按照ID，遍历获取调用蓝牙接口，获取新值。最把结果按照json数组输出新的文件中
     * @return 成功：true，失败：false
     */
    public static boolean doBluetoothData(){
        List<MeterCore> meterCores = new ArrayList<>();
        for(MeterCore item : meterCores){
            String i = item.cbjl_id; // 根据id值调用蓝牙接口获取抄表数据
        }

        StorageUtils.getTargetToPcFilePath(); // 写入文件地址
        return true;
    }

    /**
     * 从PC获取的文件中获取，json串解析出对象表
     * @return
     */
    private static List<MeterCore> getFromPcFile(){
        File file = new File(StorageUtils.getTargetFormPcFilePath());
        if(!file.exists()){
            return null;
        }
        //TODO:

        return null;
    }
}
