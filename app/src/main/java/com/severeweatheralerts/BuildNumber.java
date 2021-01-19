package com.severeweatheralerts;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class BuildNumber {
  public static int get(Context context) {
    try { return getPackageInfo(context).versionCode; }
    catch (PackageManager.NameNotFoundException e) { return -1; }
  }

  private static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
    return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
  }
}
