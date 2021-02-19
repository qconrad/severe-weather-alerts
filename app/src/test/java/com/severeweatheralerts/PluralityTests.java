package com.severeweatheralerts;

import com.severeweatheralerts.TextUtils.Plurality;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PluralityTests {
  @Test
  public void getText_SingleProvided_Singular() {
    Plurality plurality = new Plurality(1.0, "inch", "inches");
    assertEquals("inch", plurality.getText());
  }

  @Test
  public void getText_PluralProvided_PluralReturn() {
    Plurality plurality = new Plurality(2.0, "inch", "inches");
    assertEquals("inches", plurality.getText());
  }

  @Test
  public void getText_DifferentSingle_SingleReturn() {
    Plurality plurality = new Plurality(1.0, "degree", "degrees");
    assertEquals("degree", plurality.getText());
  }

  @Test
  public void getText_DifferentPlural_PluralReturn() {
    Plurality plurality = new Plurality(0.5, "degree", "degrees");
    assertEquals("degrees", plurality.getText());
  }
}
