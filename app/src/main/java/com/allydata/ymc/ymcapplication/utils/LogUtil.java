package com.allydata.ymc.ymcapplication.utils;

import android.util.Log;

import com.allydata.ymc.ymcapplication.base.YmcConfig;

/**
 * Created by yangchun on 2016/5/6 0006.
 * @version V1.0
 * @ClassName: LogUtil
 * @Description: 打印日志工具类
 *
 */
public class LogUtil {
    public static void i(String tag, String content) {
        if (YmcConfig.IS_DEBUG) {
            String log = getTraceInfo() + "  :  " + content;
            Log.i(tag, log);
        }
    }

    public static void e(String tag, String content) {
        if (YmcConfig.IS_DEBUG) {
            String log = getTraceInfo() + "  :  " + content;
            Log.e(tag, log);
        }
    }

    public static void d(String tag, String content) {
        if (YmcConfig.IS_DEBUG) {
            String log = getTraceInfo() + "  :  " + content;
            Log.d(tag, log);
        }
    }

    public static void v(String tag, String content) {
        if (YmcConfig.IS_DEBUG) {
            String log = getTraceInfo() + "  :  " + content;
            Log.v(tag, log);
        }
    }

    public static void syso(String content) {
        if (YmcConfig.IS_DEBUG) {
            String log = getTraceInfo() + "  :  " + content;
            System.out.println(log);
        }
    }

    /**
     * 获取堆栈信息
     */
    private static String getTraceInfo() {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String className = stacks[2].getClassName();
        int index = className.lastIndexOf('.');
        if (index >= 0) {
            className = className.substring(index + 1, className.length());
        }
        String methodName = stacks[2].getMethodName();
        int lineNumber = stacks[2].getLineNumber();
        sb.append(className).append("->").append(methodName).append("()->").append(lineNumber);
        return sb.toString();
    }
}
