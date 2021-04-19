package com.severeweatheralerts.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.severeweatheralerts.FirstRunActivity;

import io.paperdb.Paper;

import static com.severeweatheralerts.Notifications.Channels.createNotificationChannels;

public class DefaultActivity extends AppCompatActivity {
  private SharedPreferences sharedPref;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());;
    createNotificationChannels(this);
    Paper.init(this);
    if (isFirstRun()) startActivity(new Intent(DefaultActivity.this, FirstRunActivity.class));
    else if (isUsingFixedLocation()) startActivity(new Intent(DefaultActivity.this, FetchingAlertDataActivity.class));
    else startActivity(new Intent(DefaultActivity.this, GettingLocationActivity.class));
  }

  private boolean isFirstRun() {
    return sharedPref.getBoolean("first_run", true);
  }

  private boolean isUsingFixedLocation() {
    return sharedPref.getBoolean("usefixed", false);
  }
}