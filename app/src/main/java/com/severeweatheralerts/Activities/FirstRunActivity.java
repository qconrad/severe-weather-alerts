package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Location.ConditionalDefaultLocationSync;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.LocationPermissionRequest;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.R;

public class FirstRunActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_first_run);
  }

  private void startAppWithLocation() {
    updateFirstRun();
    startActivity(new Intent(FirstRunActivity.this, GettingLocationActivity.class));
  }

  private void startAppWithFixed() {
    updateFirstRun();
    startActivity(new Intent(FirstRunActivity.this, GettingLatestDataActivity.class));
  }

  public void customSetClick(View view) {
    startActivityForResult(new Intent(FirstRunActivity.this, LocationPickerActivity.class), 1);
  }

  public void thisDeviceClick(View view) {
    if (PermissionManager.hasLocationPermissions(this)) startAppWithLocation();
    else startActivityForResult(new Intent(FirstRunActivity.this, LocationPermissionRequest.class), 0);
  }

  private void updateFirstRun() {
    PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("first_run", false).apply();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != Activity.RESULT_OK) return;
    if (permissionReturn(requestCode)) startAppWithLocation();
    else setFixedLocation(data);
  }

  private boolean permissionReturn(int requestCode) {
    return requestCode == 0;
  }

  private void setFixedLocation(@Nullable Intent data) {
    Bundle extras = data.getExtras();
    LocationsDao.getInstance(this).setDefaultLocation(extras.getString("name"), extras.getDouble("lat"), extras.getDouble("lon"));
    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("usefixed", true).apply();
    new ConditionalDefaultLocationSync(this, extras.getDouble("lat"), extras.getDouble("lon")).sync();
    startAppWithFixed();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finishAffinity();
  }
}