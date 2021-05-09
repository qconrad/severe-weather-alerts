package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.TestAlerts.ExtremePriorityTest;
import com.severeweatheralerts.Alerts.TestAlerts.HighPriorityTest;
import com.severeweatheralerts.Alerts.TestAlerts.LowPriorityTest;
import com.severeweatheralerts.Alerts.TestAlerts.MediumPriorityTest;
import com.severeweatheralerts.Location.BackgroundLocation;
import com.severeweatheralerts.Location.ConditionalDefaultLocationSync;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Notifications.NotificationSender;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelIdString;
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

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (PermissionManager.hasLocationPermissions(this)) startActivity(new Intent(SettingsActivity.this, GettingLocationActivity.class));
  }


  public static class SettingsFragment extends PreferenceFragmentCompat {
    private LocationsDao locationsDao;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      locationsDao = LocationsDao.getInstance(getContext());
      setPreferencesFromResource(R.xml.root_preferences, rootKey);
      if (findPreference("usefixed").isEnabled()) {
        findPreference("fixedloc").setSummary(locationsDao.getName(0));
      }
      createClickListeners();
    }

    private void createClickListeners() {
      createChannelListener();
      createTestAlertListener();
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

    private void createTestAlertListener() {
      Preference testAlertPref = findPreference("testalert");
      if (testAlertPref != null) {
        testAlertPref.setOnPreferenceClickListener(preference -> {
          new AlertDialog.Builder(getActivity())
                  .setTitle("Select a channel")
                  .setItems(R.array.channels, (dialogInterface, channelIndex) -> {
                    Channel selectedChannel = Channel.values()[channelIndex];
                    if (selectedChannel != Channel.NONE)
                    new NotificationSender(getContext(), getTestAlert(selectedChannel), ChannelIdString.getChannelString(selectedChannel)).send();
                  }).create().show();
          return true;
        });
      }
    }

    private Alert getTestAlert(Channel channel) {
      if (channel == Channel.EXTREME) return new ExtremePriorityTest();
      else if (channel == Channel.HIGH) return new HighPriorityTest();
      else if (channel == Channel.MEDIUM) return new MediumPriorityTest();
      return new LowPriorityTest();
    }

    private void createdUseFixedListener() {
      Preference usefixed = findPreference("usefixed");
      if (usefixed != null) {
        usefixed.setOnPreferenceChangeListener((preference, newValue) -> {
          if ((Boolean) newValue) {
            new BackgroundLocation(getContext()).stop();
            locationsDao.setName(0, "Fixed Location");
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
        locationsDao.setDefaultLocation(extras.getString("name"), extras.getDouble("lat"), extras.getDouble("lon"));
        new ConditionalDefaultLocationSync(getContext(), extras.getDouble("lat"), extras.getDouble("lon")).sync();
        startActivity(new Intent(getActivity(), GettingLatestDataActivity.class));
      }
    }
  }
}