package net.breezeware.dynamo.util.string;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * Parse a string of form 'yyyy-MM-dd' into a Calendar object.
     * 
     * @param dateValStr
     * @return Calendar object created form the String value
     * @throws ParseException
     */
    public static Calendar parseDateString_yyyyMMdd(String dateValStr) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValObj = dateFormat.parse(dateValStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateValObj);
        return cal;
    }

    /**
     * Format the Calendar object into a string of form 'mm-dd-yyyy'
     * 
     * @param cal
     * @return String value of the Calendar object formatted in specified pattern
     */
    public static String formatCalendar_mmddyyyy_withDashes(Calendar cal) {
        if (cal != null) {
            String formattedDateValStr = (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + "-"
                    + cal.get(Calendar.YEAR);
            return formattedDateValStr;
        } else {
            return "";
        }
    }

    /**
     * Format the Calendar object into a string of form 'mm-dd-yyyy'
     * 
     * @param cal
     * @return String value of the Calendar object formatted in specified pattern
     */
    public static String formatCalendar_mmddyyyyhhmmss_withDashes(Calendar cal) {
        if (cal != null) {
            String AM_PM = "";
            if (cal.get(Calendar.AM_PM) == 0) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }

            String formattedDateValStr = (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + "-"
                    + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":"
                    + cal.get(Calendar.SECOND) + " " + AM_PM;
            return formattedDateValStr;
        } else {
            return "";
        }
    }
}