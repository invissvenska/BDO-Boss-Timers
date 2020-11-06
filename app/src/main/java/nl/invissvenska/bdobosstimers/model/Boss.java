package nl.invissvenska.bdobosstimers.model;

import java.util.HashMap;
import java.util.Map;

import nl.invissvenska.bdobosstimers.R;
import nl.invissvenska.bdobosstimers.util.TimeHelper;

import static nl.invissvenska.bdobosstimers.Constants.GARMOTH;
import static nl.invissvenska.bdobosstimers.Constants.KARANDA;
import static nl.invissvenska.bdobosstimers.Constants.KUTUM;
import static nl.invissvenska.bdobosstimers.Constants.KZARKA;
import static nl.invissvenska.bdobosstimers.Constants.MURAKA;
import static nl.invissvenska.bdobosstimers.Constants.NOUVER;
import static nl.invissvenska.bdobosstimers.Constants.OFFIN;
import static nl.invissvenska.bdobosstimers.Constants.QUINT;
import static nl.invissvenska.bdobosstimers.Constants.VELL;

public class Boss {
    private String name;
    private String timeSpawn;
    private Integer timeIntSpawn;

    private Integer bossOneImageResource;
    private Integer bossTwoImageResource;

    public Boss(String name, Integer timeIntSpawn) {
        this.name = name;
        this.timeSpawn = TimeHelper.getInstance().hundredToSixtyFormat(timeIntSpawn);
        this.timeIntSpawn = timeIntSpawn;
        Map<String, Integer> imageMap = new HashMap<String, Integer>() {{
            put(GARMOTH, R.drawable.garmoth);
            put(KARANDA, R.drawable.karanda);
            put(KZARKA, R.drawable.kzarka);
            put(KUTUM, R.drawable.kutum);
            put(OFFIN, R.drawable.offin);
            put(NOUVER, R.drawable.nouver);
            put(QUINT, R.drawable.quint);
            put(MURAKA, R.drawable.muraka);
            put(VELL, R.drawable.vell);
        }};
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
        final Integer now = TimeHelper.getInstance().getTimeOfTheDay();
        Integer timeDiff = TimeHelper.getInstance().getTimeDifference(timeIntSpawn, now);
        if (timeDiff < 0) {
            return TimeHelper.getInstance().getTimeDifferenceNextDay(timeIntSpawn, now);
        }
        return timeDiff;
    }

    public Integer getBossOneImageResource() {
        return bossOneImageResource;
    }

    public Integer getBossTwoImageResource() {
        return bossTwoImageResource;
    }
}