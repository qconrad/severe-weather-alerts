package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Status.ActiveAlerts;
import com.severeweatheralerts.Status.Clear;
import com.severeweatheralerts.Status.ClearWithRecent;
import com.severeweatheralerts.Status.StatusPicker;

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

  @Test
  public void pickStatus_ActiveAlerts_StatusIsActive() {
    ArrayList<Alert> active = new ArrayList<>();
    active.add(new DefaultAlert());
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals(ActiveAlerts.class, statusPicker.getStatus().getClass());
  }

  @Test
  public void pickStatus_ActiveAlerts_SubtextIsInstruction() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setInstruction("Take steps now to protect tender plants from the cold.");
    active.add(defaultAlert);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals("Take steps now to protect tender plants from the cold", statusPicker.getStatus().getSubtext());
  }

  @Test
  public void pickStatus_ActiveAlerts_SubtextIsDifferentInstruction() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setInstruction("Slow down and use caution while traveling.");
    active.add(defaultAlert);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals("Slow down and use caution while traveling", statusPicker.getStatus().getSubtext());
  }

  @Test
  public void pickStatus_ActiveAlerts_InstructionFirstSetence() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setInstruction("Take steps now to protect tender plants from the cold. To prevent freezing and possible bursting");
    active.add(defaultAlert);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals("Take steps now to protect tender plants from the cold", statusPicker.getStatus().getSubtext());
  }
}
