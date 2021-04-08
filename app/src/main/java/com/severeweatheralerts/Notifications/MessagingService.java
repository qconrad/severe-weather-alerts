package com.severeweatheralerts.Notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.MessageAdapter;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;

import static com.severeweatheralerts.Preferences.ChannelIdString.getChannelString;

public class MessagingService extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    if (remoteMessage.getData().size() > 0) {
      MessageAdapter messageAdapter = new MessageAdapter(remoteMessage.getData());
      ChannelPreferences channelPreferences = new ChannelPreferences();
      Alert alert = messageAdapter.getAlert();
      Channel channel = channelPreferences.getChannel(alert.getName(), alert.getType());
      if (channel != Channel.NONE) new NotificationSender(this, alert, getChannelString(channel)).send();
    }
  }
}
