package com.severeweatheralerts.Adapters;

import java.util.Date;

import static com.severeweatheralerts.TextUtils.DateTimeConverter.convertStringToDate;

public class EndTimeAdapter {
  private final String endTime;
  private final String expireTime;

  public EndTimeAdapter(String endTime, String expireTime) {
    this.endTime = endTime;
    this.expireTime = expireTime;
  }

  public Date adaptEndTime() {
    if (endTimeIsNull()) return convertStringToDate(expireTime);
    else return convertStringToDate(endTime);
  }

  private boolean endTimeIsNull() {
    return endTime == null;
  }
}
