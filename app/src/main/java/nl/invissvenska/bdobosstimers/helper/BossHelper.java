package nl.invissvenska.bdobosstimers.helper;

import android.util.Log;

import nl.invissvenska.bdobosstimers.util.Boss;

import static nl.invissvenska.bdobosstimers.Constants.EMPTY;
import static nl.invissvenska.bdobosstimers.Constants.GARMOTH;
import static nl.invissvenska.bdobosstimers.Constants.KARANDA;
import static nl.invissvenska.bdobosstimers.Constants.KUTUM;
import static nl.invissvenska.bdobosstimers.Constants.KZARKA;
import static nl.invissvenska.bdobosstimers.Constants.MURAKA;
import static nl.invissvenska.bdobosstimers.Constants.NOUVER;
import static nl.invissvenska.bdobosstimers.Constants.OFFIN;
import static nl.invissvenska.bdobosstimers.Constants.QUINT;
import static nl.invissvenska.bdobosstimers.Constants.VELL;

public class BossHelper {

    private static BossHelper INSTANCE = null;
    private final String[] timeGrid = {"00:15", "02:00", "05:00", "09:00", "12:00", "16:00", "19:00", "22:15", "23:15"};
    private final Integer[] timeIntGrid = {25, 200, 500, 900, 1200, 1600, 1900, 2225, 2325};

    private final String[][] bossGrid = {
            {KARANDA + "&" + KUTUM, KARANDA, KZARKA, KZARKA, OFFIN, KUTUM, NOUVER, KZARKA, EMPTY},
            {KARANDA, KUTUM, KZARKA, NOUVER, KUTUM, NOUVER, KARANDA, GARMOTH, EMPTY},
            {KUTUM + "&" + KZARKA, KARANDA, KZARKA, KARANDA, EMPTY, KUTUM + "&" + OFFIN, VELL, KARANDA + "&" + KZARKA, MURAKA + "&" + QUINT},
            {NOUVER, KUTUM, NOUVER, KUTUM, NOUVER, KZARKA, KUTUM, GARMOTH, EMPTY},
            {KARANDA + "&" + KZARKA, NOUVER, KARANDA, KUTUM, KARANDA, NOUVER, KZARKA, KUTUM + "&" + KZARKA, EMPTY},
            {KARANDA, OFFIN, NOUVER, KUTUM, NOUVER, MURAKA + "&" + QUINT, KARANDA + "&" + KZARKA, EMPTY, EMPTY},
            {KUTUM + "&" + NOUVER, KZARKA, KUTUM, NOUVER, KZARKA, VELL, GARMOTH, KZARKA + "&" + NOUVER, EMPTY}
    };

    private BossHelper() {
    }

