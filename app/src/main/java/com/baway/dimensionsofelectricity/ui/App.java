package com.baway.dimensionsofelectricity.ui;

import android.app.Application;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.15
 *@Time: 19:42:10
 *@Description:
 * */public class App extends Application {

    //private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        /*DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "config", null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();*/
    }

    /*public static DaoSession getDaoSession() {
        return daoSession;
    }*/
}
