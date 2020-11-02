package nl.invissvenska.bdobosstimers.helper;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

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
        int hours = toIntExact(seconds / 3600);
        int minutes = toIntExact(((seconds - (hours * 3600)) / 60));
        int remainingSeconds = toIntExact(seconds - (hours * 3600) - (minutes * 60));
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    public Integer normalizeDayOfTheWeek(Integer weekDay, @Nullable Integer addDays) {
        addDays = addDays == null ? 0 : addDays;
        int alteredWeekDay = (weekDay + addDays) % 7 - 1;
        return alteredWeekDay < 0 ? alteredWeekDay + 7 : alteredWeekDay;
    }

    public Integer getDayOfTheWeek(@Nullable Integer addDays) {
        addDays = addDays == null ? 0 : addDays;
        ZonedDateTime convertedTime = convertLocalZoneToUTC();
        Integer weekDay = convertedTime.getDayOfWeek().getValue();
        return normalizeDayOfTheWeek(weekDay, addDays);
    }

    public Integer getTimeOfTheDay() {
        ZonedDateTime convertedTime = convertLocalZoneToUTC();
        Integer hourOfDay = convertedTime.getHour();
        Integer minuteOfTheDay = convertedTime.getMinute();
        return sixtyToHundredFormat(hourOfDay, minuteOfTheDay);
    }

    public Integer getTimeOfTheDayWithoutTimeZoneConversion() {
        LocalDateTime ldt = LocalDateTime.now();
        Integer hourOfDay = ldt.getHour();
        Integer minuteOfTheDay = ldt.getMinute();
        return sixtyToHundredFormat(hourOfDay, minuteOfTheDay);
    }

    public Integer getTimeDifference(Integer time, Integer now) {
        return time > 2400 && time - now > 2400 ? (int) ((time % 2400 - now) * 0.6) : (int) ((time - now) * 0.6);
    }

    public Integer getTimeDifferenceNextDay(Integer time, Integer now) {
        return (int) (((2400 - now) + time) * 0.6);
    }

    public Integer sixtyToHundredFormat(Integer hours, Integer minutes) {
        return (int) (hours * 100 + ceil(minutes * 1.6667));
    }

    private static int toIntExact(long value) {
        if ((int) value != value) {
            throw new ArithmeticException("integer overflow");
        }
        return (int) value;
    }

    private ZonedDateTime convertLocalZoneToUTC() {
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZoneId localZoneId = ZoneId.systemDefault();
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime configTime = ldt.atZone(localZoneId);
        return configTime.withZoneSameInstant(utcZoneId);
    }

    public String hundredToSixtyFormat(Integer hundredTime) {
        String timeFormat = "HH:mm";
        DateTimeFormatter format = DateTimeFormatter.ofPattern(timeFormat);
        return format.format(convertUTCToLocalZone(hundredTime));
    }

    private ZonedDateTime convertUTCToLocalZone(final int time) {
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZoneId localZoneId = ZoneId.systemDefault();

        LocalDateTime ldt = LocalDateTime.now().withHour(hundredToSixtyFormatHours(time)).withMinute(hundredToSixtyFormatMinutes(time));
        ZonedDateTime configTime = ldt.atZone(utcZoneId);
        return configTime.withZoneSameInstant(localZoneId);
    }

    private Integer hundredToSixtyFormatHours(Integer hundredTime) {
        return hundredTime % 2400 / 100;
    }

    private Integer hundredToSixtyFormatMinutes(Integer hundredTime) {
        return (int) ((hundredTime - (hundredTime / 100) * 100) * 0.6);
    }
}
