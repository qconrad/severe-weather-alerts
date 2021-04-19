package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.severeweatheralerts.FirstRunActivity;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.R;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settings_activity);
    if (savedInstanceState == null) {
      getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.settings, new SettingsFragment())
              .commit();
    }
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  public static class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      setPreferencesFromResource(R.xml.root_preferences, rootKey);
      if (findPreference("usefixed").isEnabled()) {
        findPreference("fixedloc").setSummary(LocationsDao.getName(0));
      }
      createClickListeners();
    }

    private void createClickListeners() {
      createChannelListener();
      createSeverityPreferencesListener();
    }

    private void createSeverityPreferencesListener() {
      Preference severityPrefs = findPreference("severepref");
      if (severityPrefs != null) {
        severityPrefs.setOnPreferenceClickListener(preference -> {
          showSeverityPreferences();
          return true;
        });
      }
    }

    private void createChannelListener() {
      Preference channels = findPreference("channels");
      Preference fixedloc = findPreference("fixedloc");
      if (channels != null) {
        channels.setOnPreferenceClickListener(preference -> {
          showNotificationChannels();
          return true;
        });
      }
      if (fixedloc != null) {
        fixedloc.setOnPreferenceClickListener(preference -> {
          startActivityForResult(new Intent(getActivity(), LocationPickerActivity.class), 0);
          return true;
        });
      }
    }

    private void showSeverityPreferences() {
      Intent alertListIntent = new Intent(getActivity(), ChannelPreferencesActivity.class);
      startActivity(alertListIntent);
    }

    private void showNotificationChannels() {
      Intent intent = new Intent();
      intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
      intent.putExtra("app_package", getContext().getPackageName());
      intent.putExtra("app_uid", getContext().getApplicationInfo().uid);
      intent.putExtra("android.provider.extra.APP_PACKAGE", getContext().getPackageName());
      startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (resultCode == Activity.RESULT_OK) {
        Bundle extras = data.getExtras();
        LocationsDao.setDefaultLocation(extras.getString("name"), extras.getDouble("lat"), extras.getDouble("lon"));
        startActivity(new Intent(getActivity(), FetchingAlertDataActivity.class));
      }
    }
  }
}