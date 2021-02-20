package com.severeweatheralerts;

import com.severeweatheralerts.AlertListTools.NotActiveFilter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class NotActiveFilterTests {
  @Test
  public void emptyList_StaysEmpty() {
    assertEquals(0, new NotActiveFilter(new ArrayList<Alert>(), null).filter().size());
  }

  @Test
  public void notActive_Filtered() {
    ArrayList<Alert> alerts = new ArrayList<>();;
    DefaultAlert alert = new DefaultAlert();;
    alert.setType(Alert.Type.POST);
    alert.setEndTime(new Date(0));
    alerts.add(alert);
    assertEquals(0, new NotActiveFilter(alerts, new Date(3)).filter().size());
  }

  @Test
  public void active_NotFiltered() {
    ArrayList<Alert> alerts = new ArrayList<>();;
    DefaultAlert alert = new DefaultAlert();;
    alert.setType(Alert.Type.POST);
    alert.setStartTime(new Date(0));
    alert.setEndTime(new Date(2));
    alerts.add(alert);
    assertEquals(1, new NotActiveFilter(alerts, new Date(1)).filter().size());
  }
}
