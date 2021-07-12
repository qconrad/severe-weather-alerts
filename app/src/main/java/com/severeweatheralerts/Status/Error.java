package com.severeweatheralerts.Status;

import android.graphics.Color;

import com.severeweatheralerts.R;

import java.util.ArrayList;

public class Error implements Status {
  protected ArrayList<String> subtexts = new ArrayList<>();

  public Error(String errorMessage) {
    subtexts.add(errorMessage);
  }

  @Override
  public int getColor() {
    return Color.parseColor("#4d0803");
  }

  @Override
  public int getIcon() {
    return R.drawable.error;
  }

  @Override
  public String getHeadline() {
    return "An error has occurred";
  }

  @Override
  public ArrayList<String> getSubtexts() {
    return subtexts;
  }
}
