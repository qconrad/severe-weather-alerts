package com.severeweatheralerts.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.AlertListTools.ActiveFilter;
import com.severeweatheralerts.AlertListTools.InactiveFilter;
import com.severeweatheralerts.AlertListTools.ReplacementFilter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Alert.AlertCardHolder;
import com.severeweatheralerts.RecyclerViews.Alert.AlertRecyclerViewAdapter;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import java.util.ArrayList;
import java.util.Date;

public class AlertListActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alertlist);
    populateRecyclerViews();
    setBackgroundColor();
    userSync();
  }

  private void userSync() {
    new UserSyncWorkScheduler(this).oneTimeSync();
  }

  private void displayFullAlert(Alert alert, RecyclerView.ViewHolder holder) {
    Intent alertIntent = new Intent(AlertListActivity.this, AlertViewerActivity.class);
    alertIntent.putExtra("locIndex", 0);
    AlertCardHolder ach = (AlertCardHolder) holder;
    Pair<View,String> pair1 = Pair.create(ach.card, "zoom");
    ActivityOptions aO = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      aO = ActivityOptions.makeSceneTransitionAnimation(AlertListActivity.this, pair1);
      ach.card.setTransitionName("zoom");
    }
    alertIntent.putExtra("alertID", alert.getNwsId());
    startActivity(alertIntent, aO.toBundle());
  }

  private void populateRecyclerViews() {
    populateActiveRecyclerView();
    populateRecentRecyclerView();
  }

  private void populateActiveRecyclerView() {
    ArrayList<Alert> activeAlerts = new ActiveFilter(getRelevantAlerts(), new Date()).filter();
    if (activeAlerts.size() > 0) {
      findViewById(R.id.active_alerts).setVisibility(View.VISIBLE);
      populateRecyclerView(findViewById(R.id.active_recycler_view), activeAlerts);
    }
  }

  private void populateRecentRecyclerView() {
    ArrayList<Alert> inactiveAlerts = new InactiveFilter(getRelevantAlerts(), new Date()).filter();
    if (inactiveAlerts.size() > 0) {
      findViewById(R.id.iactive_alerts).setVisibility(View.VISIBLE);
      populateRecyclerView(findViewById(R.id.inactive_recycler_view), inactiveAlerts);
    }
  }

  public void populateRecyclerView(RecyclerView view, ArrayList<Alert> alerts) {
    view.setLayoutManager(new LinearLayoutManager(this));
    AlertRecyclerViewAdapter alertRecyclerViewAdapter = new AlertRecyclerViewAdapter(alerts);
    alertRecyclerViewAdapter.setClickListener(this::displayFullAlert);
    view.setAdapter(alertRecyclerViewAdapter);
  }

  private ArrayList<Alert> getRelevantAlerts() {
    return new ReplacementFilter(LocationsDao.getLocation(0).getAlerts()).filter();
  }

  private void setBackgroundColor() {
    int situationSeverityColor = Color.parseColor("#33FF00");
    View listView = findViewById(R.id.alert_list_view);
    int topGradientStep = ColorBrightnessChanger.changeBrightness(situationSeverityColor, 0.3f);
    int bottomGradientStep = ColorBrightnessChanger.changeBrightness(situationSeverityColor, 0.0f);
    GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {topGradientStep, bottomGradientStep});
    listView.setBackground(gd);
  }

  @Override
  public void onBackPressed() {
    finishAffinity();
  }
}