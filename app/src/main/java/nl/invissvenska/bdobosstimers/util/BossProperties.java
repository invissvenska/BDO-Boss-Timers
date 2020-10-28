package nl.invissvenska.bdobosstimers.util;

import nl.invissvenska.bdobosstimers.R;

public enum BossProperties {
    GARMOTH("Garmoth", R.drawable.garmoth_big),
    KARANDA("Karanda", R.drawable.karanda_big),
    KZARKA("Kzarka", R.drawable.kzarka_big),
    KUTUM("Kutum", R.drawable.kutum_big),
    OFFIN("Offin", R.drawable.offin_big),
    NOUVER("Nouver", R.drawable.nouver_big),
    QUINT("Quint", R.drawable.quint_big),
    MURAKA("Muraka", R.drawable.muraka_big),
    VELL("Vell", R.drawable.vell_big),
    EMPTY("", 0);

    private String name;
    private Integer image;

    BossProperties(String name, Integer image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Integer getImage() {
        return image;
    }
}
