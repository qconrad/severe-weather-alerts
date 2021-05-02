package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.TextGeneraters.Time.AlertTime;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AlertTimeStringGeneratorTests {
  @Test
  public void hasCenterTime_AlertIsCancellation_ReturnsTrue() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.CANCEL);
    AlertTime at = new AlertTime(da, new Date(0));
    assertTrue(at.hasCenterTime());
  }

  @Test
  public void hasCenterTime_AlertIsNotCancellation_ReturnsFalse() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.UPDATE);
    AlertTime at = new AlertTime(da, new Date(0));
    assertFalse(at.hasCenterTime());
  }

  @Test
  public void getCenterTime_AlertIsCancel1MinuteAgo_ReturnsString() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.CANCEL);
    da.setSentTime(new Date(0));
    AlertTime at = new AlertTime(da, new Date(60000));
    assertEquals("Cancelled 1 minute ago", at.getCenterTime());
  }

  @Test
  public void getCenterTime_AlertIsCancel2MinutesAgo_ReturnsString() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.CANCEL);
    da.setSentTime(new Date(0));
    AlertTime atsg = new AlertTime(da, new Date(120000));
    assertEquals("Cancelled 2 minutes ago", atsg.getCenterTime());
  }

  @Test
  public void hasLeftTime_AlertIsCancellation_DoesNotHaveLeftTime() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.CANCEL);
    AlertTime at = new AlertTime(da, new Date(0));
    assertFalse(at.hasLeftTime());
  }

  @Test
  public void hasLeftTime_AlertIsCancellation_HasLeftTime() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.UPDATE);
    AlertTime at = new AlertTime(da, new Date(0));
    assertTrue(at.hasLeftTime());
  }

  @Test
  public void hasLeftTime_AlertIsCancellation_HasRightTime() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.CANCEL);
    AlertTime at = new AlertTime(da, new Date(0));
    assertFalse(at.hasRightTime());
  }

  @Test
  public void hasLeftTime_AlertIsUpdate_HasRightTime() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.UPDATE);
    AlertTime at = new AlertTime(da, new Date(0));
    assertTrue(at.hasRightTime());
  }

  @Test
  public void getLeftTime_IsPost_StringIsCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.POST);
    da.setSentTime(new Date(0));
    AlertTime at = new AlertTime(da, new Date(60000));
    assertEquals("Posted 1 minute ago", at.getLeftTime());
  }

  @Test
  public void getLeftTime_PostFrom2MinutesAgo_StringIsCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.POST);
    da.setSentTime(new Date(0));
    AlertTime at = new AlertTime(da, new Date(120000));
    assertEquals("Posted 2 minutes ago", at.getLeftTime());
  }

  @Test
  public void getLeftTime_UpdateFrom2MinutesAgo_StringIsCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.UPDATE);
    da.setSentTime(new Date(0));
    AlertTime at = new AlertTime(da, new Date(120000));
    assertEquals("Updated 2 minutes ago", at.getLeftTime());
  }

  @Test
  public void getLeftTime_UpdateFrom1MinuteAgo_StringIsCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setType(Alert.Type.UPDATE);
    da.setSentTime(new Date(0));
    AlertTime at = new AlertTime(da, new Date(60000));
    assertEquals("Updated 1 minute ago", at.getLeftTime());
  }

  @Test
  public void getRightTime_ActiveIn1Minute_StringIsCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setStartTime(new Date(60000));
    da.setEndTime(new Date(0));
    AlertTime at = new AlertTime(da, new Date(0));
    assertEquals("Active in 1 minute", at.getRightTime());
  }

  @Test
  public void getRightTime_ActiveIn2Minutes_StringIsCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setStartTime(new Date(120000));
    da.setEndTime(new Date(0));
    AlertTime at = new AlertTime(da, new Date(0));
    assertEquals("Active in 2 minutes", at.getRightTime());
  }

  @Test
  public void getRightTime_Expired1MinuteAgo_StringIsCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setEndTime(new Date(5));
    da.setStartTime(new Date(0));
    AlertTime at = new AlertTime(da, new Date(60000));
    assertEquals("Expired 1 minute ago", at.getRightTime());
  }

  @Test
  public void getRightTime_ActiveNext1Minute_StringIsCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setStartTime(new Date(0));
    da.setEndTime(new Date(60000));
    AlertTime at = new AlertTime(da, new Date(5));
    assertEquals("Active next 1 minute", at.getRightTime());
  }

  @Test
  public void getRightTime_ActiveNext2Minutes_StringIsCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setStartTime(new Date(0));
    da.setEndTime(new Date(120000));
    AlertTime at = new AlertTime(da, new Date(5));
    assertEquals("Active next 2 minutes", at.getRightTime());
  }

  @Test
  public void getRightTime_TimeNotActiveYet_ActiveIn() {
    DefaultAlert da = new DefaultAlert();
    da.setStartTime(new Date(60000));
    da.setEndTime(new Date(120000));
    AlertTime at = new AlertTime(da, new Date(5));
    assertEquals("Active in 1 minute", at.getRightTime());
  }

  @Test
  public void discontinuedAtGiven_HasRightTim() {
    DefaultAlert da = new DefaultAlert();
    da.setDiscontinuedAt(new Date(6000));
    AlertTime at = new AlertTime(da, new Date(7000));
    assertTrue(at.hasRightTime());
  }

  @Test
  public void discontinuedAtGiven_CenterTimeCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setDiscontinuedAt(new Date(0));
    AlertTime at = new AlertTime(da, new Date(120000));
    assertEquals("Exited area 2 minutes ago", at.getRightTime());
  }

  @Test
  public void differentDiscontinuedAtGiven_CenterTimeCorrect() {
    DefaultAlert da = new DefaultAlert();
    da.setDiscontinuedAt(new Date(0));
    AlertTime at = new AlertTime(da, new Date(60000));
    assertEquals("Exited area 1 minute ago", at.getRightTime());
  }
}
