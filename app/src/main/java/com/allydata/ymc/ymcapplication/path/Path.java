package com.allydata.ymc.ymcapplication.path;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import com.allydata.ymc.ymcapplication.base.YmcConfig;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/5 0005.
 */
public class Path implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 7373248994698828806L;

    public static PreferencesHelper ph;

    private static Context ctx;
    /**
     * application version code
     */
    public static String APP_VERSION;
    /**
     * application package name
     */
    public static String APP_PACKAGE;
    /**
     * application card storage
     */
    public static File EXT_STORAGE;

    /**
     * 应用根目录 YMC
     */
    public static String ROOT_NAME;



    public static Path getInstance(Context context, String name) {
        Path.ctx = context;
        Path.ROOT_NAME = name;
        return PathHolder.instance;
    }

    private static class PathHolder {
        public final static Path instance = new Path();
    }

    private Path() {
        initSelf();
    }
    /**
     * application root path,when base package application initialization method
     * call this library application create the folder in the card and create
     * other folder in root folder to use another communications.
     */
    static String rootPath;

    /**
     * put the properties file for sup or file system need
     */
//    static String configPath;

    /**
     * when application in test model log will not output if application
     * exception the reason be write to file and upload to server
     */
    static String exlogPath;

    /**
     * auto create database to this path or delete database from this path when
     * the application update self or come data error
     */
    static String databasePath;

    /**
     * put dictionary to this path
     */
//    static String dictPath;

    /**
     * put some temporary file to this path
     */
    static String downloadPath;

    /**
     * resource in this path
     */

    static String resourcePath;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        if (!ph.getString("root").equals(rootPath)) {
            if (new File(getRootPath()).renameTo(new File(rootPath)))
                ph.setString("root", rootPath);
        }
        Path.rootPath = rootPath;
        if (YmcConfig.IS_DEBUG)
            System.err.println("Path.setRootPath==>>" + Path.rootPath);
    }

//    public String getConfigPath() {
//        return configPath;
//    }
//
//    public void setConfigPath(String configPath) {
//        if (!ph.getString("config").equals(configPath)) {
//            if (new File(getConfigPath()).renameTo(new File(configPath)))
//                ph.setString("config", configPath);
//        }
//        Path.configPath = configPath;
//    }

    public String getExlogPath() {
        return exlogPath;
    }

    public void setExlogPath(String exlogPath) {
        if (!ph.getString("exlog").equals(exlogPath)) {
            if (new File(getExlogPath()).renameTo(new File(exlogPath)))
                ph.setString("exlog", exlogPath);
        }
        Path.exlogPath = exlogPath;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        if (!ph.getString("database").equals(databasePath)) {
            if (new File(getDatabasePath()).renameTo(new File(databasePath)))
                ph.setString("database", databasePath);
        }
        Path.databasePath = databasePath;
    }

