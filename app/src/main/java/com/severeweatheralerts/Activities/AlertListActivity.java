package com.severeweatheralerts.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.AlertListTools.AlertFilters.ActiveFilter;
import com.severeweatheralerts.AlertListTools.AlertFilters.InactiveFilter;
import com.severeweatheralerts.AlertListTools.AlertFilters.ReplacementFilter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Alert.AlertCardHolder;
import com.severeweatheralerts.RecyclerViews.Alert.AlertRecyclerViewAdapter;
import com.severeweatheralerts.Status.Status;
import com.severeweatheralerts.Status.StatusPicker;
import com.severeweatheralerts.TextListFade;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import java.util.ArrayList;
import java.util.Date;

public class AlertListActivity extends AppCompatActivity {
  private ArrayList<Alert> relevantAlerts;
  private ArrayList<Alert> activeAlerts;
  private ArrayList<Alert> inactiveAlerts;
  private Status status;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alertlist);
    relevantAlerts = new ReplacementFilter(LocationsDao.getLocation(0).getAlerts()).filter();
    activeAlerts = new ActiveFilter(relevantAlerts, new Date()).filter();
    inactiveAlerts = new InactiveFilter(relevantAlerts, new Date()).filter();
    status = new StatusPicker(activeAlerts, inactiveAlerts).getStatus();
    populateRecyclerViews();
    setStatus();
    userSync();
  }

  private void setStatus() {
    setBackgroundColor();
    setStatusHeadline();
    setStatusSubtext();
    setStatusIcon();
  }

  private void setStatusSubtext() {
    new TextListFade(this, status.getSubtexts(), findViewById(R.id.status_switcher)).beginFade();
  }

  private void setStatusHeadline() {
    TextView headline = findViewById(R.id.status_headline);
    headline.setText(status.getHeadline());
  }

  private void setStatusIcon() {
    ImageView icon = findViewById(R.id.status_icon);
    icon.setImageResource(status.getIcon());
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
    ArrayList<Alert> activeAlerts = new ActiveFilter(relevantAlerts, new Date()).filter();
    if (activeAlerts.size() > 0) {
      findViewById(R.id.active_alerts).setVisibility(View.VISIBLE);
      populateRecyclerView(findViewById(R.id.active_alerts_rv), activeAlerts);
    }
  }

  private void populateRecentRecyclerView() {
    ArrayList<Alert> inactiveAlerts = new InactiveFilter(relevantAlerts, new Date()).filter();
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

  private void setBackgroundColor() {
    int situationSeverityColor = status.getColor();
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

  public void settingsClick(View view) {
    Toast.makeText(this, "Settings clicke", Toast.LENGTH_SHORT).show();
  }
}