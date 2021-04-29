package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;
import com.severeweatheralerts.Notifications.MessageAdapter;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MessageAdapterTests {
  @Test
  public void getAlert_NameProvided_AlertTypeIsThatName() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("name", "Tornado Warning");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals(TornadoWarning.class, messageAdapter.getAlert().getClass());
  }

  @Test
  public void getAlert_DifferentNameProvided_AlertTypeIsThatName() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("name", "Tornado Watch");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals(TornadoWatch.class, messageAdapter.getAlert().getClass());
  }

  @Test
  public void getAlert_DescriptionProvided_AlertHasThatDescription() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("description", "This is a description.");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("This is a description.", messageAdapter.getAlert().getDescription());
  }

  @Test
  public void getAlert_DifferentDescriptionProvided_AlertHasThatDescription() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("description", "This is a different description.");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("This is a different description.", messageAdapter.getAlert().getDescription());
  }

  @Test
  public void getAlert_InstructionProvided_AlertHasThatInstruction() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("instruction", "This is an instruction.");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("This is an instruction.", messageAdapter.getAlert().getInstruction());
  }

  @Test
  public void getAlert_DifferentInstructionProvided_AlertHasThatInstruction() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("instruction", "This is a different instruction.");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("This is a different instruction.", messageAdapter.getAlert().getInstruction());
  }

  @Test
  public void getAlert_NameProvided_GetNameReturnsThatName() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("name", "Tornado Warning");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("Tornado Warning", messageAdapter.getAlert().getName());
  }

  @Test
  public void getAlert_DifferentNameProvided_GetNameReturnsThatName() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("name", "Tornado Watch");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("Tornado Watch", messageAdapter.getAlert().getName());
  }

  @Test
  public void getAlert_AlertTypeProvided_getTypeReturnsPost() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("type", "Alert");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals(Alert.Type.POST, messageAdapter.getAlert().getType());
  }

  @Test
  public void getAlert_UpdateTypeProvided_TypeIsUpdate() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("type", "Update");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals(Alert.Type.UPDATE, messageAdapter.getAlert().getType());
  }

  @Test
  public void getAlert_CancelTypeProvided_TypeIsCancel() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("type", "Cancel");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals(Alert.Type.CANCEL, messageAdapter.getAlert().getType());
  }

  @Test
  public void getAlert_nwsHeadlineProvided_IsLargeHeadline() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("nwsHeadline", "Big storm coming");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("Big storm coming", messageAdapter.getAlert().getLargeHeadline());
  }

  @Test
  public void getAlert_differentNwsHeadlineProvided_IsLargeHeadline() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("nwsHeadline", "A bigger storm coming");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("A bigger storm coming", messageAdapter.getAlert().getLargeHeadline());
  }

  @Test
  public void getAlert_differentNwsHeadlineProvided_IsSmallHeadline() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("nwsHeadline", "WINTER STORM WARNING IN EFFECT UNTIL 3AM TODAY");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("WINTER STORM WARNING IN EFFECT UNTIL 3AM TODAY", messageAdapter.getAlert().getSmallHeadline());
  }

  @Test
  public void getAlert_differentSmallNwsHeadlineProvided_IsSmallHeadline() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("nwsHeadline", "WINTER STORM WARNING REMAINS IN EFFECT UNTIL 4AM TODAY");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("WINTER STORM WARNING REMAINS IN EFFECT UNTIL 4AM TODAY", messageAdapter.getAlert().getSmallHeadline());
  }

  @Test
  public void getAlert_differentSmallNwsHeadlineProvided_LargeHeadlineNull() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("nwsHeadline", "WINTER STORM WARNING REMAINS IN EFFECT UNTIL 4AM TODAY");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertNull(messageAdapter.getAlert().getLargeHeadline());
  }

  @Test
  public void getAlert_NwsHeadlineProvided_SmallHeadlineNull() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("nwsHeadline", "A bigger storm coming");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertNull(messageAdapter.getAlert().getSmallHeadline());
  }

  @Test
  public void getAlert_DescriptionHeadlineProvided_RemovedFromDescription() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("description", "...Major storm coming...\n\nText");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("Text", messageAdapter.getAlert().getDescription());
  }

  @Test
  public void getAlert_DescriptionProvided_Beautified() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("description", "This is hard wrapped text\nfor some weird reason.");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("This is hard wrapped text for some weird reason.", messageAdapter.getAlert().getDescription());
  }

  @Test
  public void getAlert_InstructionProvided_Beautified() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("instruction", "This is hard wrapped text\nfor some weird reason.");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("This is hard wrapped text for some weird reason.", messageAdapter.getAlert().getInstruction());
  }

  @Test
  public void getAlert_sentProvided_Parsed() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("sent", "2021-04-04T09:06:21+00:00");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertNotNull(messageAdapter.getAlert().getSentTime());
  }

  @Test
  public void getAlert_onsetProvided_Parsed() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("onset", "2021-04-04T09:06:21+00:00");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertNotNull(messageAdapter.getAlert().getStartTime());
  }

  @Test
  public void getAlert_endProvided_Parsed() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("ends", "2021-04-04T09:06:21+00:00");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertNotNull(messageAdapter.getAlert().getEndTime());
  }

  @Test
  public void getAlert_expiresProvided_Parsed() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("expires", "2021-04-04T09:06:21+00:00");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertNotNull(messageAdapter.getAlert().getEndTime());
  }

  @Test
  public void getAlert_senderCodeParsed() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("senderCode", "LOT");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("lot", messageAdapter.getAlert().getSenderCode());
  }

  @Test
  public void getAlert_differentSenderCodeParsed() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("senderCode", "ILX");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("ilx", messageAdapter.getAlert().getSenderCode());
  }

  @Test
  public void getAlert_senderNameParsed() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("senderName", "NWS Chicago IL");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("NWS Chicago IL", messageAdapter.getAlert().getSender());
  }

  @Test
  public void getAlert_differentSenderNameParsed() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("senderName", "NWS Lincoln IL");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("NWS Lincoln IL", messageAdapter.getAlert().getSender());
  }

  @Test
  public void getAlert_ZoneCountCorrect() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("zones", "[\"fire/SDZ003\",\"fire/SDZ004\",\"fire/SDZ005\",\"fire/SDZ009\",\"fire/SDZ010\",\"fire/SDZ015\"]");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals(6, messageAdapter.getAlert().getZoneLinkCount());
  }

  @Test
  public void getAlert_DifferentZoneCountCorrect() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("zones", "[\"fire/SDZ003\",\"fire/SDZ004\",\"fire/SDZ005\",\"fire/SDZ009\",\"fire/SDZ010\"]");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals(5, messageAdapter.getAlert().getZoneLinkCount());
  }

  @Test
  public void getAlert_FirstZoneCorrect() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("zones", "[\"fire/SDZ003\",\"fire/SDZ004\",\"fire/SDZ005\",\"fire/SDZ009\",\"fire/SDZ010\"]");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("https://api.weather.gov/zones/fire/SDZ003", messageAdapter.getAlert().getZone(0));
  }

  @Test
  public void getAlert_SecondZoneCorrect() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("zones", "[\"fire/SDZ003\",\"fire/SDZ004\",\"fire/SDZ005\",\"fire/SDZ009\",\"fire/SDZ010\"]");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("https://api.weather.gov/zones/fire/SDZ004", messageAdapter.getAlert().getZone(1));
  }

  @Test
  public void getAlert_PolygonCountCorrect() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("polygonType", "Polygon");
    mockAlertMessage.put("polygon", "[[[-89.55,30.2],[-89.64,30.18],[-89.9,30.669999999999998],[-89.76,30.669999999999998],[-89.55,30.2]]]");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals(1, messageAdapter.getAlert().getPolygonCount());
  }

  @Test
  public void getAlert_CoordinateCountCorrect() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("polygonType", "Polygon");
    mockAlertMessage.put("polygon", "[[[-89.55,30.2],[-89.64,30.18],[-89.9,30.669999999999998],[-89.76,30.669999999999998],[-89.55,30.2]]]");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals(5, messageAdapter.getAlert().getPolygon(0).getCoordinateCount());
  }

  @Test
  public void getAlert_SecondPolygonCorrectCoordinates() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("polygonType", "MultiPolygon");
    mockAlertMessage.put("polygon", "[[[[-89.55,30.2],[-89.64,30.18],[-89.9,30.669999999999998],[-89.76,30.669999999999998],[-89.55,30.2]]],[[[1,2]]]]");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals(1, messageAdapter.getAlert().getPolygon(1).getCoordinateCount());
  }

  @Test
  public void getAlert_ExpiresGiven_NextUpdateTime() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("expires", "2021-04-04T09:06:21+00:00");
    mockAlertMessage.put("ends", "2021-04-04T09:06:21+00:00");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertNotNull(messageAdapter.getAlert().getExpectedUpdateTime());
  }

  @Test
  public void getAlert_DescriptionAdaptedCorrectly() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("description", "CCA\n\nTest");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("Test", messageAdapter.getAlert().getDescription());
  }

  @Test
  public void getAlert_TypeIsCancel_InstructionNull() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("type", "Cancel");
    mockAlertMessage.put("instruction", "CCA\n\nTest");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertNull(messageAdapter.getAlert().getInstruction());
  }

  @Test
  public void getAlert_IdGiven_IDReturned() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("id", "https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1", messageAdapter.getAlert().getNwsId());
  }

  @Test
  public void getAlert_DifferentIdGiven_IDReturned() {
    Map<String, String> mockAlertMessage = new HashMap<>();
    mockAlertMessage.put("id", "https://api.weather.gov/alerts/urn:oid:2.49.0.2.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1");
    MessageAdapter messageAdapter = new MessageAdapter(mockAlertMessage);
    assertEquals("https://api.weather.gov/alerts/urn:oid:2.49.0.2.840.0.3b06d078d97bf9ac03614f6e184c7ea3061d1e38.001.1", messageAdapter.getAlert().getNwsId());
  }
}
