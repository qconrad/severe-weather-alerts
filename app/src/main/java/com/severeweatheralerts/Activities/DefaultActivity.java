package com.severeweatheralerts.Activities;

import static com.severeweatheralerts.Notifications.Channels.createNotificationChannels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.severeweatheralerts.FileDBs;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Location.MockDatabase;

public class DefaultActivity extends AppCompatActivity {
  private SharedPreferences sharedPref;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initFileDBs();
    sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    createNotificationChannels(this);
    if (isFirstRun()) startActivity(new Intent(DefaultActivity.this, FirstRunActivity.class));
    else if (isUsingFixedLocation()) startActivity(new Intent(DefaultActivity.this, GettingLatestDataActivity.class));
    else startActivity(new Intent(DefaultActivity.this, GettingLocationActivity.class));
  }

  private void initFileDBs() {
    FileDBs.locationsDao = new LocationsDao(new MockDatabase());
  }

  private boolean isFirstRun() {
    return sharedPref.getBoolean("first_run", true);
  }

  private boolean isUsingFixedLocation() {
    return sharedPref.getBoolean("usefixed", false);
  }
}