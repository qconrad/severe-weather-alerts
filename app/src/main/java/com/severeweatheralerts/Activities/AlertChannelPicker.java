package com.severeweatheralerts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.ChannelPreferences;
import com.severeweatheralerts.R;

public class AlertChannelPicker extends AppCompatActivity {
  LayoutInflater inflater;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alert_channel_picker);
    inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View alertPreference = createAlertPreference("Tornado Warning", 0);
    View alertPreference2 = createAlertPreference("Different Warning", 1);
    LinearLayout viewById = findViewById(R.id.preference_stack);
    viewById.addView(alertPreference);
    viewById.addView(alertPreference2);
  }

  private View createAlertPreference(String alertName, int index) {
    View preferenceView = inflater.inflate(R.layout.alert_preference, null);
    TextView prefText = preferenceView.findViewById(R.id.alert_pref_text);
    Button postButton = preferenceView.findViewById(R.id.post_button);
    Button updateButton = preferenceView.findViewById(R.id.update_button);
    Button cancelButton = preferenceView.findViewById(R.id.cancel_button);
    postButton.setTag(index);
    updateButton.setTag(index);
    cancelButton.setTag(index);
    ChannelPreferences channelPreferences = new ChannelPreferences();
    postButton.setText(channelPreferences.getChannel(Alert.Type.POST, alertName).toString());
    updateButton.setText(channelPreferences.getChannel(Alert.Type.UPDATE, alertName).toString());
    cancelButton.setText(channelPreferences.getChannel(Alert.Type.CANCEL, alertName).toString());
    prefText.setText(alertName);
    return preferenceView;
  }
}