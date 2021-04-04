package com.severeweatheralerts;

import android.content.Intent;

import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlertExtrasGeneratorTests {
  @Test
  public void returnsIntent() {
    Intent resultIntent = new Intent();
    TornadoWarning tornadoWarning = new TornadoWarning();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(Intent.class, alertBundleAdapter.addExtras().getClass());
  }

  @Test
  public void returnsCorrectName() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setName("Tornado Warning");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("Tornado Warning", alertBundleAdapter.addExtras().getExtras().getString("name"));
  }

  @Test
  public void differentName_returnsCorrectName() {
    TornadoWatch tornadoWatch = new TornadoWatch();
    tornadoWatch.setName("Tornado Watch");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWatch, resultIntent);;
    assertEquals("Tornado Watch", alertBundleAdapter.addExtras().getExtras().getString("name"));
  }

  @Test
  public void returnsCorrectIntent() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setName("Tornado Warning");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(resultIntent.hashCode(), alertBundleAdapter.addExtras().hashCode());
  }
}
