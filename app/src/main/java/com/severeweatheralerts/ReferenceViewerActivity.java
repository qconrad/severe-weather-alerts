package com.severeweatheralerts;

import android.view.View;
import android.widget.TextView;

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
  protected void setTimes() {
    TextView centerTime = findViewById(R.id.center_time);
    centerTime.setVisibility(View.VISIBLE);
    centerTime.setText(new ReferenceTimeStringGenerator(al, new Date()).getText());
  }
}
