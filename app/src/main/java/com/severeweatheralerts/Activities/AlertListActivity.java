package com.severeweatheralerts.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.AlertListTools.AlertFilters.ActiveFilter;
import com.severeweatheralerts.AlertListTools.AlertFilters.InactiveFilter;
import com.severeweatheralerts.AlertListTools.AlertFilters.ReplacementFilter;
import com.severeweatheralerts.AlertListTools.SeveritySorter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Alert.AlertCardHolder;
import com.severeweatheralerts.RecyclerViews.Alert.AlertRecyclerViewAdapter;
import com.severeweatheralerts.Status.Status;
import com.severeweatheralerts.Status.StatusPicker;
import com.severeweatheralerts.Status.TextListFade;

import java.util.ArrayList;
import java.util.Date;

public class AlertListActivity extends AppCompatActivity {
  private ArrayList<Alert> activeAlerts;
  private ArrayList<Alert> inactiveAlerts;
  private TextListFade textListFade;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alertlist);
    LocationsDao locationsDao = new LocationsDao(this);
    setLocationName(locationsDao.getName(0));
    sortAndFilterAlerts(locationsDao.getAlerts(0));
    populateRecyclerViews();
    setStatus(getStatus());
  }

  protected Status getStatus() {
    return new StatusPicker(activeAlerts, inactiveAlerts).getStatus();
  }

  private void sortAndFilterAlerts(ArrayList<Alert> alerts) {
    ArrayList<Alert> relevantAlerts = new ReplacementFilter(alerts).filter();
    activeAlerts = new SeveritySorter(new ActiveFilter(relevantAlerts, new Date()).filter()).getSorted();
    inactiveAlerts = new InactiveFilter(relevantAlerts, new Date()).filter();
  }

  private void setLocationName(String name) {
    TextView locationName = findViewById(R.id.location_name);
    locationName.setText(name);
  }

  private void setStatus(Status status) {
    setBackgroundColor(status.getColor());
    setStatusHeadline(status.getHeadline());
    setStatusSubtext(status.getSubtexts());
    setStatusIcon(status.getIcon());
  }

  private void setStatusSubtext(ArrayList<String> subtexts) {
    textListFade = new TextListFade(this, subtexts, findViewById(R.id.status_switcher));
    textListFade.beginFade();
  }

  private void setStatusHeadline(String headline) {
    TextView headlineView = findViewById(R.id.status_headline);
    headlineView.setText(headline);
  }

  private void setStatusIcon(int icon) {
    ImageView iconView = findViewById(R.id.status_icon);
    iconView.setImageResource(icon);
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
    if (activeAlerts.size() > 0) {
      findViewById(R.id.active_alerts).setVisibility(View.VISIBLE);
      populateRecyclerView(findViewById(R.id.active_alerts_rv), activeAlerts);
    }
  }

  private void populateRecentRecyclerView() {
    if (inactiveAlerts.size() > 0) {
      findViewById(R.id.iactive_alerts).setVisibility(View.VISIBLE);
      populateRecyclerView(findViewById(R.id.inactive_alerts_rv), inactiveAlerts);
    }
  }

  public void populateRecyclerView(RecyclerView view, ArrayList<Alert> alerts) {
    view.setLayoutManager(new LinearLayoutManager(this));
    AlertRecyclerViewAdapter alertRecyclerViewAdapter = new AlertRecyclerViewAdapter(alerts);
    alertRecyclerViewAdapter.setClickListener(this::displayFullAlert);
    view.setAdapter(alertRecyclerViewAdapter);
  }

  private void setBackgroundColor(int color) {
    int gradientTop = ColorBrightnessChanger.changeBrightness(color, 0.7f);
    GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {gradientTop, Color.BLACK});
    findViewById(R.id.alert_list_view).setBackground(gd);
  }

  @Override
  public void onBackPressed() {
    finishAffinity();
  }

  public void settingsClick(View view) {
    startActivity(new Intent(AlertListActivity.this, SettingsActivity.class));
  }

  public void subTextClick(View view) {
    textListFade.nextSubtext();
  }

  public void refreshClick(View view) {
    startActivity(new Intent(AlertListActivity.this, FetchingAlertDataActivity.class));
  }
}