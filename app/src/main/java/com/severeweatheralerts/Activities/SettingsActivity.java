package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.R;

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
      createdUseFixedListener();
      createdFixedLocationListener();
      createSeverityPreferencesListener();
    }

    private void createdUseFixedListener() {
      Preference usefixed = findPreference("usefixed");
      if (usefixed != null) {
        usefixed.setOnPreferenceChangeListener((preference, newValue) -> {
          if ((Boolean) newValue) startActivityForResult(new Intent(getActivity(), LocationPickerActivity.class), 0);
          else {
            if (!PermissionManager.hasLocationPermissions(getActivity())) PermissionManager.requestLocationPermissions(getActivity());
          }
          return true;
        });
      }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
      if (channels != null) {
        channels.setOnPreferenceClickListener(preference -> {
          showNotificationChannels();
          return true;
        });
      }
    }

    private void createdFixedLocationListener() {
      Preference fixedloc = findPreference("fixedloc");
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