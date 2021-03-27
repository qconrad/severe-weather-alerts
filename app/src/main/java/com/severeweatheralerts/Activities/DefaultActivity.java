package com.severeweatheralerts.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static com.severeweatheralerts.Notifications.Channels.createNotificationChannels;

public class DefaultActivity extends AppCompatActivity {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    createNotificationChannels(this);
    startActivity(new Intent(DefaultActivity.this, GettingLocationActivity.class));
  }
}