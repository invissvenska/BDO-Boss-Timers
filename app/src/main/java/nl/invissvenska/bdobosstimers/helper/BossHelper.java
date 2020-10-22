package nl.invissvenska.bdobosstimers.helper;

import android.util.Log;

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

    public Boss getNextBoss(Integer position, Server server) {
        final Integer now = TimeHelper.getInstance().getTimeOfTheDay();
        final Integer dayOfTheWeek = TimeHelper.getInstance().getDayOfTheWeek(null);
        for (int i = 0; i < ServerHelper.getInstance().getTimeIntGrid(server).length; i++) {
            if (ServerHelper.getInstance().getTimeIntGrid(server)[i] > now) {
                if (i + position < ServerHelper.getInstance().getTimeIntGrid(server).length) {
                    return resolveBoss(i + position, dayOfTheWeek, server);
                } else {
                    return resolveBoss(i + position - ServerHelper.getInstance().getTimeIntGrid(server).length, TimeHelper.getInstance().getDayOfTheWeek(1), server);
                }
            }
        }
        return resolveBoss(position, TimeHelper.getInstance().getDayOfTheWeek(1), server);
    }

    public Boss getPreviousBoss(Server server) {
        final Integer now = TimeHelper.getInstance().getTimeOfTheDay();
        final Integer dayOfTheWeek = TimeHelper.getInstance().getDayOfTheWeek(null);
        for (int i = 0; i < ServerHelper.getInstance().getTimeIntGrid(server).length; i++) {
            if (ServerHelper.getInstance().getTimeIntGrid(server)[i] > now || i + 1 == ServerHelper.getInstance().getTimeIntGrid(server).length) {
                if (i > 0 && ServerHelper.getInstance().getBossGrid(server)[dayOfTheWeek][i - 1].equals(EMPTY)) {
                    return resolveBoss(i - 2, dayOfTheWeek, server);
                } else if (i > 0) {
                    return resolveBoss(i - 1, dayOfTheWeek, server);
                } else {
                    int backPointer = 1;
                    while (ServerHelper.getInstance().getBossGrid(server)[TimeHelper.getInstance().getDayOfTheWeek(-1)][ServerHelper.getInstance().getTimeIntGrid(server).length - backPointer].equals(EMPTY)) {
                        backPointer++;
                    }
                    return resolveBoss(ServerHelper.getInstance().getTimeIntGrid(server).length - backPointer, TimeHelper.getInstance().getDayOfTheWeek(-1), server);
                }
            }
        }
        return resolveBoss(ServerHelper.getInstance().getTimeIntGrid(server).length - 1, dayOfTheWeek, server);
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