//    public String getDictPath() {
//        return dictPath;
//    }
//
//    public void setDictPath(String dictPath) {
//        if (!ph.getString("dict").equals(dictPath)) {
//            if (new File(getDictPath()).renameTo(new File(dictPath)))
//                ph.setString("dict", dictPath);
//        }
//        Path.dictPath = dictPath;
//    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        if (!ph.getString("download").equals(downloadPath)) {
            if (new File(getDownloadPath()).renameTo(new File(downloadPath)))
                ph.setString("download", downloadPath);
        }
        Path.downloadPath = downloadPath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        if (!ph.getString("resource").equals(resourcePath)) {
            if (new File(getResourcePath()).renameTo(new File(resourcePath)))
                ph.setString("resource", resourcePath);
        }
        Path.resourcePath = resourcePath;
    }

    /**
     * delete the folder files
     *
     * @return success or failure
     */
    public boolean deleteFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            File f = new File(path);
            for (File file : f.listFiles()) {
                if (file.isFile())
                    file.delete();
            }
            return true;
        } else
            return false;
    }

    /**
     * create the folder in root path
     *
     * @param path
     * @return success or failure
     */
    public boolean createFolder(String path) {
        if (!TextUtils.isEmpty(path)) {
            File f = new File(path);
            if (!f.exists())
                f.mkdir();
            return true;
        } else
            return false;
    }

    /**
     * initialization this folder and initialization path
     *
     */
    private void initSelf() {
        ph = PreferencesHelper.getInstance(ctx, PreferencesHelper.PRE_NAME);
        PackageManager pm = ctx.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (pi != null) {
            APP_VERSION = pi.versionName;
            APP_PACKAGE = pi.packageName;
        } else
            System.err.println("get application version & package failure!");
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            EXT_STORAGE = Environment.getExternalStorageDirectory();
        else
            EXT_STORAGE = ctx.getCacheDir();

        //---------begin---添加应用路径-----------
        StringBuffer stringBuffer = new StringBuffer();
        String path = EXT_STORAGE.getAbsolutePath();
        stringBuffer.append(path)
                    .append(File.separator)
                    .append("Android")
                    .append(File.separator)
                    .append("data")
                    .append(File.separator)
                    .append(APP_PACKAGE);
        EXT_STORAGE = new File(stringBuffer.toString());
        if(!EXT_STORAGE.exists()) {
            EXT_STORAGE.mkdirs();
        }
        //---------end--------------
        File base = null;
        if (TextUtils.isEmpty(ph.getString("root"))) {
            base = new File(EXT_STORAGE, ROOT_NAME);
            if (base.isDirectory() || base.mkdir()) {
                ph.setString("root", base.getAbsolutePath());
                setRootPath(base.getAbsolutePath());
            }
        } else {
            base = new File(ph.getString("root"));
            setRootPath(ph.getString("root"));
        }
//        if (TextUtils.isEmpty(ph.getString("config"))) {
//            File configDir = new File(base, "config");
//            if (configDir.isDirectory() || configDir.mkdir()) {
//                ph.setString("config", configDir.getAbsolutePath());
//                setConfigPath(configDir.getAbsolutePath());
//            }
//        } else
//            setConfigPath(ph.getString("config"));
        if (TextUtils.isEmpty(ph.getString("exlog"))) {
            File exlogDir = new File(base, "exlog");
            if (exlogDir.isDirectory() || exlogDir.mkdir()) {
                ph.setString("exlog", exlogDir.getAbsolutePath());
                setExlogPath(exlogDir.getAbsolutePath());
            }
        } else
            setExlogPath(ph.getString("exlog"));
        if (TextUtils.isEmpty(ph.getString("database"))) {
            File databaseDir = new File(base, "database");
            if (databaseDir.isDirectory() || databaseDir.mkdir()) {
                ph.setString("database", databaseDir.getAbsolutePath());
                setDatabasePath(databaseDir.getAbsolutePath());
            }
        } else
            setDatabasePath(ph.getString("database"));
//        if (TextUtils.isEmpty(ph.getString("dict"))) {
//            File dictDir = new File(base, "dict");
//            if (dictDir.isDirectory() || dictDir.mkdir()) {
//                ph.setString("dict", dictDir.getAbsolutePath());
//                setDictPath(dictDir.getAbsolutePath());
//            }
//        } else
//            setDictPath(ph.getString("dict"));
        if (TextUtils.isEmpty(ph.getString("download"))) {
            File downloadDir = new File(base, "download");
            if (downloadDir.isDirectory() || downloadDir.mkdir()) {
                ph.setString("download", downloadDir.getAbsolutePath());
                setDownloadPath(downloadDir.getAbsolutePath());
            }
        } else
            setDownloadPath(ph.getString("download"));
        if (TextUtils.isEmpty(ph.getString("resource"))) {
            File resourceDir = new File(base, "resource");
            if (resourceDir.isDirectory() || resourceDir.mkdir()) {
                ph.setString("resource", resourceDir.getAbsolutePath());
                setResourcePath(resourceDir.getAbsolutePath());
            }
        } else
            setResourcePath(ph.getString("resource"));
        if (YmcConfig.IS_DEBUG)
            System.err.println("Path.initSelf==>>" + Path.rootPath);
    }
}
