package com.severeweatheralerts;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.AlertListRecyclerView.AlertCardClickedListener;
import com.severeweatheralerts.AlertListRecyclerView.AlertCardHolder;
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

  private void displayFullAlert(int alertIndex, AlertCardHolder holder) {
    Intent alertIntent = new Intent(MainActivity.this, AlertViewerActivity.class);
    alertIntent.putExtra("locIndex", 0);
    Pair<View,String> pair1 = Pair.create((View)holder.card, "zoom");
    ActivityOptions aO = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      aO = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pair1);
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      holder.card.setTransitionName("zoom");
    }
    alertIntent.putExtra("alertIndex", alertIndex);
    assert aO != null;
    startActivity(alertIntent, aO.toBundle());
  }


  private void populateRecyclerView() {
    RecyclerView recyclerView = findViewById(R.id.alert_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    AlertRecyclerViewAdapter alertRecyclerViewAdapter = new AlertRecyclerViewAdapter(LocationsDao.getLocation(0).getAlerts());
    alertRecyclerViewAdapter.setClickListener(new AlertCardClickedListener() {
      @Override
      public void onAlertCardClicked(int position, AlertCardHolder holder) {
        displayFullAlert(position, holder);
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