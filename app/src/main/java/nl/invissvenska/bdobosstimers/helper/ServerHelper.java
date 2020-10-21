package nl.invissvenska.bdobosstimers.helper;

import nl.invissvenska.bdobosstimers.Server;

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

    private ServerHelper() {
    }

    public static ServerHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerHelper();
        }
        return INSTANCE;
    }

    private final Integer[] timeIntGridEU = {0, 300, 700, 1000, 1400, 1700, 2025, 2125, 2225};

    private final String[][] bossGridEU = {
            {KARANDA, KZARKA, KZARKA, OFFIN, KUTUM, NOUVER, KZARKA, EMPTY, KARANDA},
            {KUTUM, KZARKA, NOUVER, KUTUM, NOUVER, KARANDA, GARMOTH, EMPTY, KUTUM + "&" + KZARKA},
            {KARANDA, KZARKA, KARANDA, EMPTY, KUTUM + "&" + OFFIN, VELL, KARANDA + "&" + KZARKA, MURAKA + "&" + QUINT, NOUVER},
            {KUTUM, NOUVER, KUTUM, NOUVER, KZARKA, KUTUM, GARMOTH, EMPTY, KARANDA + "&" + KZARKA},
            {NOUVER, KARANDA, KUTUM, KARANDA, NOUVER, KZARKA, KUTUM + "&" + KZARKA, EMPTY, KARANDA},
            {OFFIN, NOUVER, KUTUM, NOUVER, MURAKA + "&" + QUINT, KARANDA + "&" + KZARKA, EMPTY, EMPTY, KUTUM + "&" + NOUVER},
            {KZARKA, KUTUM, NOUVER, KZARKA, VELL, GARMOTH, KZARKA + "&" + NOUVER, EMPTY, KUTUM + "&" + KARANDA}
    };

    private final Integer[] timeIntGridNA = {0, 325, 425, 525, 700, 1000, 1400, 1700, 2100};

    private final String[][] bossGridNA = {
            {GARMOTH, KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM, KARANDA, KZARKA, KZARKA, OFFIN, KUTUM},
            {NOUVER, KZARKA, EMPTY, KARANDA, KUTUM, KZARKA, NOUVER, KUTUM, NOUVER},
            {KARANDA, GARMOTH, EMPTY, KUTUM + "&" + KZARKA, KARANDA, EMPTY, KARANDA, NOUVER, KUTUM + "&" + OFFIN},
            {VELL, KARANDA + "&" + KZARKA, MURAKA + "&" + QUINT, NOUVER, KUTUM, KZARKA, KUTUM, NOUVER, KZARKA},
            {KUTUM, GARMOTH, EMPTY, KARANDA + "&" + KZARKA, NOUVER, KARANDA, KUTUM, KARANDA, NOUVER},
            {KZARKA, KUTUM + "&" + KZARKA, EMPTY, KARANDA, OFFIN, NOUVER, KUTUM, NOUVER, MURAKA + "&" + QUINT},
            {KARANDA + "&" + KZARKA, EMPTY, EMPTY, KUTUM + "&" + NOUVER, KZARKA, KUTUM, NOUVER, KZARKA, VELL}
    };

    private final Integer[] timeIntGridSEA = {300, 700, 800, 1200, 1600, 1750};

    private final String[][] bossGridSEA = {
            {KZARKA + "&" + NOUVER, KUTUM + "&" + NOUVER, EMPTY, KARANDA + "&" + KZARKA, OFFIN, NOUVER},
            {KARANDA + "&" + KUTUM, KUTUM + "&" + KZARKA, EMPTY, MURAKA + "&" + QUINT, GARMOTH, KZARKA + "&" + OFFIN},
            {KUTUM + "&" + NOUVER, KARANDA + "&" + KZARKA, EMPTY, KUTUM + "&" + NOUVER, VELL, KUTUM},
            {KARANDA + "&" + KZARKA, KUTUM + "&" + NOUVER, EMPTY, KARANDA + "&" + NOUVER, GARMOTH, NOUVER},
            {KUTUM + "&" + KZARKA, KARANDA + "&" + KZARKA, EMPTY, KUTUM + "&" + NOUVER, OFFIN, KARANDA},
            {KUTUM + "&" + KZARKA, KARANDA + "&" + NOUVER, GARMOTH, MURAKA + "&" + QUINT, EMPTY, KZARKA},
            {KARANDA + "&" + NOUVER, KARANDA + "&" + KUTUM, VELL, KARANDA + "&" + KZARKA, KUTUM + "&" + NOUVER, KUTUM}
    };

    public Integer[] getTimeIntGrid(Server server) {
        switch (server) {
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

    public String[][] getBossGrid(Server server) {
        switch (server) {
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
