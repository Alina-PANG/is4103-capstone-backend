package capstone.is4103capstone.util;

import java.text.SimpleDateFormat;
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

    public static Date getTomorrow(Date today){
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE,1);
        return c.getTime();
    }
    public static String getTomorrowStr(Date today){
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE,1);
        return dateFormatter.format(c.getTime());
    }
}
