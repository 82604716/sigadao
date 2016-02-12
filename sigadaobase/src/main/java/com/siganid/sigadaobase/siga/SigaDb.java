package com.siganid.sigadaobase.siga;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SigaDb {

    private static final String TAG = "SigaDb";

    private static HashMap<String, SigaDb> dbMap = new HashMap<String, SigaDb>();

    private SQLiteDatabase db;
    private DaoConfig config;

    private SigaDb(DaoConfig config) {
        if (config == null)
            throw new RuntimeException("daoConfig is null");
        if (config.getContext() == null)
            throw new RuntimeException("android context is null");
        this.db = new SqliteDbHelper(config.getContext().getApplicationContext(), config.getDbName(), config.getDbVersion(), config.getDbUpdateListener()).getWritableDatabase();
        this.config = config;
    }


    private synchronized static SigaDb getInstance(DaoConfig daoConfig) {
        SigaDb dao = dbMap.get(daoConfig.getDbName());
        if (dao == null) {
            dao = new SigaDb(daoConfig);
            dbMap.put(daoConfig.getDbName(), dao);
        }
        return dao;
    }


    public static SigaDb create(Context context) {
        DaoConfig config = new DaoConfig();
        config.setContext(context);
        return getInstance(config);
    }


    public static SigaDb create(Context context, boolean isDebug) {
        DaoConfig config = new DaoConfig();
        config.setContext(context);
        config.setDebug(isDebug);
        return getInstance(config);
    }


    public static SigaDb create(Context context, String dbName) {
        DaoConfig config = new DaoConfig();
        config.setContext(context);
        config.setDbName(dbName);
        return getInstance(config);
    }


    public static SigaDb create(Context context, String dbName, boolean isDebug) {
        DaoConfig config = new DaoConfig();
        config.setContext(context);
        config.setDbName(dbName);
        config.setDebug(isDebug);
        return getInstance(config);
    }


    public static SigaDb create(Context context, String dbName, boolean isDebug, int dbVersion, DbUpdateListener dbUpdateListener) {
        DaoConfig config = new DaoConfig();
        config.setContext(context);
        config.setDbName(dbName);
        config.setDebug(isDebug);
        config.setDbVersion(dbVersion);
        config.setDbUpdateListener(dbUpdateListener);
        return getInstance(config);
    }

    /**
     * 创建FinalDb
     *
     * @param daoConfig
     * @return
     */
    public static SigaDb create(DaoConfig daoConfig) {
        return getInstance(daoConfig);
    }

    public void exeSql(String sql) {
        if (sql != null) {
            debugSql(sql);
            db.execSQL(sql);
        } else {
            Log.e(TAG, "sava error:sql is null");
        }
    }

    /**
     * 根据条件查找所有数据
     *
     * @param strSQL
     */
    public Cursor findBySql(String strSQL) {
        debugSql(strSQL);
        Cursor cursor = db.rawQuery(strSQL, null);
        return cursor;
    }

    private void debugSql(String sql) {
        if (config != null && config.isDebug())
            Log.d("Debug SQL", ">>>>>>  " + sql);
    }


    public static class DaoConfig {
        private Context context = null;//android上下文
        private String dbName = "siga.db";//数据库名字
        private int dbVersion = 1;//数据库版本
        private boolean debug = true;
        private DbUpdateListener dbUpdateListener;

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public int getDbVersion() {
            return dbVersion;
        }

        public void setDbVersion(int dbVersion) {
            this.dbVersion = dbVersion;
        }

        public boolean isDebug() {
            return debug;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
        }

        public DbUpdateListener getDbUpdateListener() {
            return dbUpdateListener;
        }

        public void setDbUpdateListener(DbUpdateListener dbUpdateListener) {
            this.dbUpdateListener = dbUpdateListener;
        }

    }


    class SqliteDbHelper extends SQLiteOpenHelper {

        private DbUpdateListener mDbUpdateListener;

        public SqliteDbHelper(Context context, String name, int version, DbUpdateListener dbUpdateListener) {
            super(context, name, null, version);
            this.mDbUpdateListener = dbUpdateListener;
        }

        public void onCreate(SQLiteDatabase db) {
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (mDbUpdateListener != null) {
                mDbUpdateListener.onUpgrade(db, oldVersion, newVersion);
            } else { //清空所有的数据信息
                Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type ='table'", null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        db.execSQL("DROP TABLE " + cursor.getString(0));
                    }
                }
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }
        }
    }

    public interface DbUpdateListener {
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

}
