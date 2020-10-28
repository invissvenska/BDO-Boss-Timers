package nl.invissvenska.bdobosstimers.helper;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import nl.invissvenska.bdobosstimers.Server;
import nl.invissvenska.bdobosstimers.util.BossProperties;

//import static nl.invissvenska.bdobosstimers.Constants.GARMOTH;
//import static nl.invissvenska.bdobosstimers.Constants.KARANDA;
//import static nl.invissvenska.bdobosstimers.Constants.KUTUM;
//import static nl.invissvenska.bdobosstimers.Constants.KZARKA;
//import static nl.invissvenska.bdobosstimers.Constants.MURAKA;
//import static nl.invissvenska.bdobosstimers.Constants.NOUVER;
//import static nl.invissvenska.bdobosstimers.Constants.OFFIN;
//import static nl.invissvenska.bdobosstimers.Constants.QUINT;
//import static nl.invissvenska.bdobosstimers.Constants.VELL;

public class BossSettings implements Parcelable {

    private Boolean kzarka;
    private Boolean karanda;
    private Boolean nouver;
    private Boolean kutum;
    private Boolean garmoth;
    private Boolean offin;
    private Boolean vell;
    private Boolean quint;
    private Boolean muraka;
    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;
    private Integer saturday;
    private Integer sunday;
    private Integer timeFrom;
    private Integer timeTo;
    private Integer alertBefore;
    private Integer alertTimes;
    private Integer alertDelay;
    private Boolean vibration;
    private Server server;
    private Integer maxBosses;
    private Boolean silentPeriod;

    public BossSettings(Parcel parcel) {
        kzarka = parcel.readInt() == 1;
        karanda = parcel.readInt() == 1;
        nouver = parcel.readInt() == 1;
        kutum = parcel.readInt() == 1;
        garmoth = parcel.readInt() == 1;
        offin = parcel.readInt() == 1;
        vell = parcel.readInt() == 1;
        quint = parcel.readInt() == 1;
        muraka = parcel.readInt() == 1;
        monday = parcel.readInt();
        tuesday = parcel.readInt();
        wednesday = parcel.readInt();
        thursday = parcel.readInt();
        friday = parcel.readInt();
        saturday = parcel.readInt();
        sunday = parcel.readInt();
        timeFrom = parcel.readInt();
        timeTo = parcel.readInt();
        alertBefore = parcel.readInt();
        alertTimes = parcel.readInt();
        alertDelay = parcel.readInt();
        vibration = parcel.readInt() == 1;
        server = Server.valueOf(parcel.readString());
        maxBosses = parcel.readInt();
        silentPeriod = parcel.readInt() == 1;
    }

    public BossSettings(Boolean kzarka, Boolean karanda, Boolean nouver, Boolean kutum, Boolean garmoth,
                        Boolean offin, Boolean vell, Boolean quint, Boolean muraka, Integer monday,
                        Integer tuesday, Integer wednesday, Integer thursday, Integer friday, Integer saturday,
                        Integer sunday, Integer timeFrom, Integer timeTo, Integer alertBefore,
                        Integer alertTimes, Integer alertDelay, Boolean vibration, Server server, Integer maxBosses,
                        Boolean silentPeriod) {
        this.kzarka = kzarka;
        this.karanda = karanda;
        this.nouver = nouver;
        this.kutum = kutum;
        this.garmoth = garmoth;
        this.offin = offin;
        this.vell = vell;
        this.quint = quint;
        this.muraka = muraka;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.alertBefore = alertBefore;
        this.alertTimes = alertTimes;
        this.alertDelay = alertDelay;
        this.vibration = vibration;
        this.server = server;
        this.maxBosses = maxBosses;
        this.silentPeriod = silentPeriod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(kzarka ? 1 : 0);
        dest.writeInt(karanda ? 1 : 0);
        dest.writeInt(nouver ? 1 : 0);
        dest.writeInt(kutum ? 1 : 0);
        dest.writeInt(garmoth ? 1 : 0);
        dest.writeInt(offin ? 1 : 0);
        dest.writeInt(vell ? 1 : 0);
        dest.writeInt(quint ? 1 : 0);
        dest.writeInt(muraka ? 1 : 0);
        dest.writeInt(monday);
        dest.writeInt(tuesday);
        dest.writeInt(wednesday);
        dest.writeInt(thursday);
        dest.writeInt(friday);
        dest.writeInt(saturday);
        dest.writeInt(sunday);
        dest.writeInt(timeFrom);
        dest.writeInt(timeTo);
        dest.writeInt(alertBefore);
        dest.writeInt(alertTimes);
        dest.writeInt(alertDelay);
        dest.writeInt(vibration ? 1 : 0);
        dest.writeString(server.name());
        dest.writeInt(maxBosses);
        dest.writeInt(silentPeriod ? 1 : 0);
    }

    public static final Creator<BossSettings> CREATOR = new Creator<BossSettings>() {
        @Override
        public BossSettings createFromParcel(Parcel source) {
            return new BossSettings(source);
        }

        @Override
        public BossSettings[] newArray(int size) {
            return new BossSettings[size];
        }
    };

    public List<BossProperties> getEnabledBosses() {
        ArrayList<BossProperties> bosses = new ArrayList<>();
        if (kzarka) {
            bosses.add(BossProperties.KZARKA);
        }
        if (karanda) {
            bosses.add(BossProperties.KARANDA);
        }
        if (nouver) {
            bosses.add(BossProperties.NOUVER);
        }
        if (kutum) {
            bosses.add(BossProperties.KUTUM);
        }
        if (garmoth) {
            bosses.add(BossProperties.GARMOTH);
        }
        if (offin) {
            bosses.add(BossProperties.OFFIN);
        }
        if (vell) {
            bosses.add(BossProperties.VELL);
        }
        if (quint) {
            bosses.add(BossProperties.QUINT);
        }
        if (muraka) {
            bosses.add(BossProperties.MURAKA);
        }
        return bosses;
    }

    public Integer getMonday() {
        return monday;
    }

    public Integer getTuesday() {
        return tuesday;
    }

    public Integer getWednesday() {
        return wednesday;
    }

    public Integer getThursday() {
        return thursday;
    }

    public Integer getFriday() {
        return friday;
    }

    public Integer getSaturday() {
        return saturday;
    }

    public Integer getSunday() {
        return sunday;
    }

    public Integer getTimeAfter() {
        return timeFrom;
    }

    public Integer getTimeBefore() {
        return timeTo;
    }

    public Integer getAlertBefore() {
        return alertBefore;
    }

    public Integer getAlertTimes() {
        return alertTimes;
    }

    public Integer getAlertDelay() {
        return alertDelay;
    }

    public Boolean getVibration() {
        return vibration;
    }

    public Server getSelectedServer() {
        return server;
    }

    public Integer getMaxBosses() {
        return maxBosses;
    }

    public Boolean isSilentPeriod() {
        return silentPeriod;
    }
}
