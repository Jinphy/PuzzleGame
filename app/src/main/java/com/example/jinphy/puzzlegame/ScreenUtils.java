package com.example.jinphy.puzzlegame;

import android.content.Context;

/**
 * Created by jinphy on 2017/7/16.
 */

public class ScreenUtils {

    public static float getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static float getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

}
