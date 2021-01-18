package com.severeweatheralerts.UserSync;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.severeweatheralerts.Networking.AsyncPost;
import com.severeweatheralerts.Location.LocationsDao;

public class UserSyncWorker extends Worker {
  public UserSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
  }

  @NonNull
  @Override
  public Result doWork() {
    syncLocation();
    return Result.success();
  }

  private void syncLocation() {
    getToken(this::postToken);
  }

  private void getToken(OnCompleteListener<String> completeListener) {
    FirebaseMessaging.getInstance().getToken()
    .addOnCompleteListener(completeListener);
  }

  private String getToken(Task<String> task) {
    return task.getResult();
  }

  private void postToken(Task<String> task) {
    if (failure(task)) return;
    new AsyncPost(getUrl(), getSyncData(task)).execute();
  }

  private String getSyncData(Task<String> task) {
    return new UserSyncJSONGenerator(getToken(task)).getLocationsString(getLocations());
  }

  protected String getUrl() {
    return "https://us-central1-severe-weather-alerts.cloudfunctions.net/userupdate";
  }

  private String getLocations() {
    return new JSONLocationString(LocationsDao.getLocationList()).getString();
  }

  private boolean failure(Task<String> task) {
    return !task.isSuccessful();
  }
}
