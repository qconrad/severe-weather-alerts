package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.TestAlerts.ExtremePriorityTest;
import com.severeweatheralerts.Alerts.TestAlerts.HighPriorityTest;
import com.severeweatheralerts.Alerts.TestAlerts.LowPriorityTest;
import com.severeweatheralerts.Alerts.TestAlerts.MediumPriorityTest;
import com.severeweatheralerts.Feedback.FeedbackActivity;
import com.severeweatheralerts.Location.BundledLocation;
import com.severeweatheralerts.Location.BackgroundLocation;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Permissions.LocationPermissionRequest;
import com.severeweatheralerts.Notifications.NotificationSender;
import com.severeweatheralerts.Permissions.PermissionManager;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelIdString;
import com.severeweatheralerts.R;

import java.util.HashSet;
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
    if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
  }

  public static class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      setPreferencesFromResource(R.xml.root_preferences, rootKey);
      if (findPreference("usefixed").isEnabled())
        findPreference("fixedloc").setSummary(LocationsDao.getInstance(getContext()).getName(0));
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
      createdFeedbackListener();
      createdRestoreDismissedListener();
      createdProListener();
      createdManageExtraLocationsListener();
    }

    private void createdManageExtraLocationsListener() {
      boolean isPro = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("is_pro", false);
      Preference manageExtra = findPreference("manageextra");
      if (manageExtra != null && isPro) {
        manageExtra.setEnabled(true);
        manageExtra.setSummary("Add, remove, or change extra locations");
        manageExtra.setOnPreferenceClickListener(preference -> {
          startActivity(new Intent(getActivity(), ManageLocationsActivity.class));
          return true;
        });
      }
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
      if (usefixed != null)
        usefixed.setOnPreferenceChangeListener((preference, newValue) -> useFixed((Boolean) newValue));
    }

    private boolean useFixed(Boolean useFixedEnabled) {
      if (useFixedEnabled) startActivityForResult(new Intent(getActivity(), LocationPickerActivity.class), 1);
      else return checkLocationPermissions();
      return false;
    }

    private boolean checkLocationPermissions() {
      if (!PermissionManager.hasLocationPermissions(getActivity())) return requestPermissions();
      else return getLocation();
    }

    private boolean getLocation() {
      startActivity(new Intent(getActivity(), GettingLocationActivity.class));
      return true;
    }

    private boolean requestPermissions() {
      startActivityForResult(new Intent(getActivity(), LocationPermissionRequest.class), 0);
      return false;
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
          startActivityForResult(new Intent(getActivity(), LocationPickerActivity.class), 1);
          return true;
        });
      }
    }

    private void createdFeedbackListener() {
      Preference feedbackPref = findPreference("feedback");
      if (feedbackPref != null) {
        feedbackPref.setOnPreferenceClickListener(preference -> {
          showFeedback();
          return true;
        });
      }
    }

    private void createdRestoreDismissedListener() {
      Preference restoreDismissed = findPreference("restoreDismissed");
      if (restoreDismissed != null) {
        restoreDismissed.setOnPreferenceClickListener(preference -> {
          Toast.makeText(getContext(), "Restored", Toast.LENGTH_SHORT).show();
          Set<String> dismissedAlerts = new HashSet<>(PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet("dismissedIds", new HashSet<>()));
          dismissedAlerts.clear();
          PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putStringSet("dismissedIds", dismissedAlerts).apply();
          if (findPreference("usefixed").isEnabled()) startActivity(new Intent(getActivity(), GettingLatestDataActivity.class));
          else startActivity(new Intent(getActivity(), GettingLocationActivity.class));
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

    private void createdProListener() {
      Preference pro = findPreference("pro");
      if (pro != null) {
        pro.setOnPreferenceClickListener(preference -> {
          startActivity(new Intent(getActivity(), ProActivity.class));
          return true;
        });
      }
    }

    private void showFeedback() {
      startActivity(new Intent(getActivity(), FeedbackActivity.class));
    }

    private void showSeverityPreferences() {
      startActivity(new Intent(getActivity(), ChannelPreferencesActivity.class));
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
      if (resultCode != Activity.RESULT_OK) return;
      if (permissionReturn(requestCode)) useLocation();
      else setFixedLocation(data);
    }

    private void useLocation() {
      PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean("usefixed", false).apply();
      startActivity(new Intent(getActivity(), GettingLocationActivity.class));
    }

    private boolean permissionReturn(int requestCode) {
      return requestCode == 0;
    }

    private void setFixedLocation(@Nullable Intent data) {
      new BundledLocation(getContext(), data).setFixedLocation();
      new BackgroundLocation(getContext()).stop();
      startActivity(new Intent(getActivity(), GettingLatestDataActivity.class));
    }
  }
}