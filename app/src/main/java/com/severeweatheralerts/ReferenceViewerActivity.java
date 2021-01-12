package com.severeweatheralerts;

public class ReferenceViewerActivity extends AlertViewerActivity {
  @Override
  protected void setNextUpdate() { /* Do not show next update times for references */ }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }
}
