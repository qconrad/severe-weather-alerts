package com.severeweatheralerts.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;

import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Preference.PreferenceAdapter;
import com.severeweatheralerts.RippleEdit;

public class AlertChannelPickerActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alert_channel_picker);
    inflatePreferenceList();
  }

  private void inflatePreferenceList() {
    RecyclerView view = findViewById(R.id.preference_stack);
    view.setLayoutManager(new LinearLayoutManager(this));
    ChannelPreferences channelPreferences = new ChannelPreferences();
    PreferenceAdapter preferenceAdapter = new PreferenceAdapter(alerts, channelPreferences);
    preferenceAdapter.setClickListener((type, index) -> {
      new AlertDialog.Builder(this)
              .setTitle("Select a channel")
             .setItems(R.array.channels, (dialogInterface, i) -> {
               new RippleEdit(channelPreferences).verticalRipple(alerts, index, type, Channel.values()[i]);
               preferenceAdapter.notifyDataSetChanged();
              }).create().show();
    });
    view.setAdapter(preferenceAdapter);
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