package com.severeweatheralerts.Adapters;

import java.util.Date;

import static com.severeweatheralerts.TextUtils.DateTimeConverter.convertStringToDate;

public class StartTimeAdapter {
  private String onsetTime;

  public StartTimeAdapter(String onsetTime) {
    this.onsetTime = onsetTime;
  }

  public Date adaptStartTime() {
    return convertStringToDate(onsetTime);
  }
}
