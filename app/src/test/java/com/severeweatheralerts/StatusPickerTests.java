package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StatusPickerTests {
  @Test
  public void pickStatus_NoAlerts_StatusIsClear() {
    StatusPicker statusPicker = new StatusPicker(new ArrayList<Alert>(), new ArrayList<Alert>());
    assertEquals(Clear.class, statusPicker.getStatus().getClass());
  }

  @Test
  public void pickStatus_HasRecentAlerts_StatusIsClearWithRecent() {
    ArrayList<Alert> recent = new ArrayList<>();
    recent.add(new DefaultAlert());
    StatusPicker statusPicker = new StatusPicker(new ArrayList<Alert>(), recent);
    assertEquals(ClearWithRecent.class, statusPicker.getStatus().getClass());
  }
}
