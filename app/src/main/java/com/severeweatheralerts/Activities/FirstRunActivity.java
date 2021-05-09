package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Location.ConditionalDefaultLocationSync;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.PermissionManager;
import com.severeweatheralerts.R;

public class FirstRunActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_first_run);
  }

  private void updateFirstRun() {
    PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("first_run", false).apply();
  }

  public void customSet(View view) {
    startActivityForResult(new Intent(FirstRunActivity.this, LocationPickerActivity.class), 0);
  }

  public void thisDevice(View view) {
    if (PermissionManager.hasLocationPermissions(this)) startApp();
    else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) showDisclaimer();
    else PermissionManager.requestLocationPermissions(this);
  }

  private void startApp() {
    startActivity(new Intent(FirstRunActivity.this, GettingLocationActivity.class));
    updateFirstRun();
  }

  private void showDisclaimer() {
    AlertDialog alertDialog = new AlertDialog.Builder(FirstRunActivity.this).create();
    alertDialog.setTitle(getString(R.string.disclaimer_title));
    alertDialog.setMessage(getString(R.string.location_disclaimer));
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialog, which) -> PermissionManager.requestLocationPermissions(this));
    alertDialog.show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (PermissionManager.hasLocationPermissions(this)) {
      startApp();
    } else if (PermissionManager.hasFineLocation(this)) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        AlertDialog alertDialog = new AlertDialog.Builder(FirstRunActivity.this).create();
        alertDialog.setTitle(getString(R.string.disclaimer_title));
        alertDialog.setMessage("To receive alerts for the correct location as your device moves, background location access is needed. Otherwise, alerts will be sent for the location the device was at when this app was last opened. Would you like to grant background access?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Use last open location", (dialog, which) -> {
          startApp();
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Grant access to background location", (dialog, which) -> PermissionManager.requestBackgroundPermissions(this));
        alertDialog.show();
      } else {
        AlertDialog alertDialog = new AlertDialog.Builder(FirstRunActivity.this).create();
        alertDialog.setTitle(getString(R.string.background_deny_title));
        alertDialog.setMessage(getString(R.string.background_deny_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Allow background location", (dialog, which) -> PermissionManager.requestLocationPermissions(this));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "This is what I want", (dialog, which) -> {
          startApp();
        });
        alertDialog.show();
      }
    } else {
      AlertDialog alertDialog = new AlertDialog.Builder(FirstRunActivity.this).create();
      alertDialog.setTitle(getString(R.string.location_deny_title));
      alertDialog.setMessage(getString(R.string.location_deny_message));
      alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Enter fixed location", (dialog, which) ->
              startActivityForResult(new Intent(FirstRunActivity.this, LocationPickerActivity.class), 0));
      alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Accept location permission", (dialog, which) ->
              PermissionManager.requestLocationPermissions(this));
      alertDialog.show();

    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      Bundle extras = data.getExtras();
      LocationsDao.getInstance(this).setDefaultLocation(extras.getString("name"), extras.getDouble("lat"), extras.getDouble("lon"));
      PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("usefixed", true).apply();
      new ConditionalDefaultLocationSync(this, extras.getDouble("lat"), extras.getDouble("lon")).sync();
      startActivity(new Intent(FirstRunActivity.this, GettingLatestDataActivity.class));
      updateFirstRun();
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finishAffinity();
  }
}