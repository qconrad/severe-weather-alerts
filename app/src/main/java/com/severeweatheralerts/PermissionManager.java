package com.severeweatheralerts;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {
  public static boolean hasLocationPermissions(Context context) {
    return hasBackgroundLocation(context) && hasFineLocation(context) && hasCoarseLocation(context);
  }

  public static void requestLocationPermissions(Activity activity) {
    ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 0);
  }

  public static boolean hasFineLocation(Context context) {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
  }

  public static boolean hasCoarseLocation(Context context) {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
  }

  private static boolean hasBackgroundLocation(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
      return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
    return true;
  }
}
