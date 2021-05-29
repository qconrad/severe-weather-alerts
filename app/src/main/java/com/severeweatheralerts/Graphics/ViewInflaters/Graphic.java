package com.severeweatheralerts.Graphics.ViewInflaters;

import android.view.View;

public interface Graphic {
  View getView();
  boolean hasSubtext();
  String getSubtext();
}
