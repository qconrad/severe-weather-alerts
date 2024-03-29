package com.severeweatheralerts.Activities;

import static com.severeweatheralerts.FileDB.getLocationsDao;
import static com.severeweatheralerts.NewAlerts.hasNewAlerts;
import static com.severeweatheralerts.NewAlerts.onNewAlerts;

import android.app.ActivityOptions;
import android.app.AlertDialog;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.AlertListTools.AlertFilters.ActiveFilter;
import com.severeweatheralerts.AlertListTools.AlertFilters.DismissedFilter;
import com.severeweatheralerts.AlertListTools.AlertFilters.InactiveFilter;
import com.severeweatheralerts.AlertListTools.AlertFilters.ReplacementFilter;
import com.severeweatheralerts.AlertListTools.SeveritySorter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Billing.SubscriptionManager;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.FileDB;
import com.severeweatheralerts.Location.ConditionalDefaultLocationSync;
import com.severeweatheralerts.Location.LastKnownLocation;
import com.severeweatheralerts.Location.LocationChange;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Notifications.NotificationCancel;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Alert.AlertCardHolder;
import com.severeweatheralerts.RecyclerViews.Alert.AlertRecyclerViewAdapter;
import com.severeweatheralerts.Refreshing.IntervalRun;
import com.severeweatheralerts.Refreshing.Refresher;
import com.severeweatheralerts.Status.Status;
import com.severeweatheralerts.Status.StatusPicker;
import com.severeweatheralerts.Status.TextListFade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AlertListActivity extends AppCompatActivity {
  private final Refresher refresher = new Refresher();
  int locationIndex = 0;
  private ArrayList<Alert> activeAlerts;
  private ArrayList<Alert> inactiveAlerts;
  private IntervalRun subtextFade;
  private GCSCoordinate lastLocation;
  private Date lastLocationTime = null;
  private Set<String> dismissedIds;
  private SubscriptionManager subscriptionManager;
  private LocationsDao locationsDao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    fetchDataIfCleared();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alertlist);
    locationIndex = getIntent().getIntExtra("locationIndex", 0);
    locationsDao = getLocationsDao(this);
    lastLocation = locationsDao.getLocation(locationIndex).getCoordinate();
    setLocationName(locationsDao.getLocation(locationIndex).getName());
    ArrayList<Alert> alerts = locationsDao.getLocation(locationIndex).getAlerts();
    dismissedIds = new HashSet<>(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getStringSet("dismissedIds", new HashSet<>()));
    sortAndFilterAlerts(alerts);
    populateRecyclerViews();
    setStatus(getStatus());
    updateLocationTime();
    keepEverythingUpToDate(locationsDao, alerts);
    displayEdgeToEdge();
    subscriptionManager = new SubscriptionManager(this);
    subscriptionManager.startBillingConnection();
  }

  private void displayEdgeToEdge() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      makeStatusBarTransparent();
      setContentInsets();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void makeStatusBarTransparent() {
    WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
    getWindow().setStatusBarColor(Color.TRANSPARENT);
  }

  private void setContentInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.alert_list_view), (v, insets) -> {
      Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom);
      return WindowInsetsCompat.CONSUMED;
    });
  }

  private void keepEverythingUpToDate(LocationsDao locationsDao, ArrayList<Alert> alerts) {
    onNewAlerts(() -> promptNewData(getString(R.string.new_data_available)));
    if (usingDevicesLocation()) monitorForLocationChanges(locationsDao);
    monitorForExpiration(alerts);
  }

  private boolean usingDevicesLocation() {
    return locationIndex == 0 && !PreferenceManager.getDefaultSharedPreferences(this).getBoolean("usefixed", false);
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
    locationsDao.setDefaultLocation(locationsDao.getDefaultLocation().setCoordinate(new GCSCoordinate(newLoc.getLatitude(), newLoc.getLongitude())));
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
            startActivity(new Intent(AlertListActivity.this, GettingLatestDataActivity.class)
              .putExtra("locationIndex", locationIndex)))
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
    if (FileDB.hasLocationsDaoInstance()) return;
    startActivity(new Intent(AlertListActivity.this, GettingLatestDataActivity.class));
    finish();
  }

  protected Status getStatus() {
    return new StatusPicker(activeAlerts, inactiveAlerts).getStatus();
  }

  private void sortAndFilterAlerts(ArrayList<Alert> alerts) {
    ArrayList<Alert> relevantAlerts = new ReplacementFilter(alerts).filter();
    activeAlerts = new SeveritySorter(new ActiveFilter(relevantAlerts, new Date()).filter()).getSorted();
    inactiveAlerts = new InactiveFilter(relevantAlerts, new Date()).filter();
    clearDismissedIfListIsLargeAndNoInactive();
    inactiveAlerts = new DismissedFilter(inactiveAlerts, dismissedIds).filter();
  }

  private void clearDismissedIfListIsLargeAndNoInactive() {
    if (inactiveAlerts.size() <= 0 && dismissedIds.size() > 500)
      dismissedIds.clear();
  }

  private void setLocationName(String name) {
    TextView locationName = findViewById(R.id.location_name);
    locationName.setText(name);
  }

  private void setStatus(Status status) {
    resetSubtextFade();
    setBackgroundColor(status.getColor());
    setStatusHeadline(status.getHeadline());
    setStatusSubtext(status.getSubtexts());
    setStatusIcon(status.getIcon());
  }

  private void resetSubtextFade() {
    if (subtextFade == null) return;
    subtextFade.stop();
    subtextFade = null;
  }

  private void setStatusSubtext(ArrayList<String> subtexts) {
    fadeSubtextsIfMultiple(subtexts, new TextListFade(this, subtexts, findViewById(R.id.status_switcher)));
  }

  private void fadeSubtextsIfMultiple(ArrayList<String> subtexts, TextListFade textListFade) {
    if (!multipleTexts(subtexts)) return;
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
    alertIntent.putExtra("locIndex", locationIndex);
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
    RecyclerView activeRv = findViewById(R.id.active_alerts_rv);
    activeRv.setLayoutManager(new LinearLayoutManager(this));
    activeRv.setAdapter(getAlertAdapter(activeAlerts));
    setRVTitleVisibility(findViewById(R.id.active_alerts), activeAlerts);
  }

  private AlertRecyclerViewAdapter getAlertAdapter(ArrayList<Alert> alerts) {
    AlertRecyclerViewAdapter alertRecyclerViewAdapter = new AlertRecyclerViewAdapter(alerts);
    alertRecyclerViewAdapter.setClickListener(this::displayFullAlert);
    return alertRecyclerViewAdapter;
  }

  private void populateRecentRecyclerView() {
    RecyclerView recentRv = findViewById(R.id.inactive_alerts_rv);
    recentRv.setHasFixedSize(false);
    recentRv.setLayoutManager(new LinearLayoutManager(this));
    AlertRecyclerViewAdapter alertRecyclerViewAdapter = getAlertAdapter(inactiveAlerts);
    new ItemTouchHelper(getItemTouchHelper(alertRecyclerViewAdapter)).attachToRecyclerView(recentRv);
    recentRv.setAdapter(alertRecyclerViewAdapter);
    setRVTitleVisibility(findViewById(R.id.inactive_alerts), inactiveAlerts);
  }

  private ItemTouchHelper.SimpleCallback getItemTouchHelper(AlertRecyclerViewAdapter alertRecyclerViewAdapter) {
    return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
      @Override
      public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        String id = inactiveAlerts.get(viewHolder.getAbsoluteAdapterPosition()).getNwsId();
        inactiveAlerts.remove(viewHolder.getAbsoluteAdapterPosition());
        alertRecyclerViewAdapter.notifyDataSetChanged();
        setRVTitleVisibility(findViewById(R.id.inactive_alerts), inactiveAlerts);
        dismissedIds.add(id);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putStringSet("dismissedIds", dismissedIds).apply();
        if (noAlerts(inactiveAlerts)) setStatus(getStatus());
      }
    };
  }

  public void setRVTitleVisibility(View view, ArrayList<Alert> alerts) {
    if (noAlerts(alerts)) view.setVisibility(View.GONE);
    else view.setVisibility(View.VISIBLE);
  }

  private boolean noAlerts(ArrayList<Alert> alertList) {
    return alertList.size() <= 0;
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
    subscriptionManager.checkPurchases();
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
    if (hasNewAlerts()) promptNewData(getString(R.string.new_data_available));
  }

  private void checkIfLocationIsReasonablyUpToDate() {
    if (lastLocationTime != null && outdated(lastLocationTime)) {
      updateLocationTime();
      startActivity(new Intent(AlertListActivity.this, GettingLocationActivity.class));
    }
  }

  private void updateLocationTime() {
    lastLocationTime = new Date();
  }

  private boolean outdated(Date time) {
    return time.getTime() < new Date().getTime() - Constants.APP_OPENED_LOCATION_EXPIRE;
  }

  private void resumeSubtext() {
    if (subtextFade != null) subtextFade.startNextInterval();
  }

  public void switchLocationClick(View view) {
    boolean isPro = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("is_pro", false);
    if (isPro) {
      ArrayList<com.severeweatheralerts.Location.Location> locations = locationsDao.getLocations();
      ArrayList<String> listItems = new ArrayList<>();
      for (int i = 0; i < locations.size(); i++) {
        if (i == locationIndex) continue;
        listItems.add(locations.get(i).getName());
      }
      listItems.add("Add/Manage Locations");
      final CharSequence[] items = listItems.toArray(new CharSequence[0]);
      new AlertDialog.Builder(this)
              .setTitle("Select a location")
              .setItems(items, (dialogInterface, index) -> {
                if (index == locations.size() - 1)
                  startActivity(new Intent(AlertListActivity.this, ManageLocationsActivity.class));
                else {
                  Intent intent = new Intent(AlertListActivity.this, GettingLatestDataActivity.class);
                  int locIndex = index;
                  if (locIndex >= locationIndex) locIndex++;
                  intent.putExtra("locationIndex", locIndex);
                  startActivity(intent);
                }
              }).create().show();
    }
    else {
      new AlertDialog.Builder(this)
              .setTitle("Upgrade to pro for location switching")
              .setMessage("Add and monitor multiple locations with a pro subscription.")
              .setPositiveButton("Upgrade", (dialogInterface, i) -> startActivity(new Intent(AlertListActivity.this, ProActivity.class)))
              .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
              .show();
    }
  }
}