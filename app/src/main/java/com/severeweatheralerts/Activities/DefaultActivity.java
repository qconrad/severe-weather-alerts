package com.severeweatheralerts.Activities;

import static com.severeweatheralerts.FileDB.getLocationsDao;
import static com.severeweatheralerts.Notifications.Channels.createNotificationChannels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class DefaultActivity extends AppCompatActivity {
  private SharedPreferences sharedPref;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    createNotificationChannels(this);
    if (getLocationsDao(this).getDefaultLocation().coordinateSet()) startApp();
    else showFirstRun();
  }

  private void showFirstRun() {
    startActivity(new Intent(DefaultActivity.this, FirstRunActivity.class));
  }

  private void startApp() {
    if (isUsingFixedLocation()) startActivity(new Intent(DefaultActivity.this, GettingLatestDataActivity.class));
    else startActivity(new Intent(DefaultActivity.this, GettingLocationActivity.class));
  }

  private boolean isUsingFixedLocation() {
    return sharedPref.getBoolean("usefixed", false);
  }
}