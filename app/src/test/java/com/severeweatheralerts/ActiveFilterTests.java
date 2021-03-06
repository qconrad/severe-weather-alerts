package com.severeweatheralerts;

import com.severeweatheralerts.AlertListTools.AlertFilters.ActiveFilter;
import com.severeweatheralerts.AlertListTools.AlertFilters.InactiveFilter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ActiveFilterTests {
  @Test
  public void emptyList_StaysEmpty() {
    assertEquals(0, new InactiveFilter(new ArrayList<Alert>(), null).filter().size());
  }

  @Test
  public void NotActiveFilter_NotActive_NotFiltered() {
    ArrayList<Alert> alerts = new ArrayList<>();;
    DefaultAlert alert = new DefaultAlert();;
    alert.setType(Alert.Type.POST);
    alert.setEndTime(new Date(0));
    alerts.add(alert);
    assertEquals(1, new InactiveFilter(alerts, new Date(3)).filter().size());
  }

  @Test
  public void NotActiveFilter_Active_Filtered() {
    ArrayList<Alert> alerts = new ArrayList<>();;
    DefaultAlert alert = new DefaultAlert();;
    alert.setType(Alert.Type.POST);
    alert.setStartTime(new Date(0));
    alert.setEndTime(new Date(2));
    alerts.add(alert);
    assertEquals(0, new InactiveFilter(alerts, new Date(1)).filter().size());
  }

  @Test
  public void ActiveFilter_Active_NotFiltered() {
    ArrayList<Alert> alerts = new ArrayList<>();;
    DefaultAlert alert = new DefaultAlert();;
    alert.setType(Alert.Type.POST);
    alert.setStartTime(new Date(0));
    alert.setEndTime(new Date(2));
    alerts.add(alert);
    assertEquals(1, new ActiveFilter(alerts, new Date(1)).filter().size());
  }

  @Test
  public void ActiveFilter_NotActive_Filtered() {
    ArrayList<Alert> alerts = new ArrayList<>();;
    DefaultAlert alert = new DefaultAlert();;
    alert.setType(Alert.Type.POST);
    alert.setStartTime(new Date(0));
    alert.setEndTime(new Date(2));
    alerts.add(alert);
    assertEquals(0, new ActiveFilter(alerts, new Date(3)).filter().size());
  }
}
