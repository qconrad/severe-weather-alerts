package com.severeweatheralerts;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.severeweatheralerts.Location.LocationsDao;

public class UserSyncWorker extends Worker {
  public UserSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
  }

  public void syncLocation() {
    FirebaseMessaging.getInstance().getToken()
    .addOnCompleteListener(this::onComplete);
  }

  private String getToken(Task<String> task) {
    return task.getResult();
  }

  private void onComplete(Task<String> task) {
    if (failure(task)) return;
    String syncData = new UserSyncJSONGenerator(getToken(task)).getLocationsString(getLocations());
    new AsyncPost().execute("https://us-central1-severe-weather-alerts.cloudfunctions.net/userupdate", syncData);
  }

  private String getLocations() {
    return new JSONLocationString(LocationsDao.getLocationList()).getString();
  }

  private boolean failure(Task<String> task) {
    return !task.isSuccessful();
  }

  @NonNull
  @Override
  public Result doWork() {
    syncLocation();
    return Result.success();
  }
}
