package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.Rounder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RounderTests {
  @Test
  public void test() {
    Rounder rounder = new Rounder(2.5, 1);
    assertEquals(2.5, rounder.getRounded(), 0.001);
  }

  @Test
  public void getRounded_1DigitNumberWith0DigitRound_WholeNumber() {
    Rounder rounder = new Rounder(2.5, 0);
    assertEquals(3, rounder.getRounded(), 0.001);
  }

  @Test
  public void getRounded_Different1DigitNumberWith0DigitRound_WholeNumber() {
    Rounder rounder = new Rounder(4.5, 0);
    assertEquals(5, rounder.getRounded(), 0.001);
  }

  @Test
  public void getRounded_2Decimals_RoundedToOne() {
    Rounder rounder = new Rounder(4.55, 1);
    assertEquals(4.6, rounder.getRounded(), 0.001);
  }

  @Test
  public void getRounded_3Decimals_RoundedToTwo() {
    Rounder rounder = new Rounder(4.558, 2);
    assertEquals(4.56, rounder.getRounded(), 0.001);
  }
}
