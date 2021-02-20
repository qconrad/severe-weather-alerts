package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;
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
    StatusPicker statusPicker = new StatusPicker(new ArrayList<>(), new ArrayList<>());
    assertEquals(Clear.class, statusPicker.getStatus().getClass());
  }

  @Test
  public void pickStatus_HasRecentAlerts_StatusIsClearWithRecent() {
    ArrayList<Alert> recent = new ArrayList<>();
    recent.add(new DefaultAlert());
    StatusPicker statusPicker = new StatusPicker(new ArrayList<>(), recent);
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
    assertEquals("Take steps now to protect tender plants from the cold", statusPicker.getStatus().getSubtexts().get(0));
  }

  @Test
  public void pickStatus_ActiveAlerts_SubtextIsDifferentInstruction() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setInstruction("Slow down and use caution while traveling.");
    active.add(defaultAlert);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals("Slow down and use caution while traveling", statusPicker.getStatus().getSubtexts().get(0));
  }

  @Test
  public void pickStatus_ActiveAlerts_InstructionFirstSetence() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setInstruction("Take steps now to protect tender plants from the cold. To prevent freezing and possible bursting");
    active.add(defaultAlert);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals("Take steps now to protect tender plants from the cold", statusPicker.getStatus().getSubtexts().get(0));
  }

  @Test
  public void pickStatus_ActiveAlerts_IconIsAlert() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert = new DefaultAlert();
    active.add(defaultAlert);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals(defaultAlert.getIcon(), statusPicker.getStatus().getIcon());
  }

  @Test
  public void pickStatus_ActiveAlerts_IconIsDifferentAlert() {
    ArrayList<Alert> active = new ArrayList<>();
    WinterStormWarning wsw = new WinterStormWarning();
    active.add(wsw);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals(wsw.getIcon(), statusPicker.getStatus().getIcon());
  }

  @Test
  public void pickStatus_ActiveAlertsWithInstruction_IconIsAlert() {
    ArrayList<Alert> active = new ArrayList<>();
    WinterStormWarning wsw = new WinterStormWarning();
    wsw.setInstruction("test");
    active.add(wsw);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals(wsw.getIcon(), statusPicker.getStatus().getIcon());
  }

  @Test
  public void pickStatus_TwoSentences_SecondStatusCorrect() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setInstruction("Take steps now to protect tender plants from the cold. To prevent freezing and possible bursting");
    active.add(defaultAlert);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals("To prevent freezing and possible bursting", statusPicker.getStatus().getSubtexts().get(1));
  }

  @Test
  public void pickStatus_TwoAlerts_ThirdSubtextCorrect() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert1 = new DefaultAlert();
    defaultAlert1.setInstruction("Take steps now to protect tender plants from the cold. To prevent freezing and possible bursting");
    DefaultAlert defaultAlert2 = new DefaultAlert();
    defaultAlert2.setInstruction("A different instruction here.");
    active.add(defaultAlert1);
    active.add(defaultAlert2);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals("A different instruction here", statusPicker.getStatus().getSubtexts().get(2));
  }

  @Test
  public void TwoAlerts_NoInstruction_NoSubtexts() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert1 = new DefaultAlert();
    DefaultAlert defaultAlert2 = new DefaultAlert();
    active.add(defaultAlert1);
    active.add(defaultAlert2);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals(0, statusPicker.getStatus().getSubtexts().size());
  }

  @Test
  public void getStatus_InstructionHasURL_SubtextsCorrect() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setInstruction("Additional information at weather.gov");
    active.add(defaultAlert);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals("Additional information at weather.gov", statusPicker.getStatus().getSubtexts().get(0));
  }

  @Test
  public void getStatus_LargeHeadlineProvided_SubtextIsLargeHeadline() {
    ArrayList<Alert> active = new ArrayList<>();
    DefaultAlert defaultAlert = new DefaultAlert();
    defaultAlert.setLargeHeadline("Fog developing overnight");
    active.add(defaultAlert);
    StatusPicker statusPicker = new StatusPicker(active, new ArrayList<>());
    assertEquals("Fog developing overnight", statusPicker.getStatus().getSubtexts().get(0));
  }
}
