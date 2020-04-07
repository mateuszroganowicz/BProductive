package com.example.bproductive3;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class Util
{
    private static int sTheme;
    public final static int THEME_HAPPY = 0;
    public final static int THEME_SAD = 1;

    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_HAPPY:
                activity.setTheme(R.style.ThemeAppHappy);
                break;
            case THEME_SAD:
                activity.setTheme(R.style.ThemeAppSad);
                break;
        }
    }
}
