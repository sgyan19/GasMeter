package com.cqgas.gasmeter.center;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cqgas.gasmeter.core.MeterCore;
import com.cqgas.gasmeter.core.QueryCore;
import com.cqgas.gasmeter.sqlite.DBHelper;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 国耀 on 2015/11/29.
 */
public class QueryMeterCenter {

    private static DBHelper dbHelper;

    static {
        dbHelper = DBHelper.getInstance();
    }

    public static QueryCore getUiQuery(String query) throws SQLException,FileNotFoundException{
        if (null == query) {
            query = "";
        }
        if (ReadMeterCenter.pcFileExists()) {
            ReadMeterCenter.buildDBFromPCFile();
            ReadMeterCenter.deletePCFile();
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        QueryCore queryCore = new QueryCore();
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("select count(*) as count, sum(case when cbjl_cb_qk = " + MeterCore.NORMAL +  " then 1 else 0 end) as normal, sum(cbjl_bcbd) as total1,sum(cbjl_scbd) as total2 from " + DBHelper.TABLE_NAME + " where cbjl_yqdz_ms like ?", new String[]{"%" + query + "%"});
            cursor.moveToNext();
            int count = cursor.getInt(cursor.getColumnIndex("count"));
            int normal = cursor.getInt(cursor.getColumnIndex("normal"));
            int total1 = cursor.getInt(cursor.getColumnIndex("total1"));
            int total2 = cursor.getInt(cursor.getColumnIndex("total2"));
            int unread = count - normal;
            queryCore.userCount = count;
            queryCore.readUserCount = normal;
            queryCore.unReadUserCount = unread;
            queryCore.readData = total1 - total2;
        } finally {
            if (null != cursor) {
                cursor.close();
            }
            if (null != db) {
                db.close();
            }
        }

        db = null;
        cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("select * from " + DBHelper.TABLE_NAME + " where cbjl_yqdz_ms like ?", new String[]{"%" + query + "%"});
            List<MeterCore> list = fetchFromCursor(cursor);
            queryCore.list = list;
        } finally {
            if (null != cursor) {
                cursor.close();
            }
            if (null != db) {
                db.close();
            }
        }

        return queryCore;
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
