package com.severeweatheralerts.Notifications;

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

import static com.severeweatheralerts.Preferences.ChannelIdString.getChannelString;

public class MessageService extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    if (remoteMessage.getData().size() < 1) return;
    LocationsDao.messageReceived();
    if (remoteMessage.getData().containsKey("fetchManually")) fetchAlert(remoteMessage.getData().get("id"));
    else sendAlert(new MessageAdapter(remoteMessage.getData()).getAlert());
  }

  private void fetchAlert(String id) {
    LocationsDao locationsDao = LocationsDao.getInstance(this);
    new FromLocationPointPopulater(locationsDao.getCoordinate(0), this).populate(new PopulateCallback() {
      @Override
      public void complete(ArrayList<Alert> alerts) {
        locationsDao.setAlerts(0, alerts);
        Alert alert = new AlertFinder(alerts).findAlertByID(id);
        if (alert != null) sendAlert(alert);
      }
      @Override
      public void error(String message) { }
    });
  }

  private void sendAlert(Alert alert) {
    Channel channel = LocationsDao.getInstance(this).getChannelPreferences(0).getChannel(alert.getName(), alert.getType());
    if (channel != Channel.NONE) new NotificationSender(this, alert, getChannelString(channel)).send();
  }

  @Override
  public void onNewToken(@NonNull String s) {
    super.onNewToken(s);
    if (LocationsDao.getInstance(this).hasLocations()) new UserSyncWorkScheduler(this).oneTimeSync();
  }
}
