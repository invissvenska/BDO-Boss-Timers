package nl.invissvenska.bdobosstimers.preference.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.preference.Preference;

public class SavedState extends Preference.BaseSavedState {

    public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
        public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
        }
        public SavedState[] newArray(int size) {
            return new SavedState[size];
        }
    };
    String text;

    SavedState(Parcel source) {
        super(source);
        text = source.readString();
    }

    SavedState(Parcelable superState) {
        super(superState);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(text);
    }
}
