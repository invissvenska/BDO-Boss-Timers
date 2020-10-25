package nl.invissvenska.bdobosstimers.helper;

import java.util.List;

import nl.invissvenska.bdobosstimers.Server;
import nl.invissvenska.bdobosstimers.util.Boss;
import timber.log.Timber;

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

    public List<Boss> getNextBosses(Server server, Integer addDays, List<Boss> bosses, Integer max) {
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
            return getNextBosses(server, addDays + 1, bosses, max);
        }
        return bosses;
    }

    public Boss getPreviousBoss(Server server, Integer addDays) {
        Boss boss = null;
        final Integer now = TimeHelper.getInstance().getTimeOfTheDay();
        final Integer dayOfTheWeek = TimeHelper.getInstance().getDayOfTheWeek(addDays);
        for (int i = ServerHelper.getInstance().getTimeIntGrid(server).length - 1; i >= 0; i--) {
            // loopt over the timespans
            if ((addDays <= -1 || (ServerHelper.getInstance().getTimeIntGrid(server)[i] < now)) && !ServerHelper.getInstance().getBossGrid(server)[dayOfTheWeek][i].equals(EMPTY)) {
                //check if timespan is greater (future) then current time
                boss = resolveBoss(i, dayOfTheWeek, server);
                break;
            }
        }
        if (boss == null) {
            return getPreviousBoss(server, addDays - 1);
        }
        return boss;
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
            Timber.d("Don't alert yet, minutes till spawn: %d, minutes to alert ahead: %d", nextBoss.getMinutesToSpawn(), limitMin);
            return false;
        }

        //silent period
        if (bossSettings.isSilentPeriod()) {
            Integer now = TimeHelper.getInstance().getTimeOfTheDayWithoutTimeZoneConversion();
            if (now >= bossSettings.getTimeAfter() || now < bossSettings.getTimeBefore()) {
                Timber.d("Don't alert, we are in silent period");
                return false;
            }
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

        //state 3 means today is 'off'
        if (state == 3) {
            Timber.d("Today is not enabled in settings. So we don't alert anything today");
            return false;
        }
        Timber.d("Today is enabled in settings. We can alert something.");

        String[] bosses = nextBoss.getName().split("&");
        boolean enabled = false;
        for (String boss : bosses) {
            if (bossSettings.getEnabledBosses().contains(boss)) {
                enabled = true;
                Timber.d("Boss: %s found in settings, we will alert!", boss);
            }
        }
        return enabled;
    }
}
