package com.example.kniffelblock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkey) {
        setPreferencesFromResource(R.xml.preferences, rootkey);
        final CheckBoxPreference warnings = (CheckBoxPreference) findPreference("check_warning");
        CheckBoxPreference design = (CheckBoxPreference) findPreference("check_design");
        design.setDefaultValue(false);
        Preference delete = findPreference("button_delete");

        //design toggle
        design.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(newValue.toString().equals("true")) {
                    Log.d(TAG, "onPreferenceChange: night mode activated");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    Log.d(TAG, "onPreferenceChange: day mode activiated");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;
            }
        });

        //warning toggle
        warnings.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if((Boolean) newValue) {
                    Log.d(TAG, "onPreferenceChange: warnings enabled");
                    warnings.setChecked(true);
                }
                else {
                    Log.d(TAG, "onPreferenceChange: warnings disabled");
                    warnings.setChecked(false);

                }
                return true;
            }
        });

        delete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(warnings.isChecked()) {
                    final ButtonDialogs open = new ButtonDialogs(getContext());
                    AlertDialog dialog = open.getDeleteWarning(getContext());
                    dialog.show();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if(open.getBoolean()) {
                                new DeleteAsyncTask(getContext()).execute();
                                Toast.makeText(getContext(), "Spieldaten gelöscht", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    new DeleteAsyncTask(getContext()).execute();
                    Toast.makeText(getContext(), "Spieldaten gelöscht", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}
