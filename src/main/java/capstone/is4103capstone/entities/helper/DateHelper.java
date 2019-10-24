package capstone.is4103capstone.entities.helper;

import java.time.DayOfWeek;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static Date getDateWithoutTimeUsingCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static DayOfWeek getDayOfWeekFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DayOfWeek.of(calendar.DAY_OF_WEEK);
    }

    public static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static YearMonth getYearMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return YearMonth.of(Calendar.YEAR, Calendar.MONTH);
    }
}
