package com.severeweatheralerts.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.AlertListTools.AlertFilters.ActiveFilter;
import com.severeweatheralerts.AlertListTools.AlertFilters.InactiveFilter;
import com.severeweatheralerts.AlertListTools.AlertFilters.ReplacementFilter;
import com.severeweatheralerts.AlertListTools.SeveritySorter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Refreshing.IntervalRun;
import com.severeweatheralerts.Location.ConditionalDefaultLocationSync;
import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.LocationChange;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Notifications.NotificationCancel;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Alert.AlertCardHolder;
import com.severeweatheralerts.RecyclerViews.Alert.AlertRecyclerViewAdapter;
import com.severeweatheralerts.Refreshing.Refresher;
import com.severeweatheralerts.Status.Status;
import com.severeweatheralerts.Status.StatusPicker;
import com.severeweatheralerts.Status.TextListFade;

import java.util.ArrayList;
import java.util.Date;

public class AlertListActivity extends AppCompatActivity {
  private final Refresher refresher = new Refresher();
  private ArrayList<Alert> activeAlerts;
  private ArrayList<Alert> inactiveAlerts;
  private IntervalRun subtextFade;
  private GCSCoordinate lastLocation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    fetchDataIfCleared();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alertlist);
    LocationsDao locationsDao = LocationsDao.getInstance(this);
    lastLocation = locationsDao.getCoordinate(0);
    setLocationName(locationsDao.getName(0));
    ArrayList<Alert> alerts = locationsDao.getAlerts(0);
    sortAndFilterAlerts(alerts);
    populateRecyclerViews();
    setStatus(getStatus());
    keepEverythingUpToDate(locationsDao, alerts);
  }

  private void keepEverythingUpToDate(LocationsDao locationsDao, ArrayList<Alert> alerts) {
    LocationsDao.onNewAlerts(() -> promptNewData(getString(R.string.new_data_available)));
    if (usingDevicesLocation()) monitorForLocationChanges(locationsDao);
    monitorForExpiration(alerts);
  }

  private boolean usingDevicesLocation() {
    return !PreferenceManager.getDefaultSharedPreferences(this).getBoolean("usefixed", false);
  }

  private void monitorForLocationChanges(LocationsDao locationsDao) {
    refresher.add(new IntervalRun(Constants.APP_OPEN_LAST_KNOWN_LOCATION_CHECK_INTERVAL, () -> checkForLocationUpdate(locationsDao)));
  }

  private void checkForLocationUpdate(LocationsDao locationsDao) {
    Location newLoc = new LastKnownLocation(this).getLocation();
    if (newLoc != null && locationChanged(lastLocation, newLoc))
      saveAndPromptRefresh(locationsDao, newLoc);
  }

  private void saveAndPromptRefresh(LocationsDao locationsDao, Location newLoc) {
    locationsDao.setDefaultLocationCoordinate(newLoc.getLatitude(), newLoc.getLongitude());
    promptLocationChange(getString(R.string.location_change), newLoc);
  }

  private void promptLocationChange(String text, Location newLoc) {
    Snackbar.make(findViewById(android.R.id.content), text,
      Snackbar.LENGTH_INDEFINITE).setAction("Refresh", view -> syncAndRefresh(newLoc)).show();
  }

  private void syncAndRefresh(Location newLoc) {
    startActivity(new Intent(AlertListActivity.this, GettingLatestDataActivity.class));
    new ConditionalDefaultLocationSync(this, newLoc.getLatitude(), newLoc.getLongitude()).sync();
  }

  private void promptNewData(String text) {
    Snackbar.make(findViewById(android.R.id.content), text,
            Snackbar.LENGTH_INDEFINITE).setAction("Refresh", view ->
            startActivity(new Intent(AlertListActivity.this, GettingLatestDataActivity.class)))
            .show();
  }

  private boolean locationChanged(GCSCoordinate currentLocation, Location newLocation) {
    return new LocationChange(currentLocation, newLocation).hasChanged();
  }

  private void monitorForExpiration(ArrayList<Alert> alerts) {
    refresher.add(new IntervalRun(Constants.CHECK_FOR_EXPIRATION_REFRESH_TIME, () -> refresh(alerts)));
  }

  private void refresh(ArrayList<Alert> alerts) {
    if (new InactiveFilter(activeAlerts, new Date()).filter().size() <= 0) return;
    sortAndFilterAlerts(alerts);
    populateRecyclerViews();
    setStatus(getStatus());
  }

  private void fetchDataIfCleared() {
    if (!LocationsDao.hasInstance()) {
      startActivity(new Intent(AlertListActivity.this, GettingLatestDataActivity.class));
      finish();
    }
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
    fadeSubtextsIfMultiple(subtexts, new TextListFade(this, subtexts, findViewById(R.id.status_switcher)));
  }

  private void fadeSubtextsIfMultiple(ArrayList<String> subtexts, TextListFade textListFade) {
    if (!multipleTexts(subtexts)) return;
    if (subtextFade != null) subtextFade.stop();
    subtextFade = new IntervalRun(Constants.STATUS_SUBTEXT_TRANSITION_TIME, textListFade::nextText);
    subtextFade.startNextInterval();
  }

  private boolean multipleTexts(ArrayList<String> texts) {
    return texts.size() > 1;
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
    new NotificationCancel(this, alert).cancel();
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
    populateRecyclerView(findViewById(R.id.active_alerts_rv), activeAlerts);
    if (noAlerts(activeAlerts)) hideText(R.id.active_alerts);
    else findViewById(R.id.active_alerts).setVisibility(View.VISIBLE);
  }

  private void populateRecentRecyclerView() {
    populateRecyclerView(findViewById(R.id.inactive_alerts_rv), inactiveAlerts);
    if (noAlerts(inactiveAlerts)) hideText(R.id.inactive_alerts);
    else findViewById(R.id.inactive_alerts).setVisibility(View.VISIBLE);
  }

  private void hideText(int p) {
    findViewById(p).setVisibility(View.GONE);
  }

  private boolean noAlerts(ArrayList<Alert> activeAlerts) {
    return activeAlerts.size() <= 0;
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
    if (subtextFade != null) subtextFade.execAndReset();
  }

  private boolean stopped;
  @Override
  protected void onStop() {
    super.onStop();
    pauseSubtextFade();
    stopped = true;
    refresher.stop();
  }

  private void pauseSubtextFade() {
    if (subtextFade != null) subtextFade.stop();
  }

  @Override
  protected void onResume() {
    super.onResume();
    resumeSubtext();
    if (stopped) resume();
    else firstStart();
  }

  private void resume() {
    if (usingDevicesLocation()) checkIfLocationIsReasonablyUpToDate();
    refresher.startAndRefresh();
  }

  private void firstStart() {
    checkForMissedAlerts();
    refresher.start();
  }

  private void checkForMissedAlerts() {
    if (LocationsDao.getInstance(this).messagesAvailable())
      promptNewData(getString(R.string.new_data_available));
  }

  private void checkIfLocationIsReasonablyUpToDate() {
    Location newLoc = new LastKnownLocation(this).getLocation();
    if (newLoc != null && outdated(newLoc))startActivity(new Intent(AlertListActivity.this, GettingLocationActivity.class));
  }

  private boolean outdated(android.location.Location lastKnown) {
    return lastKnown.getTime() < new Date().getTime() - Constants.APP_OPENED_LOCATION_EXPIRE;
  }

  private void resumeSubtext() {
    if (subtextFade != null) subtextFade.startNextInterval();
  }
}