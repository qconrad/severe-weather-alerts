package com.severeweatheralerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.Activities.GettingLocationActivity;
import com.severeweatheralerts.Activities.LocationPickerActivity;

public class FirstRunActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_first_run);
    updateFirstRun();
  }

  private void updateFirstRun() {
    getSharedPreferences("status", Context.MODE_PRIVATE).edit().putBoolean("first_run", false).apply();
  }

  public void customSet(View view) {
    startActivity(new Intent(FirstRunActivity.this, LocationPickerActivity.class));
  }

  public void thisDevice(View view) {
    if (PermissionManager.hasLocationPermissions(this)) startActivity(new Intent(FirstRunActivity.this, GettingLocationActivity.class));
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
    if (PermissionManager.hasLocationPermissions(this)) startActivity(new Intent(FirstRunActivity.this, GettingLocationActivity.class));
  }
}