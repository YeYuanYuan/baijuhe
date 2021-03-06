package com.ifeimo.im.framwork;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;

import com.ifeimo.im.IEmployee;
import com.ifeimo.im.framwork.database.IMDataBaseHelper;

import y.com.sqlitesdk.framework.AppMain;

/**
 * Created by lpds on 2017/4/25.
 */
final class SqliteMain implements AppMain,IEmployee{

    private static SqliteMain sqliteMain = null;

    static {
        sqliteMain = new SqliteMain();
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    private IMDataBaseHelper imDataBaseHelper;

    private SqliteMain(){
        imDataBaseHelper = new IMDataBaseHelper(IMSdk.CONTEXT);
        ManagerList.getInstances().addManager(this);
    }

    public static AppMain getInstances(){
        return sqliteMain;
    }

    @Override
    public Application getApplication() {
        return IMSdk.CONTEXT;
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    @Override
    public SQLiteDatabase getSQLiteDatabase() {
        return imDataBaseHelper.getWritableDatabase();
    }


    public boolean isInitialized() {
        return imDataBaseHelper != null;
    }
}
