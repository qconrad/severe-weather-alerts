package com.severeweatheralerts;

import android.os.Bundle;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;
import com.severeweatheralerts.Adapters.BundleAlertAdapter;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
    bundle.putLong("sent", 3000L);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(3000L, bundleAlertAdapter.getAlert().getSentTime().getTime());
  }

  @Test
  public void sentDifferent_returnsCorrectSent() {
    Bundle bundle = new Bundle();
    bundle.putLong("sent", 4000L);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(4000L, bundleAlertAdapter.getAlert().getSentTime().getTime());
  }

  @Test
  public void returnsStart() {
    Bundle bundle = new Bundle();
    bundle.putLong("start", 3000L);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(3000L, bundleAlertAdapter.getAlert().getStartTime().getTime());
  }

  @Test
  public void startDifferent_returnsStart() {
    Bundle bundle = new Bundle();
    bundle.putLong("start", 4000L);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(4000L, bundleAlertAdapter.getAlert().getStartTime().getTime());
  }

  @Test
  public void returnsEnd() {
    Bundle bundle = new Bundle();
    bundle.putLong("ends", 3000L);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(3000L, bundleAlertAdapter.getAlert().getEndTime().getTime());
  }

  @Test
  public void differentEnd_returnsEnd() {
    Bundle bundle = new Bundle();
    bundle.putLong("ends", 4000L);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(4000L, bundleAlertAdapter.getAlert().getEndTime().getTime());
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

  @Test
  public void returnsDescription() {
    Bundle bundle = new Bundle();
    bundle.putString("description", "Test");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("Test", bundleAlertAdapter.getAlert().getDescription());
  }

  @Test
  public void returnsDifferentDescription() {
    Bundle bundle = new Bundle();
    bundle.putString("description", "Different Test");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("Different Test", bundleAlertAdapter.getAlert().getDescription());
  }

  @Test
  public void returnsCorrectType() {
    Bundle bundle = new Bundle();
    bundle.putString("name", "Tornado Warning");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(TornadoWarning.class, bundleAlertAdapter.getAlert().getClass());
  }

  @Test
  public void returnsDifferentType() {
    Bundle bundle = new Bundle();
    bundle.putString("name", "Tornado Watch");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(TornadoWatch.class, bundleAlertAdapter.getAlert().getClass());
  }

  @Test
  public void returnsLargeHeadline() {
    Bundle bundle = new Bundle();
    bundle.putString("largeHeadline", "Large Headline");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("Large Headline", bundleAlertAdapter.getAlert().getLargeHeadline());
  }

  @Test
  public void returnsDifferentLargeHeadline() {
    Bundle bundle = new Bundle();
    bundle.putString("largeHeadline", "Different Large Headline");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("Different Large Headline", bundleAlertAdapter.getAlert().getLargeHeadline());
  }

  @Test
  public void returnsSmallHeadline() {
    Bundle bundle = new Bundle();
    bundle.putString("smallHeadline", "Small Headline");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("Small Headline", bundleAlertAdapter.getAlert().getSmallHeadline());
  }

  @Test
  public void returnsDifferentSmallHeadline() {
    Bundle bundle = new Bundle();
    bundle.putString("smallHeadline", "Different Small Headline");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("Different Small Headline", bundleAlertAdapter.getAlert().getSmallHeadline());
  }

  @Test
  public void returnsInstruction() {
    Bundle bundle = new Bundle();
    bundle.putString("instruction", "Instruction");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("Instruction", bundleAlertAdapter.getAlert().getInstruction());
  }

  @Test
  public void returnsDifferentInstruction() {
    Bundle bundle = new Bundle();
    bundle.putString("instruction", "Different Instruction");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("Different Instruction", bundleAlertAdapter.getAlert().getInstruction());
  }

  @Test
  public void returnsSender() {
    Bundle bundle = new Bundle();
    bundle.putString("sender", "NWS Chicago IL");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("NWS Chicago IL", bundleAlertAdapter.getAlert().getSender());
  }

  @Test
  public void returnsDifferentSender() {
    Bundle bundle = new Bundle();
    bundle.putString("sender", "NWS Lincoln IL");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("NWS Lincoln IL", bundleAlertAdapter.getAlert().getSender());
  }

  @Test
  public void returnsZoneLink() {
    Bundle bundle = new Bundle();
    ArrayList<String> zones = new ArrayList<>();
    zones.add("zone");
    bundle.putStringArrayList("zones", zones);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(1, bundleAlertAdapter.getAlert().getZoneLinkCount());
  }

  @Test
  public void returnsPolygonCount() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[25.0, 25.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(1, bundleAlertAdapter.getAlert().getPolygonCount());
  }

  @Test
  public void returnsTwoPolygons() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[25.0, 25.0]],[[25.0, 25.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(2, bundleAlertAdapter.getAlert().getPolygonCount());
  }

  @Test
  public void returnsCorrectXCoordinate() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[25.0, 25.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(25.0, bundleAlertAdapter.getAlert().getPolygon(0).getCoordinate(0).getX(), 0.001);
  }

  @Test
  public void returnsCorrectYCoordinate() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[25.0, 25.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(25.0, bundleAlertAdapter.getAlert().getPolygon(0).getCoordinate(0).getY(), 0.001);
  }

  @Test
  public void returnsDifferentCorrectXCoordinate() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[24.0, 25.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(24.0, bundleAlertAdapter.getAlert().getPolygon(0).getCoordinate(0).getX(), 0.001);
  }

  @Test
  public void returnsDifferentCorrectYCoordinate() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[24.0, 23.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(23.0, bundleAlertAdapter.getAlert().getPolygon(0).getCoordinate(0).getY(), 0.001);
  }

  @Test
  public void returnsTwoCoordinates() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[24.0, 23.0],[25.0, 23.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(2, bundleAlertAdapter.getAlert().getPolygon(0).getCoordinateCount());
  }

  @Test
  public void secondCoordinateXCorrect() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[24.0, 23.0],[25.0, 23.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(25.0, bundleAlertAdapter.getAlert().getPolygon(0).getCoordinate(1).getX(), 0.001);
  }

  @Test
  public void secondCoordinateYCorrect() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[24.0, 23.0],[25.0, 22.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(22.0, bundleAlertAdapter.getAlert().getPolygon(0).getCoordinate(1).getY(), 0.001);
  }

  @Test
  public void secondPolygonCoordinateXCorrect() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[24.0, 23.0]],[[20.0, 15.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(20.0, bundleAlertAdapter.getAlert().getPolygon(1).getCoordinate(0).getX(), 0.001);
  }

  @Test
  public void secondPolygonCoordinateYCorrect() {
    Bundle bundle = new Bundle();
    bundle.putString("polygons", "[[[24.0, 23.0]],[[20.0, 15.0]]]");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(15.0, bundleAlertAdapter.getAlert().getPolygon(1).getCoordinate(0).getY(), 0.001);
  }

  @Test
  public void expectedUpdateTime() {
    Bundle bundle = new Bundle();
    bundle.putLong("expectedUpdate", 1500L);
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals(1500L, bundleAlertAdapter.getAlert().getExpectedUpdateTime().getTime());
  }

  @Test
  public void noExpectedUpdateTime_returnsNull() {
    Bundle bundle = new Bundle();
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertNull(bundleAlertAdapter.getAlert().getExpectedUpdateTime());
  }

  @Test
  public void idGiven_idParsed() {
    Bundle bundle = new Bundle();
    bundle.putString("id", "https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1", bundleAlertAdapter.getAlert().getNwsId());
  }

  @Test
  public void differentIdGiven_idParsed() {
    Bundle bundle = new Bundle();
    bundle.putString("id", "https://api.weather.gov/alerts/urn:oid:3.49.0.1.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1");
    BundleAlertAdapter bundleAlertAdapter = new BundleAlertAdapter(bundle);
    assertEquals("https://api.weather.gov/alerts/urn:oid:3.49.0.1.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1", bundleAlertAdapter.getAlert().getNwsId());
  }
}
