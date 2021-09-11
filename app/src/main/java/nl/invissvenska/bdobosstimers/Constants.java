package nl.invissvenska.bdobosstimers;

public final class Constants {

    public final static String KZARKA = "Kzarka";
    public final static String KARANDA = "Karanda";
    public final static String NOUVER = "Nouver";
    public final static String KUTUM = "Kutum";
    public final static String GARMOTH = "Garmoth";
    public final static String OFFIN = "Offin";
    public final static String VELL = "Vell";
    public final static String QUINT = "Quint";
    public final static String MURAKA = "Muraka";
    public final static String EMPTY = "";

    public interface ACTION {
        String STOP_FOREGROUND_ACTION = "nl.invissvenska.bdobosstimers.action.stopforeground";
        String START_FOREGROUND_ACTION = "nl.invissvenska.bdobosstimers.action.startforeground";
        String NOTIFICATION_CLICKED = "NOTIFICATION_CLICKED";
    }

    private Constants() {
        // Hide public constructor
    }
}
