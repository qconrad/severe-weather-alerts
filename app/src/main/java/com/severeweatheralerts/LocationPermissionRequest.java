package com.severeweatheralerts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.Activities.GettingLatestDataActivity;
import com.severeweatheralerts.Activities.LocationPickerActivity;

public class LocationPermissionRequest extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) showDisclaimer();
    else PermissionManager.requestLocationPermissions(this);
  }

  private void showDisclaimer() {
    AlertDialog alertDialog = new AlertDialog.Builder(LocationPermissionRequest.this).create();
    alertDialog.setOnCancelListener(dialogInterface -> finish());
    alertDialog.setTitle(getString(R.string.disclaimer_title));
    alertDialog.setMessage(getString(R.string.location_disclaimer));
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> finish());
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialog, which) -> PermissionManager.requestLocationPermissions(this));
    alertDialog.show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (PermissionManager.hasLocationPermissions(this)) returnSuccess();
    else promptUser();
  }

  private void promptUser() {
    AlertDialog alertDialog = new AlertDialog.Builder(LocationPermissionRequest.this).create();
    if (!PermissionManager.hasFineLocation(this)) locationDeniedDialog(alertDialog);
    else hasLocationAccess(alertDialog);
    alertDialog.setOnCancelListener(dialogInterface -> finish());
    alertDialog.show();
  }

  private void hasLocationAccess(AlertDialog alertDialog) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) backgroundRequestDialog(alertDialog);
    else backgroundDeniedDialog(alertDialog);
  }

  private void locationDeniedDialog(AlertDialog alertDialog) {
    alertDialog.setTitle(getString(R.string.location_deny_title));
    alertDialog.setMessage(getString(R.string.location_deny_message));
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Enter fixed location", (dialog, which) ->
            startActivityForResult(new Intent(LocationPermissionRequest.this, LocationPickerActivity.class), 0));
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Accept location permission", (dialog, which) ->
            PermissionManager.requestLocationPermissions(this));
  }

  private void backgroundDeniedDialog(AlertDialog alertDialog) {
    alertDialog.setTitle(getString(R.string.background_deny_title));
    alertDialog.setMessage(getString(R.string.background_deny_message));
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> finish());
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Allow background location", (dialog, which) -> PermissionManager.requestLocationPermissions(this));
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "This is what I want", (dialog, which) -> returnSuccess());
  }

  private void backgroundRequestDialog(AlertDialog alertDialog) {
    alertDialog.setTitle(getString(R.string.disclaimer_title));
    alertDialog.setMessage("To receive alerts for the correct location as your device moves, background location access is needed. Otherwise, alerts will be sent for the location the device was at when this app was last opened. Would you like to grant background access?");
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Use last open location", (dialog, which) -> returnSuccess());
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Grant access to background location", (dialog, which) -> PermissionManager.requestBackgroundPermissions(this));
  }

  private void returnSuccess() {
    setResult(Activity.RESULT_OK, new Intent());
    finish();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != Activity.RESULT_OK) finish();
    else useFixed(data);
  }

  private void useFixed(@Nullable Intent data) {
    new BundledLocation(this, data).setFixedLocation();
    startActivity(new Intent(LocationPermissionRequest.this, GettingLatestDataActivity.class));
  }
}