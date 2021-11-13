package com.severeweatheralerts.Notifications;

import static com.severeweatheralerts.FileDB.getLocationsDao;
import static com.severeweatheralerts.Preferences.ChannelIdString.getChannelString;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.severeweatheralerts.AlertListTools.AlertFinder;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Networking.LocationPopulaters.FromLocationPointPopulater;
import com.severeweatheralerts.Networking.LocationPopulaters.PopulateCallback;
import com.severeweatheralerts.NewAlerts;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import java.util.ArrayList;

public class MessageService extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    if (remoteMessage.getData().size() < 1) return;
    NewAlerts.alertReceived();
    MessageAdapter message = new MessageAdapter(remoteMessage.getData());
    if (notificationsDisabled(message.getLocationIndex())) return;
    if (message.fetchManually()) fetchAlert(message.getFetchManuallyID(), message.getLocationIndex());
    else sendAlert(message.getAlert(), message.getLocationIndex());
  }

  private boolean notificationsDisabled(int locationIndex) {
    return getLocationsDao(this).getLocation(locationIndex).isNotifying();
  }

  private void fetchAlert(String id, int locationIndex) {
    LocationsDao locationsDao = getLocationsDao(this);
    new FromLocationPointPopulater(locationsDao.getLocation(locationIndex).getCoordinate(), this).populate(new PopulateCallback() {
      @Override
      public void complete(ArrayList<Alert> alerts) {
        locationsDao.getDefaultLocation().setAlerts(alerts);
        Alert alert = new AlertFinder(alerts).findAlertByID(id);
        if (alert != null) sendAlert(alert, locationIndex);
      }
      @Override
      public void error(String message) {}
    });
  }

  private void sendAlert(Alert alert, int locationIndex) {
    sendNotification(alert, getChannel(alert, locationIndex), locationIndex);
  }

  private void sendNotification(Alert alert, Channel channel, int locationIndex) {
    if (channel == Channel.NONE) return;
    new NotificationSender(this, alert, getChannelString(channel), locationIndex).send();
  }

  private Channel getChannel(Alert alert, int locationIndex) {
    return getLocationsDao(this).getLocation(locationIndex).getChannelPreferences().getChannel(alert.getName(), alert.getType());
  }

  @Override
  public void onNewToken(@NonNull String s) {
    super.onNewToken(s);
    if (!getLocationsDao(this).getDefaultLocation().coordinateSet()) return;
    new UserSyncWorkScheduler(this).oneTimeSync();
  }
}
