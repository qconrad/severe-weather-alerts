package com.severeweatheralerts.Notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.severeweatheralerts.MessageAdapter;

public class MessagingService extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    if (remoteMessage.getData().size() > 0) {
      MessageAdapter messageAdapter = new MessageAdapter(remoteMessage.getData());
      new NotificationSender(this, messageAdapter.getAlert(), "high").send();
    }
  }
}
