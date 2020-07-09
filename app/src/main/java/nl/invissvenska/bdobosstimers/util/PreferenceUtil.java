package nl.invissvenska.bdobosstimers.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import nl.invissvenska.bdobosstimers.helper.BossSettings;

public final class PreferenceUtil {

    private static PreferenceUtil INSTANCE;
    private SharedPreferences preferences;

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

    private Integer getAlertBefore() {
        return preferences.getInt("alert_before", 0);
    }

    private Integer getNumberOfAlerts() {
        return preferences.getInt("number_of_alerts", 0);
    }

    private Integer getAlertDelay() {
        return preferences.getInt("alert_delay", 0);
    }

    private Boolean isVibrationEnabled() {
        return preferences.getBoolean("vibration", false);
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
                1730,
                1731,
                getAlertBefore(),
                getNumberOfAlerts(),
                getAlertDelay(),
                isVibrationEnabled());
    }
}
