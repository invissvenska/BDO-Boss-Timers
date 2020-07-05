package nl.invissvenska.bdobosstimers.helper;

import androidx.annotation.Nullable;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

    enum Mode {
        NORMAL, DEBUG;
    }

    private Mode mode = Mode.NORMAL;
    private Integer timeOfTheDay = 0;
    private Integer dayOfTheWeek = 0;

    public void setDebug(Integer timeOfTheDay, Integer dayOfTheWeek) {
        this.timeOfTheDay = timeOfTheDay;
        this.dayOfTheWeek = dayOfTheWeek;
        mode = Mode.DEBUG;
    }

    public void setNormal() {
        timeOfTheDay = 0;
        dayOfTheWeek = 0;
        mode = Mode.NORMAL;
    }

    public String minutesToHoursAndMinutes(Integer minutes) {
        Integer hours = minutes / 60;
        Integer remainingMinutes = minutes - hours * 60;
        return hours == 0 ? remainingMinutes + " minutes" : hours + " hours and " + remainingMinutes + " minutes";
    }

    public Integer normalizeDayOfTheWeek(Integer weekDay, @Nullable Integer addDays) {
        addDays = addDays == null ? 0 : addDays;
        Integer alteredWeekDay = (weekDay + addDays) % 7 - 2;
        return alteredWeekDay < 0 ? alteredWeekDay + 7 : alteredWeekDay;
    }

    public Integer getDayOfTheWeek(@Nullable Integer addDays) {
        if (mode == Mode.DEBUG) {
            return dayOfTheWeek;
        }
        addDays = addDays == null ? 0 : addDays;
        Calendar cal = Calendar.getInstance();
        Integer weekDay = cal.get(Calendar.DAY_OF_WEEK);
        return normalizeDayOfTheWeek(weekDay, addDays);
    }

    public Integer getTimeOfTheDay() {
        if (mode == Mode.DEBUG) {
            return timeOfTheDay;
        }
        Calendar cal = Calendar.getInstance();
        Integer hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        Integer minuteOfTheDay = cal.get(Calendar.MINUTE);
        return sixtyToHundredFormat(hourOfDay, minuteOfTheDay);
    }

    public Integer getTimeDifference(Integer time, Integer now) {
if(time>2400 && time-now>2400) {
    return (int)((time%2400-now)*0.6);
} else {
    return (int)((time - now)*0.6);
}
//        return ((if (time>2400 && time-now>2400) (time%2400-now) else (time - now)) * 0.6).toInt();
//
//        return time > 2400 && time - now > 2400 ? (int) ((time % 2400 - now) * 0.6) : (int) ((time - now) * 0.6);
    }

    public Long getSecondsToSpawn(String time) {
        Integer hours = Integer.valueOf(time.split(":")[0]);
        Integer minutes = Integer.valueOf(time.split(":")[1]);

        LocalTime spawnTime = LocalTime.of(hours,minutes,0);
        LocalTime now = LocalTime.now();

        Duration duration = Duration.between(now, spawnTime);
        return duration.getSeconds();
    }

    public void getTimeDifferenceToNow(Integer time) {
        getTimeDifference(time, getTimeOfTheDay());
    }

    public String hundredToSixtyFormat(Integer hundredTime) {
        Integer hours = hundredTime % 2400 / 100;
        Integer minutes = (int) ((hundredTime - (hundredTime / 100) * 100) * 0.6);
        String hoursStr = hours < 10 ? "0" + hours : "" + hours;
        String minutesStr = minutes < 10 ? "0" + minutes : "" + minutes;
        return hoursStr + ":" + minutesStr;
    }

    public Integer sixtyToHundredFormat(Integer hours, Integer minutes) {
        return (int) (hours * 100 + ceil(minutes * 1.6667));
//        return (hours * 100) + minutes;
    }

}
