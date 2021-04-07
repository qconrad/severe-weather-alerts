package com.severeweatheralerts.Graphics.Generators;

import com.severeweatheralerts.Graphics.Graphic;

public interface GraphicCompleteListener {
  void onComplete(Graphic graphic);
  void error(String message);
}
