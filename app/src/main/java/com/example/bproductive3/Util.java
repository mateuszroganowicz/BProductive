package com.example.bproductive3;

import android.app.Activity;
import android.content.Intent;

public class Util
{
    private static int sTheme;
    public final static int THEME_NORMAL = 0;
    public final static int THEME_SAD = 1;
    public final static int THEME_ANGRY = 2;
    public final static int THEME_CONFIDENT = 3;
    public final static int THEME_HAPPY = 4;
    public final static int THEME_STRESSED = 5;

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
            case THEME_NORMAL:
                activity.setTheme(R.style.ThemeAppNormal);
                break;
            case THEME_HAPPY:
                activity.setTheme(R.style.ThemeAppHappy);
                break;
            case THEME_SAD:
                activity.setTheme(R.style.ThemeAppSad);
                break;
            case THEME_ANGRY:
                activity.setTheme(R.style.ThemeAppAngry);
                break;
            case THEME_CONFIDENT:
                activity.setTheme(R.style.ThemeAppConfident);
                break;
            case THEME_STRESSED:
                activity.setTheme(R.style.ThemeAppStressed);
                break;
        }
    }
}
