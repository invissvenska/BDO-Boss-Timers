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

    private final Integer[] timeIntGridEU = {100, 400, 800, 1100, 1500, 1800, 2125, 2225, 2325};
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

    private final Integer[] timeIntGridSA = {250, 325, 500, 1400, 1900, 2100, 2300};
    private final String[][] bossGridSA = {
            {OFFIN, EMPTY, KARANDA + "&" + KZARKA, NOUVER, KUTUM + "&" + KZARKA, EMPTY, KUTUM + "&" + NOUVER},
            {GARMOTH, EMPTY, KUTUM, KZARKA, KUTUM + "&" + NOUVER, EMPTY, KARANDA + "&" + KZARKA},
            {OFFIN, EMPTY, KZARKA, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, MURAKA + "&" + QUINT},
            {GARMOTH, EMPTY, KARANDA + "&" + KUTUM, KARANDA + "&" + NOUVER, KUTUM + "&" + KZARKA, EMPTY, KUTUM + "&" + NOUVER},
            {EMPTY, VELL, KARANDA + "&" + OFFIN, NOUVER, KUTUM + "&" + KZARKA, EMPTY, KZARKA + "&" + NOUVER},
            {GARMOTH, EMPTY, KZARKA, KARANDA + "&" + KZARKA, KUTUM + "&" + NOUVER, EMPTY, MURAKA + "&" + QUINT},
            {EMPTY, EMPTY, NOUVER, KUTUM + "&" + NOUVER, KARANDA + "&" + KZARKA, VELL, KARANDA + "&" + NOUVER}
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

    private final Integer[] timeIntGridXboxNA = {0, 325, 400, 525, 600, 1400, 1800, 2000, 2100, 2200, 2300};
    private final String[][] bossGridXboxNA = {
            {KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, EMPTY, OFFIN},
            {KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, KARANDA + "&" + KUTUM, KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, EMPTY, EMPTY, KZARKA + "&" + NOUVER, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, MURAKA + "&" + QUINT, EMPTY, KARANDA + "&" + KUTUM, KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, EMPTY, EMPTY, KZARKA + "&" + NOUVER, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, OFFIN, EMPTY, KARANDA + "&" + KUTUM, EMPTY, KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM, MURAKA + "&" + QUINT, EMPTY},
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, KARANDA + "&" + KUTUM, EMPTY, EMPTY, KARANDA + "&" + KUTUM, OFFIN, KZARKA + "&" + NOUVER, EMPTY, EMPTY}
    };

    private final Integer[] timeIntGridXboxEU = {700, 1100, 1300, 1400, 1500, 1700, 2025, 2100, 2225, 2300};
    private final String[][] bossGridXboxEU = {
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, OFFIN, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, KARANDA + "&" + KUTUM},
            {KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY, EMPTY, KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, EMPTY, EMPTY, KZARKA + "&" + NOUVER},
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, MURAKA + "&" + QUINT, EMPTY, KARANDA + "&" + KUTUM},
            {KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY, EMPTY, KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, EMPTY, EMPTY, KZARKA + "&" + NOUVER},
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, OFFIN, EMPTY, KARANDA + "&" + KUTUM},
            {EMPTY, KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM, MURAKA + "&" + QUINT, KZARKA + "&" + NOUVER, EMPTY, EMPTY, KARANDA + "&" + KUTUM, EMPTY},
            {EMPTY, KARANDA + "&" + KUTUM, OFFIN, KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY}
    };

    private final Integer[] timeIntGridPs4NA = {0, 325, 400, 525, 600, 1400, 1800, 2000, 2100, 2200, 2300};
    private final String[][] bossGridPs4NA = {
            {KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, EMPTY, OFFIN},
            {KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, KARANDA + "&" + KUTUM, KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, EMPTY, EMPTY, KZARKA + "&" + NOUVER, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, MURAKA + "&" + QUINT, EMPTY, KARANDA + "&" + KUTUM, KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, EMPTY, EMPTY, KZARKA + "&" + NOUVER, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, OFFIN, EMPTY, KARANDA + "&" + KUTUM, EMPTY, KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM, MURAKA + "&" + QUINT, EMPTY},
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, KARANDA + "&" + KUTUM, EMPTY, EMPTY, KARANDA + "&" + KUTUM, OFFIN, KZARKA + "&" + NOUVER, EMPTY, EMPTY}
    };

    private final Integer[] timeIntGridPs4EU = {700, 1100, 1300, 1400, 1500, 1700, 2025, 2100, 2225, 2300};
    private final String[][] bossGridPs4EU = {
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, OFFIN, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, KARANDA + "&" + KUTUM},
            {KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY, EMPTY, KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, EMPTY, EMPTY, KZARKA + "&" + NOUVER},
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, MURAKA + "&" + QUINT, EMPTY, KARANDA + "&" + KUTUM},
            {KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY, EMPTY, KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, EMPTY, EMPTY, KZARKA + "&" + NOUVER},
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, OFFIN, EMPTY, KARANDA + "&" + KUTUM},
            {EMPTY, KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM, MURAKA + "&" + QUINT, KZARKA + "&" + NOUVER, EMPTY, EMPTY, KARANDA + "&" + KUTUM, EMPTY},
            {EMPTY, KARANDA + "&" + KUTUM, OFFIN, KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY}
    };

    private final Integer[] timeIntGridPs4ASIA = {200, 500, 700, 800, 900, 1000, 1100, 1450, 1500, 1550, 1600};
    private final String[][] bossGridPs4ASIA = {
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, KARANDA + "&" + KUTUM, OFFIN, KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY},
            {KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY, KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY},
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, KARANDA + "&" + KUTUM, EMPTY, KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, EMPTY, EMPTY, MURAKA + "&" + QUINT},
            {KARANDA + "&" + KUTUM, EMPTY, EMPTY, EMPTY, KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY},
            {KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, KARANDA + "&" + KUTUM, EMPTY, KZARKA + "&" + NOUVER, KARANDA + "&" + KUTUM, OFFIN, EMPTY, EMPTY},
            {KZARKA + "&" + NOUVER, EMPTY, KARANDA + "&" + KUTUM, MURAKA + "&" + QUINT, EMPTY, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, KARANDA + "&" + KUTUM, EMPTY},
            {KARANDA + "&" + KUTUM, OFFIN, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY, KARANDA + "&" + KUTUM, KZARKA + "&" + NOUVER, EMPTY, EMPTY, EMPTY}
    };

    public Integer[] getTimeIntGrid(Server server) {
        switch (server) {
            case EU:
                return timeIntGridEU;
            case NA:
                return timeIntGridNA;
            case SA:
                return timeIntGridSA;
            case SEA:
                return timeIntGridSEA;
            case XBOX_NA:
                return timeIntGridXboxNA;
            case XBOX_EU:
                return timeIntGridXboxEU;
            case PS4_NA:
                return timeIntGridPs4NA;
            case PS4_EU:
                return timeIntGridPs4EU;
            case PS4_ASIA:
                return timeIntGridPs4ASIA;
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
            case SA:
                return bossGridSA;
            case SEA:
                return bossGridSEA;
            case XBOX_NA:
                return bossGridXboxNA;
            case XBOX_EU:
                return bossGridXboxEU;
            case PS4_NA:
                return bossGridPs4NA;
            case PS4_EU:
                return bossGridPs4EU;
            case PS4_ASIA:
                return bossGridPs4ASIA;
            default:
                return bossGridEU;
        }
    }
}
