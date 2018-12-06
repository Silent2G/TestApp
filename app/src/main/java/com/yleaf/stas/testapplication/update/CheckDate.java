package com.yleaf.stas.testapplication.update;

import android.content.Context;

import com.yleaf.stas.testapplication.db.room.App;
import com.yleaf.stas.testapplication.di.annotations.ApplicationContext;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

public class CheckDate {

    @ApplicationContext
    @Inject
    public Context context;

    public CheckDate() {
        App.getInstance().getAppContextComponent().inject(this);
    }

    public boolean isTodayNewDay() {

        int savedDayOfYear;
        int currentDayOfYear;

        int savedYear;
        int currentYear;

        long milliseconds;
        milliseconds = context.getSharedPreferences("update", MODE_PRIVATE).getLong("time", 0);

        if(milliseconds == 0)
            return true;

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(milliseconds);
        savedDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        savedYear = calendar.get(Calendar.YEAR);

        calendar.setTime(new Date());
        currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        currentYear = calendar.get(Calendar.YEAR);

        return currentDayOfYear > savedDayOfYear || currentYear > savedYear;
    }
}