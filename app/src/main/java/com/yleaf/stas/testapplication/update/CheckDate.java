package com.yleaf.stas.testapplication.update;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class CheckDate {
    private Activity activity;

    public CheckDate(Activity activity) {
        this.activity = activity;
    }

    public boolean isTodayNewDay() {

        int savedDayOfYear;
        int currentDayOfYear;

        int savedYear;
        int currentYear;

        long milliseconds;
        milliseconds = activity.getSharedPreferences("update", MODE_PRIVATE).getLong("time", 0);

        if(milliseconds == 0)
            return true;

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(milliseconds);
        savedDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        savedYear = calendar.get(Calendar.YEAR);

        calendar.setTime(new Date());
        currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        currentYear = calendar.get(Calendar.YEAR);

        Log.i("DATE", String.valueOf(currentDayOfYear + " " + savedDayOfYear + " " + currentYear + " " + savedYear));

        return currentDayOfYear > savedDayOfYear || currentYear > savedYear;
    }
}
