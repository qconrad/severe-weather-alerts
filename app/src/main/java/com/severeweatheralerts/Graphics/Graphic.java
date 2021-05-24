package com.severeweatheralerts.Graphics;

import android.view.View;

public interface Graphic {
  View getView();
  boolean hasSubtext();
  String getSubtext();
}
