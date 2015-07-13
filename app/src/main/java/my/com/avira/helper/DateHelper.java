package my.com.avira.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by randiwaranugraha on 7/10/15.
 */
public class DateHelper {

    private SimpleDateFormat simpleDateFormat;

    public DateHelper(String dateFormat) {
        simpleDateFormat = new SimpleDateFormat(dateFormat);
    }

    public DateHelper(String dateFormat, Locale locale) {
        simpleDateFormat = new SimpleDateFormat(dateFormat, locale);
    }

    public Date toDate(String dateString) {
        try {
            Date date = simpleDateFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String fromDate(Date date) {
        return simpleDateFormat.format(date);
    }
}