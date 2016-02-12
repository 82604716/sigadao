package com.siganid.sigadaobase.siga;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/4.
 */


public abstract class BaseDao {
    Context context;

    public SigaDb db;

    protected String voName = "";

    public BaseDao(Context context) {
        this.context = context;
        db = SigaDb.create(context, true);
    }


    protected void creatTable() {
        db.exeSql(getCreatSql());
    }

    public void checkAndCreatTable() {
        if (!tableIsExist()) {
            creatTable();
        }
    }


    static List<String> tableExitCache = new ArrayList<>();

    public boolean checkTableExitCache(String tableName) {
        return tableExitCache.contains(tableName);
    }

    protected boolean tableIsExist() {
        if (checkTableExitCache(getCheckSql()))
            return true;
        Cursor cursor = null;
        try {
            cursor = db.findBySql(getCheckSql());
            if (cursor != null && cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    tableExitCache.add(getCheckSql());
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            cursor = null;
        }
        return false;
    }

    public abstract String getCreatSql();

    public abstract String getCheckSql();


    public abstract <T> T find(String whereStr);

    public abstract <T> List<T> findAll(String whereStr);

    public abstract void save(Object object);

    public abstract void update(Object object, String whereStr);

    public abstract void delete(String whereStr);

}