    public static BossHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BossHelper();
        }
        return INSTANCE;
    }

    public Boss getNextBoss(Integer position) {
        final Integer now = TimeHelper.getInstance().getTimeOfTheDay();
        final Integer dayOfTheWeek = TimeHelper.getInstance().getDayOfTheWeek(null);
        for (int i = 0; i < timeIntGrid.length; i++) {
            if (timeIntGrid[i] > now && !bossGrid[dayOfTheWeek][i].equals(EMPTY)) {
                if (i + position < timeIntGrid.length) {
                    return resolveBoss(now, i + position, dayOfTheWeek, false);
                } else {
                    return resolveBoss(now, i + position - timeIntGrid.length, TimeHelper.getInstance().getDayOfTheWeek(1), false);
                }
            }
        }
        return resolveBoss(now - 2400, position, TimeHelper.getInstance().getDayOfTheWeek(1), false);
    }

    public Boss getPreviousBoss() {
        final Integer now = TimeHelper.getInstance().getTimeOfTheDay();
        final Integer dayOfTheWeek = TimeHelper.getInstance().getDayOfTheWeek(null);
        for (int i = 0; i < timeIntGrid.length; i++) {
            if (timeIntGrid[i] > now || i + 1 == timeIntGrid.length) {
                if (i > 0 && bossGrid[dayOfTheWeek][i - 1].equals(EMPTY)) {
                    return resolveBoss(now, i - 2, dayOfTheWeek, true);
                } else if (i > 0) {
                    return resolveBoss(now, i - 1, dayOfTheWeek, true);
                } else {
                    int backPointer = 1;
                    while (bossGrid[TimeHelper.getInstance().getDayOfTheWeek(-1)][timeIntGrid.length - backPointer].equals(EMPTY)) {
                        backPointer++;
                    }
                    return resolveBoss(2400 + now, timeIntGrid.length - backPointer, TimeHelper.getInstance().getDayOfTheWeek(-1), true);
                }
            }
        }
        return resolveBoss(2400 + now, timeIntGrid.length - 1, dayOfTheWeek, true);
    }

    private Boss resolveBoss(Integer now, Integer pointer, Integer dayOfWeek, Boolean previousBoss) {
        Integer time = timeIntGrid[pointer];
        Integer timeDiff = TimeHelper.getInstance().getTimeDifference(time, now);
        String timeSpawn = timeGrid[pointer];
        String bossName = bossGrid[dayOfWeek][pointer];
        if (timeDiff < 0 && !previousBoss) {
            timeDiff = TimeHelper.getInstance().getTimeDifferenceNextDay(time, now);
        }
        return new Boss(bossName, timeSpawn, time);
    }

    public Boolean checkAlertAllowed(Boss nextBoss, BossSettings bossSettings, Integer soundsPlayed) {
        Integer limitMin = bossSettings.getAlertBefore() != null ? bossSettings.getAlertBefore() : 15;
        Integer alertTimes = bossSettings.getAlertTimes() != null ? bossSettings.getAlertTimes() : 3;

        //time to spawn
        if (nextBoss.getMinutesToSpawn() > limitMin || soundsPlayed >= alertTimes) {
            Log.d("BossHelper", "nog niet alerten: " + nextBoss.getMinutesToSpawn() + " " + limitMin);
            return false;
        }

        //check weekday
        int state = -1;
        switch (TimeHelper.getInstance().getDayOfTheWeek(null)) {
            case 0:
                state = bossSettings.getMonday() != null ? bossSettings.getMonday() : 1;
                break;
            case 1:
                state = bossSettings.getTuesday() != null ? bossSettings.getTuesday() : 1;
                break;
            case 2:
                state = bossSettings.getWednesday() != null ? bossSettings.getWednesday() : 1;
                break;
            case 3:
                state = bossSettings.getThursday() != null ? bossSettings.getThursday() : 1;
                break;
            case 4:
                state = bossSettings.getFriday() != null ? bossSettings.getFriday() : 1;
                break;
            case 5:
                state = bossSettings.getSaturday() != null ? bossSettings.getSaturday() : 1;
                break;
            case 6:
                state = bossSettings.getSunday() != null ? bossSettings.getSunday() : 1;
                break;
        }
        Log.d("BossHelper", "1 state: " + state);

        //disabled
        if (state == 3) {
            Log.d("BossHelper", "state = 3, nog niet alerten");
            return false;
        }

//        //check time
//        if (state == 2) {
//            Integer timeFrom = bossSettings.getTimeFrom() != null ? bossSettings.getTimeFrom() : 0;
//            int timeTo = bossSettings.getTimeTo() != null ? bossSettings.getTimeTo() : 0;
//            Integer timeOfTheDay = TimeHelper.getInstance().getTimeOfTheDay();
////            if (timeFrom < timeTo) {
//                //normal case
//            Log.d("BossHelper", "normal case. from:" + timeFrom + " to:" + timeTo + "  now:" + timeOfTheDay);
//
//            Log.d("BossHelper", "normal case. from:" + (timeOfTheDay > timeFrom) + " to:" + (timeOfTheDay < timeTo));
//
//            if (timeOfTheDay < timeFrom && timeOfTheDay > timeTo) {
//                    Log.d("BossHelper", "silent period. from:" + timeFrom + " to:" + timeTo + "  now:" + timeOfTheDay);
//                    return false;
////                }
////            } else if (timeFrom > timeTo) {
////                if (timeOfTheDay < timeFrom && timeOfTheDay > timeTo) {
////                    Log.d("BossHelper", "silent period 2. from:" + timeFrom + " to:" + timeTo + "  now:" + timeOfTheDay);
////                    return false;
////                }
//            }
//            //else, probably no time set, continue
//        }


        Log.d("BossHelper", "laatste checks");

        String[] bosses = nextBoss.getName().split("&");
        boolean enabled = false;
        for (String boss : bosses) {
            if (bossSettings.getEnabledBosses().contains(boss)) {
                enabled = true;
            }
        }
        Log.d("BossHelper", "enabled " + enabled);
        return enabled;
    }
}
