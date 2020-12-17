package com.severeweatheralerts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import static com.severeweatheralerts.LocationAlertPopulator.populateLocationWithAlertsForThatLocation;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    new AsyncRefresh().execute();
  }

  private void displayFullAlert(int locationIndex, int alertIndex) {
    Intent alertIntent = new Intent(MainActivity.this, AlertViewerActivity.class);
    alertIntent.putExtra("locIndex", locationIndex);
    alertIntent.putExtra("alertIndex", alertIndex);
    startActivity(alertIntent);
  }

  private class AsyncRefresh extends AsyncTask<Void, Void, Void> {
    Location location = new Location();
    @Override
    protected Void doInBackground(Void... params) {
      try { populateLocationWithAlertsForThatLocation(location); }
      catch (IOException e) { e.printStackTrace(); }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      LocationsDao.addLocation(location);
      displayFullAlert(0, 0);
    }
  }
}