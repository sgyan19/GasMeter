package com.cqgas.gasmeter.center;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cqgas.gasmeter.core.MeterCore;
import com.cqgas.gasmeter.core.QueryCore;
import com.cqgas.gasmeter.sqlite.DBHelper;

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

    public static QueryCore getUiQuery(String query) throws SQLException {
        if (null == query) {
            query = "";
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("select count(*) as count, sum(case when cbjl_cb_qk = " + MeterCore.NORMAL +  " then 1 else 0 end) as normal, sum(cbjl_bcbd) as total from " + DBHelper.TABLE_NAME + " where cbjl_yqdz_ms like ?", new String[]{"%" + query + "%"});
            cursor.moveToNext();
            int count = cursor.getInt(cursor.getColumnIndex("count"));
            int normal = cursor.getInt(cursor.getColumnIndex("normal"));
            int total = cursor.getInt(cursor.getColumnIndex("total"));
            int unread = count = normal;
            QueryCore queryCore = new QueryCore();
            queryCore.userCount = count;
            queryCore.readUserCount = normal;
            queryCore.unReadUserCount = unread;
            queryCore.readData = total;
            return queryCore;
        } finally {
            if (null != cursor) {
                cursor.close();
            }
            if (null != db) {
                db.close();
            }
        }
    }
}
