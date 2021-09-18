package com.severeweatheralerts.Notifications;

import static com.severeweatheralerts.FileDBs.getLocationsDao;
import static com.severeweatheralerts.Preferences.ChannelIdString.getChannelString;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.severeweatheralerts.AlertListTools.AlertFinder;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Networking.LocationPopulaters.FromLocationPointPopulater;
import com.severeweatheralerts.Networking.LocationPopulaters.PopulateCallback;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import java.util.ArrayList;

public class MessageService extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    if (remoteMessage.getData().size() < 1) return;
    LocationsDao.messageReceived();
    if (remoteMessage.getData().containsKey("fetchManually")) fetchAlert(remoteMessage.getData().get("id"));
    else sendAlert(new MessageAdapter(remoteMessage.getData()).getAlert());
  }

  private void fetchAlert(String id) {
    LocationsDao locationsDao = getLocationsDao(this);
    new FromLocationPointPopulater(locationsDao.getDefaultLocation().getCoordinate(), this).populate(new PopulateCallback() {
      @Override
      public void complete(ArrayList<Alert> alerts) {
        locationsDao.getDefaultLocation().setAlerts(alerts);
        Alert alert = new AlertFinder(alerts).findAlertByID(id);
        if (alert != null) sendAlert(alert);
      }
      @Override
      public void error(String message) { }
    });
  }

  private void sendAlert(Alert alert) {
    Channel channel = getLocationsDao(this).getLocation(0).getChannelPreferences().getChannel(alert.getName(), alert.getType());
    if (channel != Channel.NONE) new NotificationSender(this, alert, getChannelString(channel)).send();
  }

  @Override
  public void onNewToken(@NonNull String s) {
    super.onNewToken(s);
    if (!getLocationsDao(this).getDefaultLocation().coordinateSet()) return;
    new UserSyncWorkScheduler(this).oneTimeSync();
  }
}
