package com.severeweatheralerts.Graphics.Types;

import com.severeweatheralerts.Graphics.Types.AlertArea;
import com.severeweatheralerts.Graphics.Types.GraphicType;

public class ExpectedSnowfall extends AlertArea implements GraphicType {
  public ExpectedSnowfall() {}

  @Override
  public String getTitle() {
    return "Expected Snowfall";
  }
}
