package com.severeweatheralerts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.AlertListRecyclerView.AlertCardClickedListener;
import com.severeweatheralerts.AlertListRecyclerView.AlertRecyclerViewAdapter;

import java.io.IOException;

import static com.severeweatheralerts.Networking.LocationAlertPopulator.populateLocationWithAlertsForThatLocation;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    new AsyncRefresh().execute();
  }

  private void displayFullAlert(int alertIndex) {
    Intent alertIntent = new Intent(MainActivity.this, AlertViewerActivity.class);
    alertIntent.putExtra("locIndex", 0);
    alertIntent.putExtra("alertIndex", alertIndex);
    startActivity(alertIntent);
  }

  private void populateRecyclerView() {
    RecyclerView recyclerView = findViewById(R.id.alert_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    AlertRecyclerViewAdapter alertRecyclerViewAdapter = new AlertRecyclerViewAdapter(LocationsDao.getLocation(0).getAlerts());
    alertRecyclerViewAdapter.setClickListener(new AlertCardClickedListener() {
      @Override
      public void onAlertCardClicked(int position) {
        displayFullAlert(position);
      }
    });
    recyclerView.setAdapter(alertRecyclerViewAdapter);
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
      populateRecyclerView();
    }
  }
}