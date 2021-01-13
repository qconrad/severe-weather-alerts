package com.severeweatheralerts;

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

import com.severeweatheralerts.Networking.LocationPopulater;
import com.severeweatheralerts.RecyclerViews.Alert.AlertCardHolder;
import com.severeweatheralerts.RecyclerViews.Alert.AlertRecyclerViewAdapter;

import java.io.IOException;

import static com.severeweatheralerts.ReferenceFilter.removeReferences;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
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
    alertIntent.putExtra("alertID", removeReferences(LocationsDao.getLocation(0).getAlerts()).get(alertIndex).getNwsId());
    startActivity(alertIntent, aO.toBundle());
  }

  private void populateRecyclerView() {
    RecyclerView recyclerView = findViewById(R.id.alert_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    AlertRecyclerViewAdapter alertRecyclerViewAdapter = new AlertRecyclerViewAdapter(removeReferences(LocationsDao.getLocation(0).getAlerts()));
    alertRecyclerViewAdapter.setClickListener(this::displayFullAlert);
    recyclerView.setAdapter(alertRecyclerViewAdapter);
  }

  private class AsyncRefresh extends AsyncTask<Void, Void, Void> {
    Location location = new Location();
    @Override
    protected Void doInBackground(Void... params) {
      try { new LocationPopulater(location).populate(); }
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