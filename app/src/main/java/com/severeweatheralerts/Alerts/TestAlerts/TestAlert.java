package com.severeweatheralerts.Alerts.TestAlerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.R;

import java.util.Date;

public abstract class TestAlert extends Alert {
  public TestAlert() {
    Date curTime = new Date();
    setSentTime(curTime);
    setStartTime(curTime);
    setType(Type.POST);
    setSenderCode("");
    setSender("National Weather Service");
    setLargeHeadline("This Is A Test Message");
    setDescription("If this had been an actual emergency or threat, detailed information about the hazard as well as instructions on how to protect life and property would appear here.");
    setEndTime(new Date(curTime.getTime() + 600000));
    setInstruction("Check to make sure notifications appear and sound according to your preferences for this channel.\n\nRemember, this is only a test. No action is needed.");
  }

  @Override
  public int getIcon() {
    return R.drawable.test;
  }
}
