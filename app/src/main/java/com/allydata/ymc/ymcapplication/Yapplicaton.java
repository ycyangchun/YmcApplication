package com.allydata.ymc.ymcapplication;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.allydata.ymc.ymcapplication.base.YmcConfig;
import com.allydata.ymc.ymcapplication.excption.EmergencyHandler;
import com.allydata.ymc.ymcapplication.path.Path;
import com.allydata.ymc.ymcapplication.path.PreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.itangqi.greendao.DaoMaster;
import me.itangqi.greendao.DaoSession;

/**
 * Created by Administrator on 2016/5/5 0005.
 * 易买车 application
 */
public class Yapplicaton extends Application {
    public static List<FragmentActivity> acm = new ArrayList<>();// Activity管理类
    private static Yapplicaton sYapplicaton = null;
    public static int width;
    public static int height;
    public static float density = 0;
    public static PreferencesHelper ph;
    public static Path path;
    public String PRE_NAME;
    public String ROOT_NAME;
    public static  SQLiteDatabase db;
    public static  DaoMaster daoMaster;
    public static  DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        sYapplicaton = this;
        initDisplay();
        initOKHttpUtils();
        initPath();
        setupDatabase();
    }

    /**
     * 避免多次创建生成 Session 对象
     */
    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,
                path.getDatabasePath()+ File.separator+"ymc.db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

    }

    public static Yapplicaton getInstance() {
        return sYapplicaton;
    }
    /**
     * 分辨率
     */
    private void initDisplay() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWm.getDefaultDisplay().getMetrics(dm);
        density = dm.scaledDensity;
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

    /**
     * 初始路径
     */
    private void initPath() {
        PRE_NAME = "PRE_YMC";
        ROOT_NAME = "YMC";
        ph = PreferencesHelper.getInstance(getApplicationContext(), PRE_NAME);
        path = Path.getInstance(getApplicationContext(), ROOT_NAME);
        EmergencyHandler.init(getApplicationContext());
    }

    /**
     *  初始 网络请求
     */
    private void initOKHttpUtils(){
        OkHttpUtils.getInstance()
                .debug("OkHttpUtils", YmcConfig.IS_DEBUG)
                .setConnectTimeout(100000, TimeUnit.MILLISECONDS);
    }


    /**
     * 添加到管理类
     */
    public void addActivityManager(FragmentActivity activity) {
        if (acm.size() == 0)
            acm.add(activity);
        else if (!acm.contains(activity) && isActivityExists(activity))
            acm.add(activity);
    }

    /**
     * 去重
     *
     * @param activity
     * @return 管理类中是否存在此实例
     */
    private boolean isActivityExists(FragmentActivity activity) {
        for (int i = 0; i < acm.size(); i++) {
            if (activity.getClass().getSimpleName()
                    .equals(acm.get(i).getClass().getSimpleName())) {
                acm.get(i).finish();
                acm.remove(i);
                acm.add(activity);
                return false;
            }
        }
        return true;
    }

    /**
     * 退出清空
     */
    public static void exit() {
        for (int i = 0; i < acm.size(); i++) {
            acm.get(i).finish();
        }
    }

}
