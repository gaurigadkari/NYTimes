package com.example.android.nytimes.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.nytimes.R;

public class SharedPref {
    public static final String PREFERENCES_FILENAME = "preferences";
    public static final String ART = "art";
    public static final String FASHION = "fashion";
    public static final String SPORTS = "sports";
    public static final String HEALTH = "health";
    public static final String EDUCATION = "education";
    public static final String NEWS_DESK = "newsDesk";
    public static final String NEWS_DESK_BOOLEAN = "newsDeskBoolean";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

    public static String createNewsDeskString(Boolean art, Boolean fashion, Boolean sports, Boolean education, Boolean health) {
        StringBuilder newsDesk = new StringBuilder();

        newsDesk.append("news_desk:(");
        if (art) {
            newsDesk.append("\"" + R.string.art + "\" ");
        }
        if (fashion) {
            newsDesk.append("\"" + R.string.fashion + "\" ");
        }
        if (sports) {
            newsDesk.append("\"" + R.string.sports + "\" ");
        }
        if (education) {
            newsDesk.append("\"" + R.string.education + "\" ");
        }
        if (health) {
            newsDesk.append("\"" + R.string.health + "\" ");
        }
        newsDesk.append(")");
        return newsDesk.toString();
    }
}
