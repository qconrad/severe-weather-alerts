package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.severeweatheralerts.AttributionActivity;
import com.severeweatheralerts.Location.Geofencing.GeofenceManager;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.R;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

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

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (PermissionManager.hasLocationPermissions(this)) startActivity(new Intent(SettingsActivity.this, GettingLocationActivity.class));
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
      createAttributionListener();
      createPrivacyPolicyListener();
      createSeverityPreferencesListener();
    }

    private void createAttributionListener() {
      Preference attributions = findPreference("attrib");
      if (attributions != null) {
        attributions.setOnPreferenceClickListener(preference -> {
          startActivity(new Intent(getActivity(), AttributionActivity.class));
          return true;
        });
      }
    }

    private void createdUseFixedListener() {
      Preference usefixed = findPreference("usefixed");
      if (usefixed != null) {
        usefixed.setOnPreferenceChangeListener((preference, newValue) -> {
          if ((Boolean) newValue) {
            new GeofenceManager(getContext()).removeGeofences();
            LocationsDao.setName(0, "Fixed Location");
            findPreference("fixedloc").setSummary("Default Location");
            startActivityForResult(new Intent(getActivity(), LocationPickerActivity.class), 0);
          }
          else {
            if (!PermissionManager.hasLocationPermissions(getActivity())) PermissionManager.requestLocationPermissions(getActivity());
            else startActivity(new Intent(getActivity(), GettingLocationActivity.class));
          }
          return true;
        });
      }
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

    private void createPrivacyPolicyListener() {
      Preference privacy = findPreference("privacy");
      if (privacy != null) {
        privacy.setOnPreferenceClickListener(preference -> {
          Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getActivity().getString(R.string.privacy_policy_link)));
          startActivity(browserIntent);
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
        new UserSyncWorkScheduler(getContext()).oneTimeSync();
        startActivity(new Intent(getActivity(), FetchingAlertDataActivity.class));
      }
    }
  }
}