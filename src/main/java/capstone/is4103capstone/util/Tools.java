package capstone.is4103capstone.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class Tools {
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static Date getYesterday(Date today){
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE,-1);
        return c.getTime();
    }

    public static Date getFirstDayOfYear(int year) throws ParseException {
        return dateFormatter.parse(year+"-01-01");
    }
    public static String getFirstStrDayOfYear(int year){
        return year+"-01-01";
    }

    public static int getCalendarYear(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }
    public static boolean isSameYear(Date one, int calendarYear){
        LocalDate localDate = one.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        return year == calendarYear;
    }

    public static Date getTomorrow(){
        return getTomorrow(new Date());
    }
    public static String getTomorrowStr(){
        return getTomorrowStr(new Date());
    }
    public static String getTomorrowStr(Date today){
        return dateFormatter.format(getTomorrow(today));
    }
    public static Date getTomorrow(Date today){
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE,1);
        return c.getTime();
    }


//    public static void main(String []args){
//        try{
//            System.out.println(dateFormatter.format(getFirstDayOfYear(2019)));
//        }catch (Exception ex){
//
//        }
//    }

}
