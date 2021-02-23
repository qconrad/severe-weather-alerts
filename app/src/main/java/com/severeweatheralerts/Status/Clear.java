package com.severeweatheralerts.Status;

import android.graphics.Color;

import com.severeweatheralerts.R;

import java.util.ArrayList;

public class Clear implements Status {
  protected ArrayList<String> subtexts = new ArrayList<>();
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
  public ArrayList<String> getSubtexts() {
    subtexts.add("There are no active alerts for this location. When hazardous weather is expected, a push notification will be sent and alerts will show up here.");
    return subtexts;
  }
}
