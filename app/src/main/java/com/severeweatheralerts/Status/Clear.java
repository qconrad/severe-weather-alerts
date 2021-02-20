package com.severeweatheralerts.Status;

import android.graphics.Color;

import com.severeweatheralerts.R;

public class Clear implements Status {
  @Override
  public int getColor() {
    return Color.parseColor("#00AC46");
  }

  @Override
  public int getIcon() {
    return R.drawable.sun;
  }

  @Override
  public String getHeadline() {
    return "You're in the clear!";
  }

  @Override
  public String getSubtext() {
    return "There are no active alerts for this location. When hazardous weather is expected, a push notification will be sent and alerts will snow up here.";
  }
}
