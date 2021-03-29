package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
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
}
