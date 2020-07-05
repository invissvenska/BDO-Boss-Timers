package nl.invissvenska.bdobosstimers.helper;

import java.util.HashMap;
import java.util.Map;

import nl.invissvenska.bdobosstimers.R;

import static nl.invissvenska.bdobosstimers.function.Constants.*;

public class BossHelper {

    private static BossHelper INSTANCE = null;
    private final String[] timeGrid = {"00:15", "02:00", "05:00", "09:00", "12:00", "16:00", "19:00", "22:15", "23:15"};
    private final Integer[] timeIntGrid = {25, 200, 500, 900, 1200, 1600, 1900, 2225, 2325};

    private final String[][] bossGrid = {
            {KARANDA + "&" + KUTUM, KARANDA, KZARKA, OFFIN, KUTUM, NOUVER, KZARKA, EMPTY},
            {KARANDA, KUTUM, KZARKA, NOUVER, KUTUM, NOUVER, KARANDA, GARMOTH, EMPTY},
            {KUTUM + "&" + KZARKA, KARANDA, KZARKA, KARANDA, EMPTY, KUTUM + "&" + OFFIN, VELL, KARANDA + "&" + KZARKA, MURAKA + "&" + QUINT},
            {NOUVER, KUTUM, NOUVER, KUTUM, NOUVER, KZARKA, KUTUM, GARMOTH, EMPTY},
            {KARANDA + "&" + KZARKA, NOUVER, KARANDA, KUTUM, KARANDA, NOUVER, KZARKA, KUTUM + "&" + KZARKA, EMPTY},
            {KARANDA, OFFIN, NOUVER, KUTUM, NOUVER, MURAKA + "&" + QUINT, KARANDA + "&" + KZARKA, EMPTY, EMPTY},
            {KUTUM + "&" + NOUVER, KZARKA, KUTUM, NOUVER, KZARKA, VELL, GARMOTH, KZARKA + "&" + NOUVER, EMPTY}
    };

    private final Map<String, Integer> imageMap = new HashMap<String, Integer>() {{
        put(GARMOTH, R.drawable.garmoth_big);
        put(KARANDA, R.drawable.karanda_big);
        put(KZARKA, R.drawable.kzarka_big);
        put(KUTUM, R.drawable.kutum_big);
        put(OFFIN, R.drawable.offin_big);
        put(NOUVER, R.drawable.nouver_big);
        put(QUINT, R.drawable.quint_big);
        put(MURAKA, R.drawable.muraka_big);
        put(VELL, R.drawable.vell_big);
    }};

    private BossHelper() {
    }

    public static BossHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BossHelper();
        }
        return INSTANCE;
    }

    public Boss getNextBoss() {
        final Integer now = TimeHelper.getInstance().getTimeOfTheDay();
        final Integer dayOfTheWeek = TimeHelper.getInstance().getDayOfTheWeek(null);
        for (int i = 0; i < timeIntGrid.length; i++) {
            if (timeIntGrid[i] > now && !bossGrid[dayOfTheWeek][i].equals(EMPTY)) {
                return resolveBoss(now, i, dayOfTheWeek);
            }
        }
        return resolveBoss(now - 2400, 0, TimeHelper.getInstance().getDayOfTheWeek(1));
    }

    public Boss getPreviousBoss() {
        final Integer now = TimeHelper.getInstance().getTimeOfTheDay();
        final Integer dayOfTheWeek = TimeHelper.getInstance().getDayOfTheWeek(null);
        for (int i = 0; i < timeIntGrid.length; i++) {
            if (timeIntGrid[i] > now || i + 1 == timeIntGrid.length) {
                if (i > 0 && bossGrid[dayOfTheWeek][i - 1].equals(EMPTY)) {
                    return resolveBoss(now, i - 2, dayOfTheWeek);
                } else if (i > 0) {
                    return resolveBoss(now, i - 1, dayOfTheWeek);
                } else {
                    int backPointer = 1;
                    while (bossGrid[TimeHelper.getInstance().getDayOfTheWeek(-1)][timeIntGrid.length - backPointer].equals(EMPTY)) {
                        backPointer++;
                    }
                    return resolveBoss(2400 + now, timeIntGrid.length - backPointer, TimeHelper.getInstance().getDayOfTheWeek(-1));
                }
            }
        }
        return resolveBoss(2400 + now, timeIntGrid.length - 1, dayOfTheWeek);
    }

    private Boss resolveBoss(Integer now, Integer pointer, Integer dayOfWeek) {
        Integer time = timeIntGrid[pointer];
        Integer timeDiff = TimeHelper.getInstance().getTimeDifference(time, now);
        String timeSpawn = timeGrid[pointer];
        String bossName = bossGrid[dayOfWeek][pointer];
        return new Boss(bossName, timeSpawn, timeDiff);
    }


    public Boolean checkAlertAllowed(Boss nextBoss, BossSettings bossSettings, Integer soundsPlayed) {
        Integer limitMin = bossSettings.getAlertBefore() != null ? bossSettings.getAlertBefore() : 15;
        Integer alertTimes = bossSettings.getAlertTimes() != null ? bossSettings.getAlertTimes() : 3;

        //time to spawn
        if (nextBoss.minutesToSpawn > limitMin || soundsPlayed >= alertTimes) {
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

        //disabled
        if (state == 3) {
            return false;
        }

        //check time
        if (state == 2) {
            Integer timeFrom = bossSettings.getTimeFrom() != null ? bossSettings.getTimeFrom() : 0;
            int timeTo = bossSettings.getTimeTo() != null ? bossSettings.getTimeTo() : 0;
            Integer timeOfTheDay = TimeHelper.getInstance().getTimeOfTheDay();
            if (timeFrom < timeTo) {
                //normal case
                if (timeOfTheDay > timeFrom && timeOfTheDay < timeTo) {
                    return false;
                }
            } else if (timeFrom > timeTo) {
                if (timeOfTheDay < timeFrom && timeOfTheDay > timeTo) {
                    return false;
                }
            }
            //else, probably no time set, continue
        }

        String[] bosses = nextBoss.getName().split("&");
        boolean enabled = false;
        for (String boss : bosses) {
            if (bossSettings.getEnabledBosses().contains(boss)) {
                enabled = true;
            }
        }
        return enabled;
    }


    public class Boss {
        private String name;
        private String timeSpawn;
        private Integer minutesToSpawn;
        private Integer bossOneImageResource;
        private Integer bossTwoImageResource;

        public Boss(String name, String timeSpawn, Integer minutesToSpawn) {
            this.name = name;
            this.timeSpawn = timeSpawn;
            this.minutesToSpawn = minutesToSpawn;
            if (name.contains("&")) {
                String[] names = name.split(("&"));
                bossOneImageResource = imageMap.get(names[0]);
                bossTwoImageResource = imageMap.get(names[1]);
            } else {
                bossOneImageResource = imageMap.get(name);
            }
        }

        public String getName() {
            return this.name;
        }

        public String getTimeSpawn() {
            return this.timeSpawn;
        }

        public Integer getMinutesToSpawn() {
            return this.minutesToSpawn;
        }

        public Integer getBossOneImageResource() {
            return bossOneImageResource;
        }

        public Integer getBossTwoImageResource() {
            return bossTwoImageResource;
        }
    }

}
