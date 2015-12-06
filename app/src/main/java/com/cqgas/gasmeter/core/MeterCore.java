package com.cqgas.gasmeter.core;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cqgas.gasmeter.utils.DateUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 国耀 on 2015/12/4.
 * 需展示4个字段，外加 总量 = cbjl_bcbd - cbjl_scbd
 */
public class MeterCore {
    public String rqb_gh = "";   // 表钢号 @NoNull
    public String mm ="";      // 密码 @NoNull
    public String cbjl_id = "";// 抄表记录ID @NoNull
    public String cbjl_yqdz_ms = "";    // 用气地址描述 @NoNull
    public String rqb_rqblx = "";       // 燃气表类型   @Nullable
    public String cbjl_hz_mc = "";      // 户主名称     @NoNull UI展示
    public int cbjl_scbd = 0;       // 上次表底     @Nullable UI展示
    public long cbjl_sccbrq = 0;     // 上次抄表日期 @Nullable UI展示 格式：yyyymmdd
    public String cbjl_pingjun_yql = "";// 平均用气量 @Nullable
    public String jzq_bh = "";          // 集中器编号    @NoNull
    public String cbjl_yqzh = "";       // 用户号      @NoNull //（只有明光的需要，其他厂家的数据是没有该属性的），接口开发中需要注意。)

    //public String rqb_gh;       // 表刚号      @NoNull
    //public String cbjl_id;      // 抄表记录     @NoNull
    public int cbjl_bcbd = 0;    // 本次表底     @NoNull     UI展示
    public String cbjl_sjcbrq = "";  // 抄表日期     @NoNull
    public String cbjl_cb_qk = "";   // 抄表情况     @NoNull

    public static int getThisMonthData(@NonNull MeterCore core){
        return core.cbjl_bcbd - core.cbjl_scbd;
    }

    public static MeterCore parse(@NonNull JSONObject obj){
        MeterCore mc = new MeterCore();
        mc.rqb_gh = obj.optString("rqb_gh");
        mc.mm = obj.optString("mm");
        mc.cbjl_id = obj.optString("cbjl_id");
        mc.cbjl_yqdz_ms = obj.optString("cbjl_yqdz_ms");
        mc.rqb_rqblx = obj.optString("rqb_rqblx");
        mc.cbjl_hz_mc = obj.optString("cbjl_hz_mc");
        mc.cbjl_scbd = obj.optInt("cbjl_scbd");
        String date = obj.optString("cbjl_sccbrq");
        mc.cbjl_sccbrq = DateUtils.getTimeInMillis(date);
        mc.cbjl_pingjun_yql = obj.optString("cbjl_pingjun_yql");
        mc.jzq_bh = obj.optString("jzq_bh");
        mc.cbjl_yqzh = obj.optString("cbjl_yqzh");
        mc.cbjl_bcbd = obj.optInt("cbjl_bcbd");
        mc.cbjl_sjcbrq = obj.optString("cbjl_sjcbrq");
        mc.cbjl_cb_qk = obj.optString("cbjl_cb_qk");
        return mc;
    }

    public static String getUiThisRead(@NonNull MeterCore core){
        String result = "";
        if(core.cbjl_bcbd != 0){
            result = core.cbjl_bcbd + "";
        }
        return result;
    }

    public static String getUiThisMonthData(@NonNull MeterCore core){
        String result = "";
        if(core.cbjl_bcbd != 0){
            result = (core.cbjl_bcbd - core.cbjl_scbd) + "";
        }
        return result;
    }


    public static boolean isRead(@NonNull MeterCore core){
        return core.cbjl_bcbd != 0;
    }
}
