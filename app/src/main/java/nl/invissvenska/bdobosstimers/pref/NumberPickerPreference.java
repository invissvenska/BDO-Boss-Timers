package nl.invissvenska.bdobosstimers.pref;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;

import nl.invissvenska.bdobosstimers.R;

public final class NumberPickerPreference extends DialogPreference {

    public static final Integer INITIAL_VALUE = 0;
    public static Integer MIN_VALUE = 0;
    public static Integer MAX_VALUE = 100;
    public static String UNIT_TEXT = "";

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public CharSequence getSummary() {
        return getPersistedInt(INITIAL_VALUE) + UNIT_TEXT;
    }

    public int getPersistedInt() {
        return super.getPersistedInt(INITIAL_VALUE);
    }

    public void doPersistInt(int value) {
        super.persistInt(value);
        notifyChanged();
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.numberPickerPreference);
        MIN_VALUE = ta.getInt(R.styleable.numberPickerPreference_numberPickerPreference_minValue, MIN_VALUE);
        MAX_VALUE = ta.getInt(R.styleable.numberPickerPreference_numberPickerPreference_maxValue, MAX_VALUE);
        if (ta.getString(R.styleable.numberPickerPreference_numberPickerPreference_unitText) != null) {
            UNIT_TEXT = ta.getString(R.styleable.numberPickerPreference_numberPickerPreference_unitText);
        }
        ta.recycle();
    }
}
