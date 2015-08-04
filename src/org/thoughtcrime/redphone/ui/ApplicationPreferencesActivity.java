/*
 * Copyright (C) 2011 Whisper Systems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.thoughtcrime.redphone.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;

import org.thoughtcrime.redphone.R;
import org.thoughtcrime.redphone.Release;

/**
 * Preferences menu Activity.
 * <p/>
 * Also provides methods for setting and getting application preferences.
 *
 * @author Stuart O. Anderson
 */
//TODO(Stuart Anderson): Consider splitting this into an Activity and a utility class
public class ApplicationPreferencesActivity extends PreferenceActivity {

    private AppCompatDelegate myDelegate;

    public static final String LOOPBACK_MODE_PREF = "pref_loopback";
    public static final String OPPORTUNISTIC_UPGRADE_PREF = "pref_prompt_upgrade";
    public static final String BLUETOOTH_ENABLED = "pref_bluetooth_enabled";


    private AppCompatDelegate getDelegate() {
        if (myDelegate == null) {
            myDelegate = AppCompatDelegate.create(this, null);
        }
        return myDelegate;
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    @Override
    protected void onCreate(Bundle icicle) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(icicle);
        super.onCreate(icicle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getFragmentManager().beginTransaction().replace(android.R.id.content,
                    new newPreferenceFragment()).commit();
        } else {
            if (Release.DEBUG) {
                addPreferencesFromResource(R.xml.debug);
            } else {
                addPreferencesFromResource(R.xml.preferences);
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.ApplicationPreferencesActivity_redphone_settings);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean getPromptUpgradePreference(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(OPPORTUNISTIC_UPGRADE_PREF, true);
    }

    public static boolean getLoopbackEnabled(Context context) {
        return Release.DEBUG &&
                PreferenceManager
                        .getDefaultSharedPreferences(context).getBoolean(LOOPBACK_MODE_PREF, false);
    }

    public static boolean getBluetoothEnabled(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(BLUETOOTH_ENABLED, false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class newPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (Release.DEBUG) {
                addPreferencesFromResource(R.xml.debug);
            } else {
                addPreferencesFromResource(R.xml.preferences);
            }
        }
    }
}
