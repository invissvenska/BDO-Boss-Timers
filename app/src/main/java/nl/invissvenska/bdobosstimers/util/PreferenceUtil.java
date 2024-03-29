package nl.invissvenska.bdobosstimers.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import nl.invissvenska.bdobosstimers.preference.BossSettings;

public final class PreferenceUtil {

    private static PreferenceUtil INSTANCE;
    private final SharedPreferences preferences;

    private PreferenceUtil(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceUtil getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferenceUtil(context);
        }
        return INSTANCE;
    }

    private Boolean isGarmothEnabled() {
        return preferences.getBoolean("garmoth_alert", false);
    }

    private Boolean isKarandaEnabled() {
        return preferences.getBoolean("karanda_alert", false);
    }

    private Boolean isKutumEnabled() {
        return preferences.getBoolean("kutum_alert", false);
    }

    private Boolean isKzarkaEnabled() {
        return preferences.getBoolean("kzarka_alert", false);
    }

    private Boolean isMurakaEnabled() {
        return preferences.getBoolean("muraka_alert", false);
    }

    private Boolean isNouverEnabled() {
        return preferences.getBoolean("nouver_alert", false);
    }

    private Boolean isOffinEnabled() {
        return preferences.getBoolean("offin_alert", false);
    }

    private Boolean isQuintEnabled() {
        return preferences.getBoolean("quint_alert", false);
    }

    private Boolean isVellEnabled() {
        return preferences.getBoolean("vell_alert", false);
    }

    private Integer isMondayEnabled() {
        return preferences.getBoolean("monday_alert", false) ? 2 : 3;
    }

    private Integer isTuesdayEnabled() {
        return preferences.getBoolean("tuesday_alert", false) ? 2 : 3;
    }

    private Integer isWednesdayEnabled() {
        return preferences.getBoolean("wednesday_alert", false) ? 2 : 3;
    }

    private Integer isThursdayEnabled() {
        return preferences.getBoolean("thursday_alert", false) ? 2 : 3;
    }

    private Integer isFridayEnabled() {
        return preferences.getBoolean("friday_alert", false) ? 2 : 3;
    }

    private Integer isSaturdayEnabled() {
        return preferences.getBoolean("saturday_alert", false) ? 2 : 3;
    }

    private Integer isSundayEnabled() {
        return preferences.getBoolean("sunday_alert", false) ? 2 : 3;
    }

    private Server getSelectedServer() {
        String value = preferences.getString("selected_server", "1");
        int newValue = Integer.parseInt(value);
        switch (newValue) {
            case 11:
                return Server.RU;
            case 2:
                return Server.NA;
            case 3:
                return Server.SEA;
            case 4:
                return Server.XBOX_NA;
            case 5:
                return Server.XBOX_EU;
            case 6:
                return Server.PS4_NA;
            case 7:
                return Server.PS4_EU;
            case 8:
                return Server.PS4_ASIA;
            case 9:
                return Server.SA;
            case 10:
                return Server.MENA;
            case 1:
            default:
                return Server.EU;
        }
    }

    private Integer getMaximumBosses() {
        switch (getSelectedServer()) {
            case EU:
            case NA:
                return 7;
            case SEA:
            case SA:
                return 5;
            case MENA:
            case XBOX_NA:
            case XBOX_EU:
            case PS4_NA:
            case PS4_EU:
            case PS4_ASIA:
            default:
                return 4;
        }
    }

    private Integer getAlertBefore() {
        return preferences.getInt("alert_before", 1);
    }

    private Integer getNumberOfAlerts() {
        return preferences.getInt("number_of_alerts", 1);
    }

    private Integer getAlertDelay() {
        return preferences.getInt("alert_delay", 5);
    }

    private Boolean isVibrationEnabled() {
        return preferences.getBoolean("vibration", false);
    }

    private Boolean isSilentAlertEnabled() {
        return preferences.getBoolean("silent_period", false);
    }

    private Integer getTimeAfter() {
        String value = preferences.getString("time_after", "00:00");
        if (value == null) {
            return TimeHelper.getInstance().sixtyToHundredFormat(0, 0);
        }
        return TimeHelper.getInstance().sixtyToHundredFormat(Integer.parseInt(value.split(":")[0]), Integer.parseInt(value.split(":")[1]));
    }

    private Integer getTimeBefore() {
        String value = preferences.getString("time_before", "00:00");
        if (value == null) {
            return TimeHelper.getInstance().sixtyToHundredFormat(0, 0);
        }
        return TimeHelper.getInstance().sixtyToHundredFormat(Integer.parseInt(value.split(":")[0]), Integer.parseInt(value.split(":")[1]));
    }

    private Boolean isTimeZoneOverride() {
        return preferences.getBoolean("timezone_override", false);
    }

    private Integer getTimeZone() {
        return Integer.valueOf(preferences.getString("selected_timezone", "0"));
    }

    public BossSettings getSettings() {
        return new BossSettings(isKzarkaEnabled(),
                isKarandaEnabled(),
                isNouverEnabled(),
                isKutumEnabled(),
                isGarmothEnabled(),
                isOffinEnabled(),
                isVellEnabled(),
                isQuintEnabled(),
                isMurakaEnabled(),
                isMondayEnabled(),
                isTuesdayEnabled(),
                isWednesdayEnabled(),
                isThursdayEnabled(),
                isFridayEnabled(),
                isSaturdayEnabled(),
                isSundayEnabled(),
                getTimeAfter(),
                getTimeBefore(),
                getAlertBefore(),
                getNumberOfAlerts(),
                getAlertDelay(),
                isVibrationEnabled(),
                getSelectedServer(),
                getMaximumBosses(),
                isSilentAlertEnabled(),
                isTimeZoneOverride(),
                getTimeZone());
    }
}
