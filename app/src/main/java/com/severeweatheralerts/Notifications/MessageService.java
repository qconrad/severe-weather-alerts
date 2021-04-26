package com.severeweatheralerts.Notifications;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import static com.severeweatheralerts.Preferences.ChannelIdString.getChannelString;

public class MessageService extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    if (remoteMessage.getData().size() > 0) {
      MessageAdapter messageAdapter = new MessageAdapter(remoteMessage.getData());
      Alert alert = messageAdapter.getAlert();
      Channel channel = LocationsDao.getInstance(this).getChannelPreferences(0).getChannel(alert.getName(), alert.getType());
      if (channel != Channel.NONE) new NotificationSender(this, alert, getChannelString(channel)).send();
    }
  }

  @Override
  public void onNewToken(@NonNull String s) {
    super.onNewToken(s);
    if (LocationsDao.getInstance(this).hasLocations()) new UserSyncWorkScheduler(this).oneTimeSync();
  }
}
