package com.mdb.example.administrator.Utils;

/**
 * Created by Administrator on 2018/1/29.
 */

public class Utils2 {
    // 两次点击按钮之间的点击间隔不能少于4000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 4000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
