package com.severeweatheralerts;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.severeweatheralerts.Activities.GettingLocationActivity;
import com.severeweatheralerts.Activities.LocationPickerActivity;

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
    startActivity(new Intent(FirstRunActivity.this, LocationPickerActivity.class));
  }

  public void thisDevice(View view) {
    if (PermissionManager.hasLocationPermissions(this)) {
      startActivity(new Intent(FirstRunActivity.this, GettingLocationActivity.class));
      updateFirstRun();
    }
    else showDisclaimer();
  }

  private void showDisclaimer() {
    AlertDialog alertDialog = new AlertDialog.Builder(FirstRunActivity.this).create();
    alertDialog.setTitle(getString(R.string.discalimer_title));
    alertDialog.setMessage(getString(R.string.location_disclaimer));
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialog, which) -> PermissionManager.requestLocationPermissions(this));
    alertDialog.show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (PermissionManager.hasLocationPermissions(this)) {
      startActivity(new Intent(FirstRunActivity.this, GettingLocationActivity.class));
      updateFirstRun();
    } else if (PermissionManager.hasFineLocation(this)) {
      AlertDialog alertDialog = new AlertDialog.Builder(FirstRunActivity.this).create();
      alertDialog.setTitle("Are you sure?");
      alertDialog.setMessage("You choose to not allow background location usage. Alerts will still work but will use the location from the last time the app was opened.");
      alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
      alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Allow background location", (dialog, which) -> PermissionManager.requestLocationPermissions(this));
      alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "This is what I want", (dialog, which) -> {
        startActivity(new Intent(FirstRunActivity.this, GettingLocationActivity.class));
        updateFirstRun();
      });
      alertDialog.show();
    } else {
      AlertDialog alertDialog = new AlertDialog.Builder(FirstRunActivity.this).create();
      alertDialog.setTitle("Location Permission Needed");
      alertDialog.setMessage("If the location permission is not accepted, this device's location cannot be determined. You can alternatively enter a custom set location.");
      alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Enter custom location", (dialog, which) -> startActivity(new Intent(FirstRunActivity.this, LocationPickerActivity.class)));
      alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Accept location permission", (dialog, which) -> PermissionManager.requestLocationPermissions(this));
      alertDialog.show();

    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finishAffinity();
  }
}