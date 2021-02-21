package com.severeweatheralerts.Activities;

import com.severeweatheralerts.Graphics.Types.AlertArea;
import com.severeweatheralerts.TextGeneraters.Time.ReferenceTime;
import com.severeweatheralerts.TextGeneraters.Time.TimeGenerator;

import java.util.Date;

public class ReferenceViewerActivity extends AlertViewerActivity {
  @Override
  protected void setNextUpdate() { /* Do not show next update times for references */ }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  @Override
  protected void generateGraphics() {
    generateAndDisplayGraphic(new AlertArea());
  }

  @Override
  protected TimeGenerator getTimeGenerator() {
    return new ReferenceTime(al, new Date());
  }
}
