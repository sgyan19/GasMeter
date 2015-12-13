package com.cqgas.gasmeter.core;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cqgas.gasmeter.sqlite.DBHelper;
import com.cqgas.gasmeter.utils.DateUtils;
import com.cqgas.gasmeter.utils.GsonUtils;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 国耀 on 2015/12/4.
 * 需展示4个字段，外加 总量 = cbjl_bcbd - cbjl_scbd
 */
public class MeterCore {

    //抄表情况
    public static final int NORMAL = 0; // 已抄
    public static final int UNUSE = 4; // 未用
    public static final int UNREAD = 9; // 未抄
    public static final int EXCEPT = 10; // 异常

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
    public int cbjl_cb_qk = UNREAD;   // 抄表情况     @NoNull

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
        mc.cbjl_cb_qk = obj.optInt("cbjl_cb_qk", UNREAD);
        return mc;
    }

    public static MeterCore parse(@NonNull Cursor cursor){
        MeterCore mc = new MeterCore();
        mc.rqb_gh = cursor.getString(cursor.getColumnIndex("rqb_gh"));
        mc.mm = cursor.getString(cursor.getColumnIndex("mm"));
        mc.cbjl_id = cursor.getString(cursor.getColumnIndex("cbjl_id"));
        mc.cbjl_yqdz_ms = cursor.getString(cursor.getColumnIndex("cbjl_yqdz_ms"));
        mc.rqb_rqblx = cursor.getString(cursor.getColumnIndex("rqb_rqblx"));
        mc.cbjl_hz_mc = cursor.getString(cursor.getColumnIndex("cbjl_hz_mc"));
        mc.cbjl_scbd = cursor.getInt(cursor.getColumnIndex("cbjl_scbd"));
        String date = cursor.getString(cursor.getColumnIndex("cbjl_sccbrq"));
        mc.cbjl_sccbrq = DateUtils.getTimeInMillis(date);
        mc.cbjl_pingjun_yql = cursor.getString(cursor.getColumnIndex("cbjl_pingjun_yql"));
        mc.jzq_bh = cursor.getString(cursor.getColumnIndex("jzq_bh"));
        mc.cbjl_yqzh = cursor.getString(cursor.getColumnIndex("cbjl_yqzh"));
        mc.cbjl_bcbd = cursor.getInt(cursor.getColumnIndex("cbjl_bcbd"));
        mc.cbjl_sjcbrq = cursor.getString(cursor.getColumnIndex("cbjl_sjcbrq"));
        mc.cbjl_cb_qk = cursor.getInt(cursor.getColumnIndex("cbjl_cb_qk"));
        return mc;
    }

    public JsonObject buildJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("rqb_gh", rqb_gh);
        jsonObject.addProperty("cbjl_id", cbjl_id);
        jsonObject.addProperty("cbjl_bcbd", cbjl_bcbd);
        jsonObject.addProperty("cbjl_sjcbrq", cbjl_sjcbrq);
        jsonObject.addProperty("cbjl_cb_qk", cbjl_cb_qk);
        return jsonObject;
    }

    public static void insertToDB(@NonNull JsonObject obj, @NonNull SQLiteDatabase sqLiteDatabase) {
        String rqb_gh = GsonUtils.optString(obj, "rqb_gh");
        String mm = GsonUtils.optString(obj, "mm");
        String cbjl_id = GsonUtils.optString(obj, "cbjl_id");
        String cbjl_yqdz_ms = GsonUtils.optString(obj, "cbjl_yqdz_ms");
        String rqb_rqblx = GsonUtils.optString(obj, "rqb_rqblx");
        String cbjl_hz_mc = GsonUtils.optString(obj, "cbjl_hz_mc");
        int cbjl_scbd = GsonUtils.optInt(obj, "cbjl_scbd");
        String date = GsonUtils.optString(obj, "cbjl_sccbrq");
        long cbjl_sccbrq = DateUtils.getTimeInMillis(date);
        String cbjl_pingjun_yql = GsonUtils.optString(obj, "cbjl_pingjun_yql");
        String jzq_bh = GsonUtils.optString(obj, "jzq_bh");
        String cbjl_yqzh = GsonUtils.optString(obj, "cbjl_yqzh");
        int cbjl_bcbd = GsonUtils.optInt(obj, "cbjl_bcbd");
        String cbjl_sjcbrq = GsonUtils.optString(obj, "cbjl_sjcbrq");
        int cbjl_cb_qk = GsonUtils.optInt(obj, "cbjl_cb_qk", UNREAD);

        ContentValues cv = new ContentValues();
        cv.put("rqb_gh", rqb_gh);
        cv.put("mm", mm);
        cv.put("cbjl_id", cbjl_id);
        cv.put("cbjl_yqdz_ms", cbjl_yqdz_ms);
        cv.put("rqb_rqblx", rqb_rqblx);
        cv.put("cbjl_hz_mc", cbjl_hz_mc);
        cv.put("cbjl_scbd", cbjl_scbd);
        cv.put("cbjl_sccbrq", cbjl_sccbrq);
        cv.put("cbjl_pingjun_yql", cbjl_pingjun_yql);
        cv.put("jzq_bh", jzq_bh);
        cv.put("cbjl_yqzh", cbjl_yqzh);
        cv.put("cbjl_bcbd", cbjl_bcbd);
        cv.put("cbjl_sjcbrq", cbjl_sjcbrq);
        cv.put("cbjl_cb_qk", cbjl_cb_qk);

        sqLiteDatabase.insert(DBHelper.TABLE_NAME, null, cv);
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
