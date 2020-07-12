package nl.invissvenska.bdobosstimers.helper;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import java.util.Calendar;

import static java.lang.Math.ceil;

public class TimeHelper {

    private static TimeHelper INSTANCE = null;

    private TimeHelper() {
    }

    public static TimeHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TimeHelper();
        }
        return INSTANCE;
    }

    @SuppressLint("DefaultLocale")
    public String secondsToHoursAndMinutesAndSeconds(Long seconds) {
        int hours = Math.toIntExact(seconds / 3600);
        int minutes = Math.toIntExact(((seconds - (hours * 3600)) / 60));
        int remainingSeconds = Math.toIntExact(seconds - (hours * 3600) - (minutes * 60));
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    public Integer normalizeDayOfTheWeek(Integer weekDay, @Nullable Integer addDays) {
        addDays = addDays == null ? 0 : addDays;
        int alteredWeekDay = (weekDay + addDays) % 7 - 2;
        return alteredWeekDay < 0 ? alteredWeekDay + 7 : alteredWeekDay;
    }

    public Integer getDayOfTheWeek(@Nullable Integer addDays) {
        addDays = addDays == null ? 0 : addDays;
        Calendar cal = Calendar.getInstance();
        Integer weekDay = cal.get(Calendar.DAY_OF_WEEK);
        return normalizeDayOfTheWeek(weekDay, addDays);
    }

    public Integer getTimeOfTheDay() {
        Calendar cal = Calendar.getInstance();
        Integer hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        Integer minuteOfTheDay = cal.get(Calendar.MINUTE);
        return sixtyToHundredFormat(hourOfDay, minuteOfTheDay);
    }

    public Integer getTimeDifference(Integer time, Integer now) {
        return time > 2400 && time - now > 2400 ? (int) ((time % 2400 - now) * 0.6) : (int) ((time - now) * 0.6);
    }

    public Integer getTimeDifferenceNextDay(Integer time, Integer now) {
        return (int) (((2400 - now) + time) * 0.6);
    }

    public void getTimeDifferenceToNow(Integer time) {
        getTimeDifference(time, getTimeOfTheDay());
    }

    public String hundredToSixtyFormat(Integer hundredTime) {
        int hours = hundredTime % 2400 / 100;
        int minutes = (int) ((hundredTime - (hundredTime / 100) * 100) * 0.6);
        String hoursStr = hours < 10 ? "0" + hours : "" + hours;
        String minutesStr = minutes < 10 ? "0" + minutes : "" + minutes;
        return hoursStr + ":" + minutesStr;
    }

    public Integer sixtyToHundredFormat(Integer hours, Integer minutes) {
        return (int) (hours * 100 + ceil(minutes * 1.6667));
    }
}
