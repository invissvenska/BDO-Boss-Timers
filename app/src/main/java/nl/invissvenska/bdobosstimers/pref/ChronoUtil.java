package nl.invissvenska.bdobosstimers.pref;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChronoUtil {
    final static SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm", Locale.ROOT);

    final static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);

    static Calendar dateToCalendar(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}