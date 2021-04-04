package com.severeweatheralerts.Adapters;

import java.util.Date;

import static com.severeweatheralerts.TextUtils.DateTimeConverter.convertStringToDate;

public class SendTimeAdapter {
  private final String sendTime;

  public SendTimeAdapter(String sendTime) {
    this.sendTime = sendTime;
  }

  public Date adaptSendTime() {
    return convertStringToDate(sendTime);
  }
}
