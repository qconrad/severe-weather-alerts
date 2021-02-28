package com.severeweatheralerts;

import android.graphics.Color;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeverityColorMapperTests {
  @Test
  public void severityIndexIsThree_ColorIsBlue() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(3);
    assertEquals(Color.parseColor("#215ca5"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIs0_ColorIsBlue() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(0);
    assertEquals(Color.parseColor("#215ca5"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIs9_ColorIsYellow() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(9);
    assertEquals(Color.parseColor("#fdc500"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIs13_ColorIsOrange() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(13);
    assertEquals(Color.parseColor("#fd8c00"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIs15_ColorIsRed() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(15);
    assertEquals(Color.parseColor("#dc0000"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIs17_ColorIsDarkRed() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(17);
    assertEquals(Color.parseColor("#780000"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIs19_ColorIsPurple() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(19);
    assertEquals(Color.parseColor("#b01994"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIsTen_ColorIsBlue() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(10);
    assertEquals(Color.parseColor("#fdc500"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIsFourteen_ColorIsOrange() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(14);
    assertEquals(Color.parseColor("#fd8c00"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIsFifteen_ColorIsRed() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(15);
    assertEquals(Color.parseColor("#dc0000"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIsSixteen_ColorIsRed() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(16);
    assertEquals(Color.parseColor("#dc0000"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIsEighteen_ColorIsDarkRed() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(18);
    assertEquals(Color.parseColor("#780000"), severityColorMapper.getColor());
  }

  @Test
  public void severityIndexIsEighteen_ColorIsPurple() {
    SeverityColorMapper severityColorMapper = new SeverityColorMapper(20);
    assertEquals(Color.parseColor("#b01994"), severityColorMapper.getColor());
  }
}
