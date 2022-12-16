package com.severeweatheralerts.UserSync;

import static com.severeweatheralerts.FileDB.getLocationsDao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.severeweatheralerts.Adapters.LocationToCoordinateListAdapter;
import com.severeweatheralerts.Networking.FetchServices.RequestCallback;
import com.severeweatheralerts.Networking.PostService;

public class UserSyncWorker extends Worker {
  private final Context context;

  public UserSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
    this.context = context;
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
    new PostService(context, getURL(), getSyncData(task)).request(new RequestCallback() {
      @Override
      public void success(Object response) { }

      @Override
      public void error(VolleyError error) { }
    });
  }

  private String getSyncData(Task<String> task) {
    return new UserSyncJSONGenerator(getToken(task)).getLocationsString(getLocations());
  }

  protected String getURL() {
    return "https://us-central1-severe-weather-alerts.cloudfunctions.net/usersync";
  }

  private String getLocations() {
    return new JSONLocationString(new LocationToCoordinateListAdapter(getLocationsDao(context).getLocations()).getCoordinates()).getString();
  }

  private boolean failure(Task<String> task) {
    return !task.isSuccessful();
  }
}
