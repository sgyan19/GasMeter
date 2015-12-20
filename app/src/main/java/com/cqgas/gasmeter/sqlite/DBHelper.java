package com.cqgas.gasmeter.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cqgas.gasmeter.MyApplication;

/**
 * Created by hs_ghoul on 15/12/13.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper dbHelper;

    static {
        dbHelper = new DBHelper(MyApplication.getContext());
    }

    public static DBHelper getInstance() {
        return dbHelper;
    }

    private static final String DATABASE_NAME = "gas_meter.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "gas_meter";
    public static final String TABLE_NAME_OLD = "gas_meter_old";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME + " (rqb_gh varchar(128) NOT NULL, mm varchar(128) NOT NULL, cbjl_id varchar(128) NOT NULL PRIMARY KEY, cbjl_yqdz_ms varchar(128) NOT NULL, rqb_rqblx varchar(128) NULL, cbjl_hz_mc varchar(128) NOT NULL, cbjl_scbd int unsigned NULL, cbjl_sccbrq int unsigned NULL, cbjl_pingjun_yql varchar(128) NULL, jzq_bh varchar(128) NOT NULL, cbjl_yqzh varchar(128) NULL, cbjl_bcbd int unsigned NULL, cbjl_sjcbrq varchar(128) NULL, cbjl_cb_qk int unsigned NULL)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

}
