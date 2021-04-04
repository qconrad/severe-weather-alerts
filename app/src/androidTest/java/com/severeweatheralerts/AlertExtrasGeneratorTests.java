package com.severeweatheralerts;

import android.content.Intent;

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
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(3000, alertBundleAdapter.addExtras().getExtras().getInt("sent"));
  }

  @Test
  public void differentSent_returnsCorrectSent() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3000));
    tornadoWarning.setEndTime(new Date(2000));
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(4000, alertBundleAdapter.addExtras().getExtras().getInt("sent"));
  }

  @Test
  public void returnsCorrectStart() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3000));
    tornadoWarning.setEndTime(new Date(2000));
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(3000, alertBundleAdapter.addExtras().getExtras().getInt("start"));
  }

  @Test
  public void differentStart_returnsCorrectStart() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(3001, alertBundleAdapter.addExtras().getExtras().getInt("start"));
  }

  @Test
  public void returnsCorrectEnds() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(2000, alertBundleAdapter.addExtras().getExtras().getInt("ends"));
  }
}
