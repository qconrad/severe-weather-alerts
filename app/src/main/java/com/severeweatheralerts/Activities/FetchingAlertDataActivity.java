package com.severeweatheralerts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Networking.LocationPopulaters.FromLocationPointPopulater;
import com.severeweatheralerts.Networking.LocationPopulaters.PopulateCallback;
import com.severeweatheralerts.R;

public class FetchingAlertDataActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading);
    getAlerts();
    setProgressbarColor();
  }

  private void setProgressbarColor() {
    ProgressBar pb = findViewById(R.id.fetch_progress);
    pb.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
  }

  private void getAlerts() {
    new FromLocationPointPopulater(LocationsDao.getLocation(0), this).populate(new PopulateCallback() {
      @Override
      public void complete() {
        displayAlerts();
      }

      @Override
      public void error(String message) {
        Toast.makeText(FetchingAlertDataActivity.this, message, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void displayAlerts() {
    Intent alertListIntent = new Intent(FetchingAlertDataActivity.this, AlertListActivity.class);
    startActivity(alertListIntent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }
}