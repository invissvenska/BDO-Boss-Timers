package nl.invissvenska.bdobosstimers;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import nl.invissvenska.bdobosstimers.preference.util.NumberDialogPreference;
import nl.invissvenska.bdobosstimers.preference.util.NumberPickerPreferenceDialogFragment;
import nl.invissvenska.bdobosstimers.preference.util.TimeDialogPreference;
import nl.invissvenska.bdobosstimers.preference.util.TimePreferenceDialogFragment;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String DIALOG_FRAGMENT_TAG = "CustomPreferenceDialog";

    public SettingsFragment() {
        //keep empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment dialogFragment = null;
        if (preference instanceof NumberDialogPreference) {
            NumberDialogPreference dialogPreference = (NumberDialogPreference) preference;
            dialogFragment = NumberPickerPreferenceDialogFragment
                    .newInstance(
                            dialogPreference.getKey(),
                            dialogPreference.getMinValue(),
                            dialogPreference.getMaxValue(),
                            dialogPreference.getUnitText()
                    );
        } else if (preference instanceof TimeDialogPreference) {
            TimeDialogPreference dialogPreference = (TimeDialogPreference) preference;
            dialogFragment = TimePreferenceDialogFragment
                    .newInstance(
                            dialogPreference.getKey(),
                            dialogPreference.isForce12HourPicker(),
                            dialogPreference.isForce24HourPicker(),
                            dialogPreference.getCustomFormat()
                    );
        }
        if (dialogFragment != null) {
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getParentFragmentManager(), DIALOG_FRAGMENT_TAG);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }
}
