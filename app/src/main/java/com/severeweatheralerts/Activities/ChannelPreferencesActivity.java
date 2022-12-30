package com.severeweatheralerts.Activities;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;
import com.severeweatheralerts.Preferences.ChannelPreferencesDataHolder;
import com.severeweatheralerts.Preferences.RippleEdit;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Preference.PreferenceAdapter;

public class ChannelPreferencesActivity extends AppCompatActivity {
  private ChannelPreferences channelPreferences;
  private PreferenceAdapter preferenceAdapter;
  private SharedPreferences sharedPref;
  private SwitchCompat rippleSwitch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alert_channel_picker);
    checkIfDeviceSupportsChannels();
    channelPreferences = getChannelPreferences();
    sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    rippleSwitch = findViewById(R.id.ripple_switch);
    rippleSwitch.setChecked(sharedPref.getBoolean("ripple_edit", true));
    inflatePreferenceList();
  }

  private ChannelPreferences getChannelPreferences() {
    if (getIntent().getExtras() == null) return new ChannelPreferences();
    boolean useDefault = getIntent().getExtras().getBoolean("useDefault", true);
    if (useDefault) return new ChannelPreferences();
    return ChannelPreferencesDataHolder.getInstance().getChannelPreferences();
  }

  private void checkIfDeviceSupportsChannels() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      TextView tv = findViewById(R.id.channel_behavior_title);
      tv.setText(R.string.channels_not_supported);
      findViewById(R.id.no_channels_message).setVisibility(View.VISIBLE);
      findViewById(R.id.channel_table).setVisibility(View.GONE);
    }
  }

  private void inflatePreferenceList() {
    RecyclerView view = findViewById(R.id.preference_stack);
    view.setLayoutManager(new LinearLayoutManager(this));
    preferenceAdapter = new PreferenceAdapter(alerts, channelPreferences);
    preferenceAdapter.setClickListener((type, index) ->
            new AlertDialog.Builder(this)
            .setTitle("Select a channel")
            .setItems(R.array.channels, (dialogInterface, channelIndex) -> {
              if (rippleSwitch.isChecked()) rippleEdit(type, index, channelIndex);
              else alertEdit(type, index, channelIndex);
            }).create().show());
    view.setAdapter(preferenceAdapter);
  }

  private void alertEdit(Alert.Type type, int index, int channelIndex) {
    new RippleEdit(channelPreferences).horizontalRipple(alerts[index], type, Channel.values()[channelIndex]);
    preferenceAdapter.notifyItemChanged(index);
  }

  private void rippleEdit(Alert.Type type, int index, int channelIndex) {
    new RippleEdit(channelPreferences).verticalRipple(alerts, index, type, Channel.values()[channelIndex]);
    preferenceAdapter.notifyDataSetChanged();
  }

  public void restoreDefaults(View view) {
    channelPreferences.resetToDefaults();
    preferenceAdapter.notifyDataSetChanged();
  }

  @Override
  protected void onPause() {
    super.onPause();
    sharedPref.edit().putBoolean("ripple_edit", rippleSwitch.isChecked()).apply();
  }

  public void savePreferences(View view) {
    ChannelPreferencesDataHolder.getInstance().setChannelPreferences(channelPreferences);
    setResult(RESULT_OK);
    finish();
  }

  public void cancel(View view) {
    finish();
  }

  String[] alerts = {"Tornado Warning",
                     "Volcano Warning",
                     "Extreme Wind Warning",
                     "Hurricane Warning",
                     "Hurricane Force Wind Warning",
                     "Storm Surge Warning",
                     "Tsunami Warning",
                     "Typhoon Warning",
                     "Snow Squall Warning",
                     "Dust Storm Warning",
                     "Severe Thunderstorm Warning",
                     "Blizzard Warning",
                     "Ice Storm Warning",
                     "Tropical Storm Warning",
                     "Storm Warning",
                     "Flash Flood Warning",
                     "Avalanche Warning",
                     "Flood Warning",
                     "Special Marine Warning",
                     "Tornado Watch",
                     "Tsunami Watch",
                     "Blizzard Watch",
                     "Severe Thunderstorm Watch",
                     "Hurricane Watch",
                     "Hurricane Force Wind Watch",
                     "Heavy Freezing Spray Warning",
                     "Lake Effect Snow Warning",
                     "Winter Storm Warning",
                     "Ashfall Warning",
                     "Earthquake Warning",
                     "Extreme Cold Warning",
                     "Wind Chill Warning",
                     "High Wind Warning",
                     "Excessive Heat Warning",
                     "Red Flag Warning",
                     "Extreme Fire Danger",
                     "Special Weather Statement",
                     "Tropical Storm Watch",
                     "Storm Surge Watch",
                     "Storm Watch",
                     "Typhoon Watch",
                     "Lake Effect Snow Watch",
                     "Winter Storm Watch",
                     "Heavy Freezing Spray Watch",
                     "Flash Flood Watch",
                     "Extreme Cold Watch",
                     "Wind Chill Watch",
                     "Excessive Heat Watch",
                     "High Wind Watch",
                     "Flood Watch",
                     "Fire Weather Watch",
                     "Avalanche Watch",
                     "Hard Freeze Warning",
                     "Freeze Warning",
                     "Hard Freeze Watch",
                     "Freeze Watch",
                     "Hurricane Local Statement",
                     "Typhoon Local Statement",
                     "Tropical Depression Local Statement",
                     "Tropical Storm Local Statement",
                     "Tsunami Advisory",
                     "Avalanche Advisory",
                     "Lakeshore Flood Warning",
                     "Coastal Flood Warning",
                     "High Surf Warning",
                     "Lake Effect Snow Advisory",
                     "Winter Weather Advisory",
                     "Freezing Spray Advisory",
                     "Freezing Rain Advisory",
                     "Freezing Fog Advisory",
                     "Heat Advisory",
                     "Wind Chill Advisory",
                     "Wind Advisory",
                     "Blowing Dust Advisory",
                     "Blowing Dust Warning",
                     "Dust Advisory",
                     "Ashfall Advisory",
                     "Dense Smoke Advisory",
                     "Dense Fog Advisory",
                     "Gale Warning",
                     "Hazardous Seas Warning",
                     "Lakeshore Flood Advisory",
                     "Lakeshore Flood Statement",
                     "Coastal Flood Advisory",
                     "Coastal Flood Statement",
                     "Frost Advisory",
                     "High Surf Advisory",
                     "Rip Current Statement",
                     "Beach Hazards Statement",
                     "Marine Weather Statement",
                     "Gale Watch",
                     "Lakeshore Flood Watch",
                     "Hazardous Seas Watch",
                     "Coastal Flood Watch",
                     "Small Craft Advisory",
                     "Flood Advisory",
                     "Lake Wind Advisory",
                     "Brisk Wind Advisory",
                     "Fire Warning",
                     "Evacuation - Immediate",
                     "Civil Danger Warning",
                     "Civil Emergency Message",
                     "Child Abduction Emergency",
                     "Shelter In Place Warning",
                     "Nuclear Power Plant Warning",
                     "Radiological Hazard Warning",
                     "Hazardous Materials Warning",
                     "911 Telephone Outage Emergency",
                     "Administrative Message",
                     "Law Enforcement Warning",
                     "Local Area Emergency",
                     "Low Water Advisory",
                     "Air Quality Alert",
                     "Air Stagnation Advisory",
                     "Short Term Forecast",
                     "Hydrologic Advisory",
                     "Hydrologic Outlook"};
}