package com.severeweatheralerts;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.severeweatheralerts.Location.LocationsDao;

public class UserSync {

  public UserSync() { }

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
    System.out.println(syncData);
  }

  private String getLocations() {
    return new JSONLocationString(LocationsDao.getLocationList()).getString();
  }

  private boolean failure(Task<String> task) {
    return !task.isSuccessful();
  }
}
