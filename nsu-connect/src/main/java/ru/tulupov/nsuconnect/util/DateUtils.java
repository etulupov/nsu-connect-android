package ru.tulupov.nsuconnect.util;

import android.content.Context;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.tulupov.nsuconnect.R;

public class DateUtils {
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");
    private static DateFormat DATE_FORMAT_FULL = new SimpleDateFormat("dd MMM yyyy");
    private static long DAY = 1000 * 60 * 60 * 24;

    public static String formatChatDate(Context context, Date date) {
        Date current = new Date();

        if (isOneDay(date, current)) {
            return DATE_FORMAT.format(date);
        } else if (isTwoDay(date, current)) {
            return context.getString(R.string.date_yesterday);
        } else {
            return DATE_FORMAT_FULL.format(date);
        }

    }

    private static boolean isOneDay(Date date, Date current) {
        Calendar first = Calendar.getInstance();
        Calendar second = Calendar.getInstance();
        first.setTime(date);
        second.setTime(current);

        return (first.get(Calendar.DAY_OF_MONTH) == second.get(Calendar.DAY_OF_MONTH));
    }

    private static boolean isTwoDay(Date date, Date current) {
        Calendar first = Calendar.getInstance();
        Calendar second = Calendar.getInstance();
        first.setTime(date);
        second.setTime(current);

        return (first.get(Calendar.DAY_OF_MONTH) + 1 == second.get(Calendar.DAY_OF_MONTH));
    }
}
