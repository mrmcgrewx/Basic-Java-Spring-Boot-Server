package util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kylemcgrew on 12/4/17.
 */
public class DateManager {

    public static Date getCurrentDate() {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        return now;
    }

    public static Date getExperationDate() {
        Date date = getCurrentDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR,12);
        Date experation = cal.getTime();
        return experation;
    }
}
