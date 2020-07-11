package nl.invissvenska.bdobosstimers.util;

import java.util.HashMap;
import java.util.Map;

import nl.invissvenska.bdobosstimers.R;

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
    private Integer minutesToSpawn;
    private Integer bossOneImageResource;
    private Integer bossTwoImageResource;

    public Boss(String name, String timeSpawn, Integer minutesToSpawn) {
        this.name = name;
        this.timeSpawn = timeSpawn;
        this.minutesToSpawn = minutesToSpawn;
        Map<String, Integer> imageMap = new HashMap<String, Integer>() {{
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