package com.severeweatheralerts;

import android.os.Bundle;

import com.severeweatheralerts.Alerts.Alert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BundleAlertAdapterTests {
  @Test
  public void returnsAlert() {
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(new Bundle());
    assertNotNull(bundleAlertAdapter.getAlert());
  }

  @Test
  public void returnsName() {
    Bundle bundle = new Bundle();
    bundle.putString("name", "Tornado Warning");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("Tornado Warning", bundleAlertAdapter.getAlert().getName());
  }

  @Test
  public void returnsDifferentName() {
    Bundle bundle = new Bundle();
    bundle.putString("name", "Tornado Watch");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("Tornado Watch", bundleAlertAdapter.getAlert().getName());
  }

  @Test
  public void returnsCorrectSent() {
    Bundle bundle = new Bundle();
    bundle.putInt("sent", 3000);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(3000, bundleAlertAdapter.getAlert().getSentTime().getTime());
  }

  @Test
  public void sentDifferent_returnsCorrectSent() {
    Bundle bundle = new Bundle();
    bundle.putInt("sent", 4000);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(4000, bundleAlertAdapter.getAlert().getSentTime().getTime());
  }

  @Test
  public void returnsStart() {
    Bundle bundle = new Bundle();
    bundle.putInt("start", 3000);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(3000, bundleAlertAdapter.getAlert().getStartTime().getTime());
  }

  @Test
  public void startDifferent_returnsStart() {
    Bundle bundle = new Bundle();
    bundle.putInt("start", 4000);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(4000, bundleAlertAdapter.getAlert().getStartTime().getTime());
  }

  @Test
  public void returnsEnd() {
    Bundle bundle = new Bundle();
    bundle.putInt("ends", 3000);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(3000, bundleAlertAdapter.getAlert().getEndTime().getTime());
  }

  @Test
  public void differentEnd_returnsEnd() {
    Bundle bundle = new Bundle();
    bundle.putInt("ends", 4000);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(4000, bundleAlertAdapter.getAlert().getEndTime().getTime());
  }

  @Test
  public void senderCode() {
    Bundle bundle = new Bundle();
    bundle.putString("senderCode", "lot");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("lot", bundleAlertAdapter.getAlert().getSenderCode());
  }

  @Test
  public void differentSenderCodeParsed() {
    Bundle bundle = new Bundle();
    bundle.putString("senderCode", "ilx");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("ilx", bundleAlertAdapter.getAlert().getSenderCode());
  }

  @Test
  public void typeParsed() {
    Bundle bundle = new Bundle();
    bundle.putString("type", "UPDATE");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(Alert.Type.UPDATE, bundleAlertAdapter.getAlert().getType());
  }

  @Test
  public void differentTypeParsed() {
    Bundle bundle = new Bundle();
    bundle.putString("type", "CANCEL");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(Alert.Type.CANCEL, bundleAlertAdapter.getAlert().getType());
  }
}
