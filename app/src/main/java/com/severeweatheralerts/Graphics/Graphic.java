package com.severeweatheralerts.Graphics;

import android.view.View;

public class Graphic {
  private String subText;
  private View view;

  public void setSubtext(String subText) { this.subText = subText; }
  public String getSubtext() { return subText; }
  public void setView(View view) { this.view = view; }
  public View getView() { return view; }
  public boolean hasSubtext() { return subText != null; }
}
