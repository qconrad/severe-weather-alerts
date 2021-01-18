package com.severeweatheralerts.UserSync;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.severeweatheralerts.UserSync.UserSyncWorker;

public class UserSyncWorkScheduler {
  private final Context context;

  public UserSyncWorkScheduler(Context context) {
    this.context = context;
  }

  public void oneTimeSync() {
    Constraints constraints = getConstraints();
    WorkRequest userSync = getWorkRequest(constraints);
    WorkManager.getInstance(context).enqueue(userSync);
  }

  private WorkRequest getWorkRequest(Constraints constraints) {
    return new OneTimeWorkRequest.Builder(UserSyncWorker.class)
            .setConstraints(constraints)
            .build();
  }

  private Constraints getConstraints() {
    return new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build();
  }
}
