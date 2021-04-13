package com.severeweatheralerts.Adapters;

import java.util.Date;

import static com.severeweatheralerts.TextUtils.DateTimeConverter.convertStringToDate;

public class ExpectedUpdateTimeAdapter {
  private final String expireTime;
  private final String endTime;

  public ExpectedUpdateTimeAdapter(String endTime, String expireTime) {
    this.endTime = endTime;
    this.expireTime = expireTime;
  }

  public Date adaptUpdateExpectedTime() {
    if (endTimeIsNull()) return null;
    return convertStringToDate(expireTime);
  }

  private boolean endTimeIsNull() {
    return endTime == null;
  }
}
