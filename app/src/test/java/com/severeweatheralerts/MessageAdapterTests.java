package com.severeweatheralerts;

import com.severeweatheralerts.Alerts.NWS.TornadoWarning;
import com.severeweatheralerts.Alerts.NWS.TornadoWatch;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
}
