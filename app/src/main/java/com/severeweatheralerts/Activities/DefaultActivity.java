package com.severeweatheralerts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static com.severeweatheralerts.NotificationChannels.createNotificationChannels;

public class DefaultActivity extends AppCompatActivity {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    createNotificationChannels(this);
    startActivity(new Intent(DefaultActivity.this, GettingLocationActivity.class));
  }
}