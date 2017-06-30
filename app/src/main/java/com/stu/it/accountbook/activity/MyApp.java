package com.stu.it.accountbook.activity;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.stu.it.accountbook.db.DaoMaster;
import com.stu.it.accountbook.db.DaoSession;
import com.stu.it.accountbook.view.LockPatternUtils;

/**
 * Created by wangx on 0004.
 */
public class MyApp extends Application {
    private static MyApp mInstance;
    private LockPatternUtils mLockPatternUtils;
    private DaoMaster.DevOpenHelper helper;
    private DaoMaster daoMaster;
    private SQLiteDatabase database;
    private DaoSession daoSession;

    /**
     * 获取 myAPP对象
     *
     * @return
     */
    public static MyApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mLockPatternUtils = new LockPatternUtils(this);
    }

    public LockPatternUtils getLockPatternUtils() {
        return mLockPatternUtils;
    }

    // 该初始化过程最好放在Application中进行,避免创建多个Session
    public DaoSession setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper创建数据库
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        /**
         * @param context :　Context
         * @param name : 数据库名字
         * @param factory : CursorFactroy
         */
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(this, "account.db", null);
        }
        // 获取数据库
        if (daoMaster == null) {
            database = helper.getWritableDatabase();
        }
        // 获取DaoMaster
        if (daoMaster == null) {
            daoMaster = new DaoMaster(database);
        }
        // 获取Session
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}
