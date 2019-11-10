package capstone.is4103capstone.entities.helper;

import java.time.DayOfWeek;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    // ------------------------------------- Get Date -------------------------------------

    public static Date getDateWithoutTimeUsingCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    // Note: month in Java's calendar is zero-based
    public static Date getDateByYearMonthDateHourMinute(int year, int month, int date, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDateByYearMonthDateHourMinuteSecond(int year, int month, int date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, date, hour, minute, second);
        return calendar.getTime();
    }

    public static Date getDaysAfter(Date originalDate, int daysAfter) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originalDate);
        calendar.add(Calendar.DAY_OF_MONTH, daysAfter);
        return calendar.getTime();
    }

    public static Date getDaysBefore(Date originalDate, int daysBefore) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originalDate);
        calendar.add(Calendar.DAY_OF_MONTH, (daysBefore * -1));
        return calendar.getTime();
    }



    // ------------------------------------- Get Time Components -------------------------------------

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

    // Note: month in Java's calendar is zero-based
    public static int getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.MONTH) + 1);
    }

    public static int getDayOfMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static YearMonth getYearMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
    }
}
