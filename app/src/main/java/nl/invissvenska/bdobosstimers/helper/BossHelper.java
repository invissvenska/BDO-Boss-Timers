package nl.invissvenska.bdobosstimers.helper;

import android.util.Log;

import java.util.List;

import nl.invissvenska.bdobosstimers.Server;
import nl.invissvenska.bdobosstimers.util.Boss;

import static nl.invissvenska.bdobosstimers.Constants.EMPTY;

public class BossHelper {

    private static BossHelper INSTANCE = null;

    private BossHelper() {
    }

    public static BossHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BossHelper();
        }
        return INSTANCE;
    }

    public List<Boss> getBoss(Server server, Integer addDays, List<Boss> bosses, Integer max) {
        final Integer now = TimeHelper.getInstance().getTimeOfTheDay();
        final Integer dayOfTheWeek = TimeHelper.getInstance().getDayOfTheWeek(addDays);
        for (int i = 0; i < ServerHelper.getInstance().getTimeIntGrid(server).length; i++) {
            // loopt over the timespans
            if ((addDays >= 1 || (ServerHelper.getInstance().getTimeIntGrid(server)[i] > now)) && !ServerHelper.getInstance().getBossGrid(server)[dayOfTheWeek][i].equals(EMPTY)) {
                //check if timespan is greater (future) then current time
                bosses.add(resolveBoss(i, dayOfTheWeek, server));
            }
            if (bosses.size() >= max) {
                break;
            }
        }
        if (bosses.size() < max) {
            return getBoss(server, addDays + 1, bosses, max);
        }
        return bosses;
    }

    public  List<Boss> getPrevBoss(Server server, Integer addDays, List<Boss> bosses, Integer max) {
        final Integer now = TimeHelper.getInstance().getTimeOfTheDay();
        final Integer dayOfTheWeek = TimeHelper.getInstance().getDayOfTheWeek(addDays);
        for (int i = ServerHelper.getInstance().getTimeIntGrid(server).length-1; i >= 0; i--) {
            // loopt over the timespans
            if ((addDays <= -1 || (ServerHelper.getInstance().getTimeIntGrid(server)[i] < now)) && !ServerHelper.getInstance().getBossGrid(server)[dayOfTheWeek][i].equals(EMPTY)) {
                //check if timespan is greater (future) then current time
                bosses.add(resolveBoss(i, dayOfTheWeek, server));
            }
            if (bosses.size() >= max) {
                break;
            }
        }
        if (bosses.size() < max) {
            return getPrevBoss(server, addDays - 1, bosses, max);
        }
        return bosses;
    }

    private Boss resolveBoss(Integer pointer, Integer dayOfWeek, Server server) {
        try {
            Integer time = ServerHelper.getInstance().getTimeIntGrid(server)[pointer];
            String bossName = ServerHelper.getInstance().getBossGrid(server)[dayOfWeek][pointer];
            return new Boss(bossName, time);
        } catch (Exception e) {
            return new Boss(EMPTY, 0);
        }
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
