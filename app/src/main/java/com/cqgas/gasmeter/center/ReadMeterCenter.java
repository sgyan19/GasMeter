package com.cqgas.gasmeter.center;

import com.cqgas.gasmeter.core.MeterCore;
import com.cqgas.gasmeter.core.UserMeter;
import com.cqgas.gasmeter.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class ReadMeterCenter {

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
}
