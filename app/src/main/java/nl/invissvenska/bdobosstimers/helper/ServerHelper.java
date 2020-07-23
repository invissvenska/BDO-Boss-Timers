package nl.invissvenska.bdobosstimers.helper;

import nl.invissvenska.bdobosstimers.SERVER;

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

public class ServerHelper {
    private static ServerHelper INSTANCE = null;
    private static SERVER server;

    private ServerHelper() {
    }

    public static ServerHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerHelper();
        }
        return INSTANCE;
    }

    private final String[] timeGridEU = {"00:15", "02:00", "05:00", "09:00", "12:00", "16:00", "19:00", "22:15", "23:15"};
    private final Integer[] timeIntGridEU = {25, 200, 500, 900, 1200, 1600, 1900, 2225, 2325};

    private final String[][] bossGridEU = {
            {KARANDA + "&" + KUTUM, KARANDA, KZARKA, KZARKA, OFFIN, KUTUM, NOUVER, KZARKA, EMPTY},
            {KARANDA, KUTUM, KZARKA, NOUVER, KUTUM, NOUVER, KARANDA, GARMOTH, EMPTY},
            {KUTUM + "&" + KZARKA, KARANDA, KZARKA, KARANDA, EMPTY, KUTUM + "&" + OFFIN, VELL, KARANDA + "&" + KZARKA, MURAKA + "&" + QUINT},
            {NOUVER, KUTUM, NOUVER, KUTUM, NOUVER, KZARKA, KUTUM, GARMOTH, EMPTY},
            {KARANDA + "&" + KZARKA, NOUVER, KARANDA, KUTUM, KARANDA, NOUVER, KZARKA, KUTUM + "&" + KZARKA, EMPTY},
            {KARANDA, OFFIN, NOUVER, KUTUM, NOUVER, MURAKA + "&" + QUINT, KARANDA + "&" + KZARKA, EMPTY, EMPTY},
            {KUTUM + "&" + NOUVER, KZARKA, KUTUM, NOUVER, KZARKA, VELL, GARMOTH, KZARKA + "&" + NOUVER, EMPTY}
    };

    private final String[] timeGridNA = {"00:00", "03:00", "07:00", "10:00", "14:00", "17:00", "20:15", "21:15", "22:15"};
    private final Integer[] timeIntGridNA = {0, 300, 700, 1000, 1400, 1700, 2025, 2125, 2225};

    private final String[][] bossGridNA = {
            {KARANDA, KZARKA, KZARKA, OFFIN, KUTUM, NOUVER, KZARKA, EMPTY, KARANDA},
            {KUTUM, KZARKA, NOUVER, KUTUM, NOUVER, KARANDA, GARMOTH, EMPTY, KUTUM + "&" + KZARKA},
            {KARANDA, EMPTY, KARANDA, NOUVER, KUTUM + "&" + OFFIN, VELL, KARANDA + "&" + KZARKA, MURAKA + "&" + QUINT, NOUVER},
            {KUTUM, KZARKA, KUTUM, NOUVER, KZARKA, KUTUM, GARMOTH, EMPTY, KARANDA + "&" + KZARKA},
            {NOUVER, KARANDA, KUTUM, KARANDA, NOUVER, KZARKA, KUTUM + "&" + KZARKA, EMPTY, KARANDA},
            {OFFIN, NOUVER, KUTUM, NOUVER, MURAKA + "&" + QUINT, KARANDA + "&" + KZARKA, EMPTY, EMPTY, KUTUM + "&" + NOUVER},
            {KZARKA, KUTUM, NOUVER, KZARKA, VELL, GARMOTH, KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM}
    };

    private final String[] timeGridSEA = {"00:00", "01:30", "11:00", "15:00", "16:00", "20:00"};
    private final Integer[] timeIntGridSEA = {0, 150, 1100, 1500, 1600, 2000};

    private final String[][] bossGridSEA = {
            {KUTUM + "&" + NOUVER, KUTUM, KZARKA + "&" + NOUVER, KUTUM + "&" + NOUVER, EMPTY, KARANDA + "&" + KZARKA},
            {OFFIN, NOUVER, KARANDA + "&" + KUTUM, KUTUM + "&" + KZARKA, EMPTY, MURAKA + "&" + QUINT},
            {GARMOTH, KZARKA + "&" + OFFIN, KUTUM + "&" + NOUVER, KARANDA + "&" + KZARKA, EMPTY, KUTUM + "&" + NOUVER},
            {VELL, KUTUM, KARANDA + "&" + KZARKA, KUTUM + "&" + NOUVER, EMPTY, KARANDA + "&" + NOUVER},
            {GARMOTH, NOUVER, KUTUM + "&" + KZARKA, KARANDA + "&" + KZARKA, EMPTY, KUTUM + "&" + NOUVER},
            {OFFIN, KARANDA, KUTUM + "&" + KZARKA, KARANDA + "&" + NOUVER, GARMOTH, MURAKA + "&" + QUINT},
            {EMPTY, KZARKA, KARANDA + "&" + NOUVER, KARANDA + "&" + KUTUM, VELL, KARANDA + "&" + KZARKA}
    };

    public String[] getTimeGrid(SERVER server) {
        this.server = server;
        switch (this.server) {
            case EU:
                return timeGridEU;
            case NA:
                return timeGridNA;
            case SEA:
                return timeGridSEA;
            default:
                return timeGridEU;
        }
    }

    public Integer[] getTimeIntGrid(SERVER server) {
        this.server = server;
        switch (this.server) {
            case EU:
                return timeIntGridEU;
            case NA:
                return timeIntGridNA;
            case SEA:
                return timeIntGridSEA;
            default:
                return timeIntGridEU;
        }
    }

    public String[][] getBossGrid(SERVER server) {
        this.server = server;
        switch (this.server) {
            case EU:
                return bossGridEU;
            case NA:
                return bossGridNA;
            case SEA:
                return bossGridSEA;
            default:
                return bossGridEU;
        }
    }
}
