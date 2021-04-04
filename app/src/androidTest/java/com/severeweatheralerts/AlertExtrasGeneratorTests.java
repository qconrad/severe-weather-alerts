package com.severeweatheralerts;

import android.content.Intent;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AlertExtrasGeneratorTests {
  @Test
  public void returnsIntent() {
    Intent resultIntent = new Intent();
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(3000));
    tornadoWarning.setStartTime(new Date(3000));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.POST);
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(Intent.class, alertBundleAdapter.addExtras().getClass());
  }

  @Test
  public void returnsCorrectName() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setName("Tornado Warning");
    tornadoWarning.setSentTime(new Date(3000));
    tornadoWarning.setStartTime(new Date(3000));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.POST);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("Tornado Warning", alertBundleAdapter.addExtras().getExtras().getString("name"));
  }

  @Test
  public void differentName_returnsCorrectName() {
    TornadoWatch tornadoWatch = new TornadoWatch();
    tornadoWatch.setName("Tornado Watch");
    tornadoWatch.setSentTime(new Date(3000));
    tornadoWatch.setStartTime(new Date(3000));
    tornadoWatch.setEndTime(new Date(2000));
    tornadoWatch.setType(Alert.Type.POST);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWatch, resultIntent);;
    assertEquals("Tornado Watch", alertBundleAdapter.addExtras().getExtras().getString("name"));
  }

  @Test
  public void returnsCorrectIntent() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setName("Tornado Warning");
    tornadoWarning.setSentTime(new Date(3000));
    tornadoWarning.setStartTime(new Date(3000));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.POST);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(resultIntent.hashCode(), alertBundleAdapter.addExtras().hashCode());
  }

  @Test
  public void returnsCorrectSent() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(3000));
    tornadoWarning.setStartTime(new Date(3000));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.POST);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(3000L, alertBundleAdapter.addExtras().getExtras().getLong("sent"));
  }

  @Test
  public void differentSent_returnsCorrectSent() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3000));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.POST);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(4000L, alertBundleAdapter.addExtras().getExtras().getLong("sent"));
  }

  @Test
  public void returnsCorrectStart() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3000));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.POST);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(3000L, alertBundleAdapter.addExtras().getExtras().getLong("start"));
  }

  @Test
  public void differentStart_returnsCorrectStart() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.POST);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(3001L, alertBundleAdapter.addExtras().getExtras().getLong("start"));
  }

  @Test
  public void returnsCorrectEnds() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.POST);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(2000L, alertBundleAdapter.addExtras().getExtras().getLong("ends"));
  }

  @Test
  public void returnsSenderCode() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setType(Alert.Type.POST);
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setSenderCode("lot");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("lot", alertBundleAdapter.addExtras().getExtras().getString("senderCode"));
  }

  @Test
  public void returnsType() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.UPDATE);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("UPDATE", alertBundleAdapter.addExtras().getExtras().getString("type"));
  }

  @Test
  public void returnsDifferentType() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("CANCEL", alertBundleAdapter.addExtras().getExtras().getString("type"));
  }

  @Test
  public void returnsDescription() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setDescription("Test");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("Test", alertBundleAdapter.addExtras().getExtras().getString("description"));
  }

  @Test
  public void returnsDifferentDescription() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setDescription("Different Test");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("Different Test", alertBundleAdapter.addExtras().getExtras().getString("description"));
  }

  @Test
  public void returnsLargeHeadline() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setLargeHeadline("Large Headline");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("Large Headline", alertBundleAdapter.addExtras().getExtras().getString("largeHeadline"));
  }

  @Test
  public void returnsDifferentLargeHeadline() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setLargeHeadline("Different Large Headline");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("Different Large Headline", alertBundleAdapter.addExtras().getExtras().getString("largeHeadline"));
  }

  @Test
  public void returnsSmallHeadline() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setSmallHeadline("Small Headline");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("Small Headline", alertBundleAdapter.addExtras().getExtras().getString("smallHeadline"));
  }

  @Test
  public void returnsDifferentSmallHeadline() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setSmallHeadline("Different Small Headline");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("Different Small Headline", alertBundleAdapter.addExtras().getExtras().getString("smallHeadline"));
  }

  @Test
  public void returnsInstruction() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setInstruction("Instruction");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("Instruction", alertBundleAdapter.addExtras().getExtras().getString("instruction"));
  }

  @Test
  public void returnsDifferentInstruction() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setInstruction("Different Instruction");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("Different Instruction", alertBundleAdapter.addExtras().getExtras().getString("instruction"));
  }
}
