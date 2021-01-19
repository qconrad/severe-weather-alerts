package com.severeweatheralerts.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Networking.LocationPopulaters.FromLocationPointPopulater;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Alert.AlertCardHolder;
import com.severeweatheralerts.RecyclerViews.Alert.AlertRecyclerViewAdapter;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import java.io.IOException;

import static com.severeweatheralerts.AlertListTools.AlertFilter.filter;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    populateLocations();
    asyncRefresh();
    userSync();
  }

  private void userSync() {
    new UserSyncWorkScheduler(this).oneTimeSync();
  }

  private void populateLocations() {
    Location loc = new Location();
    android.location.Location deviceLoc = new LastKnownLocation(this).getLocation();
    loc.setLatitude(deviceLoc.getLatitude());
    loc.setLongitude(deviceLoc.getLongitude());
    LocationsDao.addLocation(loc);
  }

  private void asyncRefresh() {
    new AsyncRefresh().execute();
  }

  private void displayFullAlert(int alertIndex, RecyclerView.ViewHolder holder) {
    Intent alertIntent = new Intent(MainActivity.this, AlertViewerActivity.class);
    alertIntent.putExtra("locIndex", 0);
    AlertCardHolder ach = (AlertCardHolder) holder;
    Pair<View,String> pair1 = Pair.create(ach.card, "zoom");
    ActivityOptions aO = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      aO = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pair1);
      ach.card.setTransitionName("zoom");
    }
    alertIntent.putExtra("alertID", filter(LocationsDao.getLocation(0).getAlerts()).get(alertIndex).getNwsId());
    startActivity(alertIntent, aO.toBundle());
  }

  private void populateRecyclerView() {
    RecyclerView recyclerView = findViewById(R.id.alert_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    AlertRecyclerViewAdapter alertRecyclerViewAdapter = new AlertRecyclerViewAdapter(filter(LocationsDao.getLocation(0).getAlerts()));
    alertRecyclerViewAdapter.setClickListener(this::displayFullAlert);
    recyclerView.setAdapter(alertRecyclerViewAdapter);
  }

  private class AsyncRefresh extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
      try { populateAlerts(); }
      catch (IOException e) { e.printStackTrace(); }
      return null;
    }

    private void populateAlerts() throws IOException {
      new FromLocationPointPopulater(LocationsDao.getLocation(0)).populate();
    }

    @Override
    protected void onPostExecute(Void result) {
      populateRecyclerView();
    }
  }
}