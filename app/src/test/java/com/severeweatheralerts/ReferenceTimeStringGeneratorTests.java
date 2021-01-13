package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.TextGeneraters.Time.ReferenceTime;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReferenceTimeStringGeneratorTests {
  @Test
  public void getCenterTime_TypeIsPost_ShowCorrectText() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.POST);
    da.setSentTime(new Date(0));
    ReferenceTime rtsg = new ReferenceTime(da, new Date(60000));
    String expectedString = "Original post from 1 minute ago";
    assertEquals(expectedString, rtsg.getCenterTime());
  }

  @Test
  public void getCenterTime_TypeIsUpdate_ShowCorrectText() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.UPDATE);
    da.setSentTime(new Date(0));
    ReferenceTime rtsg = new ReferenceTime(da, new Date(60000));
    String expectedString = "Update from 1 minute ago";
    assertEquals(expectedString, rtsg.getCenterTime());
  }

  @Test
  public void getCenterTime_UpdateFrom2MinutesAgo_ShowCorrectText() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.UPDATE);
    da.setSentTime(new Date(0));
    ReferenceTime rtsg = new ReferenceTime(da, new Date(120000));
    String expectedString = "Update from 2 minutes ago";
    assertEquals(expectedString, rtsg.getCenterTime());
  }

  @Test
  public void getCenterTime_PostFrom2MinutesAgo_ShowCorrectText() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.POST);
    da.setSentTime(new Date(0));
    ReferenceTime rtsg = new ReferenceTime(da, new Date(120000));
    String expectedString = "Original post from 2 minutes ago";
    assertEquals(expectedString, rtsg.getCenterTime());
  }

  @Test
  public void getCenterTime_ReferenceGiven_ReferencesAlwaysHaveCenter() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.POST);
    da.setSentTime(new Date(0));
    ReferenceTime rtsg = new ReferenceTime(da, new Date(120000));
    assertTrue(rtsg.hasCenterTime());
  }
}
