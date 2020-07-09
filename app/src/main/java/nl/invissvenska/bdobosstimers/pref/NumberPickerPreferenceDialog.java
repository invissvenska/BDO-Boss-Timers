package nl.invissvenska.bdobosstimers.pref;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.preference.PreferenceDialogFragmentCompat;

public class NumberPickerPreferenceDialog extends PreferenceDialogFragmentCompat {

    private static NumberPickerPreferenceDialog INSTANCE;
    private NumberPicker np;

    private NumberPickerPreferenceDialog(String key) {
        Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        this.setArguments(bundle);
    }

    public static NumberPickerPreferenceDialog getInstance(String key) {
        if (INSTANCE == null) {
            INSTANCE = new NumberPickerPreferenceDialog(key);
        }
        return INSTANCE;
    }

    @Override
    protected View onCreateDialogView(Context context) {
        np = new NumberPicker(context);
        np.setMinValue(NumberPickerPreference.MIN_VALUE);
        np.setMaxValue(NumberPickerPreference.MAX_VALUE);
        return np;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        np.setValue(((NumberPickerPreference) getPreference()).getPersistedInt());
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            np.clearFocus();
            int newValue = np.getValue();
            if (getPreference().callChangeListener(newValue)) {
                ((NumberPickerPreference) getPreference()).doPersistInt(newValue);
                getPreference().getSummary();
            }
        }
    }
}
