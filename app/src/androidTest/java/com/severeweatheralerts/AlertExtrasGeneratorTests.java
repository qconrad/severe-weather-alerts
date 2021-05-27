package com.severeweatheralerts;

import android.content.Intent;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.MotionVector;
import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Notifications.AlertExtrasGenerator;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

  @Test
  public void returnsSender() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setSender("NWS Chicago IL");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("NWS Chicago IL", alertBundleAdapter.addExtras().getExtras().getString("sender"));
  }

  @Test
  public void returnsDifferentSender() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setSender("NWS Lincoln IL");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("NWS Lincoln IL", alertBundleAdapter.addExtras().getExtras().getString("sender"));
  }

  @Test
  public void returnsFirstZoneLink() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.addZoneLink("link1");
    tornadoWarning.addZoneLink("link2");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("link1", alertBundleAdapter.addExtras().getExtras().getStringArrayList("zones").get(0));
  }

  @Test
  public void returnsPolygonString() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertNull(alertBundleAdapter.addExtras().getExtras().getString("polygons"));
  }

  @Test
  public void returnsOneCoordinate() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(25, 25));
    tornadoWarning.addPolygon(polygon);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("[[[25.0, 25.0]]]", alertBundleAdapter.addExtras().getExtras().getString("polygons"));
  }

  @Test
  public void returnsDifferentXCoordinate() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(24, 25));
    tornadoWarning.addPolygon(polygon);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("[[[24.0, 25.0]]]", alertBundleAdapter.addExtras().getExtras().getString("polygons"));
  }

  @Test
  public void returnsDifferentYCoordinate() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(24, 24));
    tornadoWarning.addPolygon(polygon);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("[[[24.0, 24.0]]]", alertBundleAdapter.addExtras().getExtras().getString("polygons"));
  }

  @Test
  public void returnsSecondCoordinate() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(24, 24));
    polygon.addCoordinate(new MercatorCoordinate(20, 21));
    tornadoWarning.addPolygon(polygon);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("[[[24.0, 24.0],[20.0, 21.0]]]", alertBundleAdapter.addExtras().getExtras().getString("polygons"));
  }

  @Test
  public void returnsTwoPolygons() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(24, 24));
    polygon.addCoordinate(new MercatorCoordinate(20, 21));
    Polygon polygon2 = new Polygon();
    polygon2.addCoordinate(new MercatorCoordinate(15, 15));
    tornadoWarning.addPolygon(polygon);
    tornadoWarning.addPolygon(polygon2);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("[[[24.0, 24.0],[20.0, 21.0]],[[15.0, 15.0]]]", alertBundleAdapter.addExtras().getExtras().getString("polygons"));
  }

  @Test
  public void expectedUpdateTime() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setExpectedUpdateTime(new Date(15000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(15000L, alertBundleAdapter.addExtras().getExtras().getLong("expectedUpdate"));
  }

  @Test
  public void idParsing() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setExpectedUpdateTime(new Date(15000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setNwsId("https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1", alertBundleAdapter.addExtras().getExtras().getString("id"));
  }

  @Test
  public void differentIdParsing() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setSentTime(new Date(4000));
    tornadoWarning.setStartTime(new Date(3001));
    tornadoWarning.setEndTime(new Date(2000));
    tornadoWarning.setExpectedUpdateTime(new Date(15000));
    tornadoWarning.setType(Alert.Type.CANCEL);
    tornadoWarning.setNwsId("https://api.weather.gov/alerts/urn:oid:3.49.0.1.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1");
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals("https://api.weather.gov/alerts/urn:oid:3.49.0.1.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1", alertBundleAdapter.addExtras().getExtras().getString("id"));
  }

  @Test
  public void motionParsing() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setMotionVector(new MotionVector(20, 20));
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(20, alertBundleAdapter.addExtras().getExtras().getInt("heading"));
  }

  @Test
  public void differentHeading() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setMotionVector(new MotionVector(21, 20));
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(21, alertBundleAdapter.addExtras().getExtras().getInt("heading"));
  }

  @Test
  public void velocityParsing() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setMotionVector(new MotionVector(21, 20));
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(20, alertBundleAdapter.addExtras().getExtras().getInt("velocity"));
  }

  @Test
  public void differentVelocity() {
    TornadoWarning tornadoWarning = new TornadoWarning();
    tornadoWarning.setMotionVector(new MotionVector(21, 21));
    Intent resultIntent = new Intent();
    AlertExtrasGenerator alertBundleAdapter = new AlertExtrasGenerator(tornadoWarning, resultIntent);;
    assertEquals(21, alertBundleAdapter.addExtras().getExtras().getInt("velocity"));
  }
}
