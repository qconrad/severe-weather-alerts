package com.severeweatheralerts.Activities;

import com.severeweatheralerts.Status.Error;
import com.severeweatheralerts.Status.Status;

public class ErrorActivity extends AlertListActivity {
  @Override
  protected Status getStatus() {
    return new Error(getIntent().getExtras().getString("errorMessage"));
  }
}