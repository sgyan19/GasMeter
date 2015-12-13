package com.cqgas.gasmeter.center;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cqgas.gasmeter.MyApplication;
import com.cqgas.gasmeter.core.MeterCore;
import com.cqgas.gasmeter.sqlite.DBHelper;
import com.cqgas.gasmeter.utils.DateUtils;
import com.cqgas.gasmeter.utils.StorageUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class ReadMeterCenter {

    private static DBHelper dbHelper;
    private static JsonParser jsonParser;

    static {
        dbHelper = new DBHelper(MyApplication.getContext());
        jsonParser = new JsonParser();
    }

    /**
     * 获取用于UI显示的列表，可能会被UI改动，所以要new一个新容器
     * @throws FileNotFoundException 文件不存在
     * @throws JsonParseException 文件格式错误
     * @return
     */
    public static List<MeterCore> getUiAll() throws FileNotFoundException, JsonParseException, SQLException {
        if (pcFileExists()) {
            buildDBFromPCFile();
            deletePCFile();
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<MeterCore> list;
        try {
            db = dbHelper.getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME, null);
            list = fetchFromCursor(cursor);
            return list;
        } finally {
            if (null != cursor) {
                cursor.close();
            }
            if (null != db) {
                db.close();
            }
        }

        // 测试数据
//        List<MeterCore> result = new ArrayList<>();
//        MeterCore meter = new MeterCore();
//        meter.cbjl_hz_mc = "袁心伦";
//        meter.cbjl_scbd = 42;
//        meter.cbjl_sccbrq = DateUtils.getTimeInMillis("20150313");
//        meter.cbjl_bcbd = 93;
//        meter.cbjl_pingjun_yql = "60";
//        result.add(meter);
//
//        meter = new MeterCore();
//        meter.cbjl_hz_mc = "唐登华";
//        meter.cbjl_scbd = 42;
//        meter.cbjl_sccbrq = DateUtils.getTimeInMillis("20150223");
//        meter.cbjl_bcbd = 93;
//        meter.cbjl_pingjun_yql = "60";
//        result.add(meter);
//
//        meter = new MeterCore();
//        meter.cbjl_hz_mc = "杨世坤";
//        meter.cbjl_scbd = 42;
//        meter.cbjl_sccbrq = DateUtils.getTimeInMillis("20150703");
//        meter.cbjl_bcbd = 0;
//        meter.cbjl_pingjun_yql = "60";
//        result.add(meter);
//
//        meter = new MeterCore();
//        meter.cbjl_hz_mc = "方仲永";
//        meter.cbjl_scbd = 42;
//        meter.cbjl_sccbrq = DateUtils.getTimeInMillis("20150527");
//        meter.cbjl_bcbd = 93;
//        meter.cbjl_pingjun_yql = "60";
//        result.add(meter);
//        return result;
    }

    /**
     * 获取未抄的数据
     * @return
     * @throws FileNotFoundException
     * @throws JsonParseException
     * @throws SQLException
     */
    public static List<MeterCore> getUiUnRead() throws FileNotFoundException, JsonParseException, SQLException {
        if (pcFileExists()) {
            buildDBFromPCFile();
            deletePCFile();
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<MeterCore> list;
        try {
            db = dbHelper.getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHILE cbjl_cb_qk != ?", new String[]{String.valueOf(MeterCore.NORMAL)});
            list = fetchFromCursor(cursor);
            return list;
        } finally {
            if (null != cursor) {
                cursor.close();
            }
            if (null != db) {
                db.close();
            }
        }

        // 测试数据
//        List<MeterCore> result = new ArrayList<>();
//        MeterCore meter = new MeterCore();
//        meter.cbjl_hz_mc = "杨世坤";
//        meter.cbjl_scbd = 42;
//        meter.cbjl_sccbrq = DateUtils.getTimeInMillis("20150703");
//        meter.cbjl_bcbd = 0;
//        meter.cbjl_pingjun_yql = "60";
//        result.add(meter);
//        return result;
    }

    public static void readMeter(String cbjl_id,int number){
        SQLiteDatabase db = null;
        long time = System.currentTimeMillis();
        try {
            ContentValues cv = new ContentValues();
            cv.put("cbjl_cb_qk", MeterCore.NORMAL);
            cv.put("cbjl_sjcbrq", DateUtils.currentTime());
            cv.put("cbjl_bcbd",number);
            db = dbHelper.getWritableDatabase();
            db.update(DBHelper.TABLE_NAME, cv, "cbjl_id = ?", new String[]{cbjl_id});
        } finally {
            if (null != db) {
                db.close();
            }
        }

        updateMeter(cbjl_id, "cbjl_bcbd", number);
        updateMeter(cbjl_id,"cbjl_cb_qk",MeterCore.NORMAL);
    }

    /**
     * 更新字符串列
     * @param cbjl_id 主键
     * @param column 要更新的列
     * @param value 要更新成的值
     * @throws SQLException
     */
    public static void updateMeter(String cbjl_id, String column, String value) throws SQLException {
        SQLiteDatabase db = null;
        try {
            ContentValues cv = new ContentValues();
            cv.put(column, value);
            db = dbHelper.getWritableDatabase();
            db.update(DBHelper.TABLE_NAME, cv, "cbjl_id = ?", new String[]{cbjl_id});
        } finally {
            if (null != db) {
                db.close();
            }
        }
    }

    /**
     * 更新数字列
     * @param cbjl_id 主键
     * @param column 要更新的列
     * @param value 要更新成的值
     * @throws SQLException
     */
    public static void updateMeter(String cbjl_id, String column, Number value) throws SQLException {
        SQLiteDatabase db = null;
        try {
            ContentValues cv = new ContentValues();
            cv.put(column, value.longValue());
            db = dbHelper.getWritableDatabase();
            db.update(DBHelper.TABLE_NAME, cv, "cbjl_id = ?", new String[]{cbjl_id});
        } finally {
            if (null != db) {
                db.close();
            }
        }
    }

    /**
     * 生成要传递给PC的文件
     * @throws IOException
     * @throws JsonParseException
     * @throws SQLException
     */
    public static void buildToPcFile() throws IOException, JsonParseException, SQLException  {
        List<MeterCore> list = getUiAll();
        JsonArray jsonArray = new JsonArray();
        for (MeterCore meter : list) {
            jsonArray.add(meter.buildJsonObject());
        }

        File toPCFile = new File(StorageUtils.getTargetToPcFilePath());
        FileWriter writer = null;
        try {
            writer = new FileWriter(toPCFile);
            writer.write(jsonArray.toString());
            writer.flush();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
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
     * @throws FileNotFoundException 文件不存在
     * @throws JsonParseException 文件格式错误
     * @throws JSONException 数据库错误
     * @return
     */
    private static void buildDBFromPCFile() throws FileNotFoundException, JsonParseException, SQLException {
        SQLiteDatabase db = null;
        BufferedReader bufferedReader = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + DBHelper.TABLE_NAME_OLD);
            db.execSQL("ALTER TABLE " + DBHelper.TABLE_NAME + " RENAME TO " + DBHelper.TABLE_NAME_OLD);
            db.execSQL(DBHelper.CREATE_TABLE);

            bufferedReader = new BufferedReader(new FileReader(StorageUtils.getTargetFormPcFilePath()));
            JsonElement arrayElement = jsonParser.parse(bufferedReader);
            JsonArray array = arrayElement.getAsJsonArray();
            for (JsonElement element : array) {
                JsonObject jsonObject = element.getAsJsonObject();
                MeterCore.insertToDB(jsonObject, db);
            }
        } catch (IllegalStateException | ClassCastException | UnsupportedOperationException e) {
            if (null != db) {
                db.execSQL("DROP TABLE IF EXISTS " + DBHelper.TABLE_NAME);
                db.execSQL("ALTER TABLE " + DBHelper.TABLE_NAME_OLD + " RENAME TO " + DBHelper.TABLE_NAME);
            }
            throw new JsonParseException("Error json format", e);
        } finally {
            if (null != db) {
                db.close();
            }
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static boolean pcFileExists() {
        String pcFilePath = StorageUtils.getTargetFormPcFilePath();
        File pcFile = new File(pcFilePath);
        return pcFile.exists();
    }

    private static void deletePCFile() {
        String pcFilePath = StorageUtils.getTargetFormPcFilePath();
        File pcFile = new File(pcFilePath);
        if (pcFile.exists()) {
            pcFile.delete();
        }
    }

    private static List<MeterCore> fetchFromCursor(Cursor cursor) {
        int size = cursor.getCount();
        List<MeterCore> list = new ArrayList<>(size);
        while (cursor.moveToNext()) {
            list.add(MeterCore.parse(cursor));
        }
        return list;
    }
}
