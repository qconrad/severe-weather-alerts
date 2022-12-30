package com.severeweatheralerts;

import static junit.framework.TestCase.assertEquals;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Graphics.Tools.RangeGenerator;

import org.junit.Test;

public class RangeGeneratorTests {
  // Construct the class with a alert and prediction amount (double)
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT... Heavy snow expected. Total snow accumulations of 3 to 6 inches, with localized amounts up to 8 inches.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 4.5);
    assertEquals("3 to 6 inches", rangeGenerator.getRange());
  }

  // Different prediction amount
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange2() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT... Heavy snow expected. Total snow accumulations of 4 to 8 inches, with localized amounts up to 10 inches.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 6);
    assertEquals("4 to 8 inches", rangeGenerator.getRange());
  }

  // Predicted amount is lower than the range
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange3() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT... Heavy snow expected. Total snow accumulations of 4 to 8 inches, with localized amounts up to 10 inches.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 5);
    assertEquals("3 to 7 inches", rangeGenerator.getRange());
  }

  // Slightly different description
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange4() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT... Snow expected. Total snow accumulations of 4 to 8 inches, with localized amounts up to 10 inches.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 6);
    assertEquals("4 to 8 inches", rangeGenerator.getRange());
  }

  // Range where lower bound is below 0
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange5() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT... Snow expected. Total snow accumulations of 4 to 8 inches, with localized amounts up to 10 inches.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 1);
    assertEquals("up to 3 inches", rangeGenerator.getRange());
  }

  // Range is worded differently Ex: Total accumulations between 4 and 8 inches
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange6() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT... Snow expected. Total accumulations between 4 and 8 inches, with localized amounts up to 10 inches.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 6);
    assertEquals("4 to 8 inches", rangeGenerator.getRange());
  }

  // Multiple ranges in description, the closest one to the prediction amount is used
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange7() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT... Heavy snow. Additional snow accumulations of 4 to 10 " +
                                 "inches, with locally higher amounts up to 16 inches over the " +
                                 "higher peaks of the Tetons and Gros Ventres. 5 to 10 inches " +
                                 "expected toward Togwotee Pass. Winds gusting as high as 30 mph " +
                                 "Friday, increasing to gusts up to 45 mph tonight.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 7.5);
    assertEquals("5 to 10 inches", rangeGenerator.getRange());
  }

  // Description says "up to" snow amount
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange8() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow. Additional snow accumulations of up to 4 inches.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 2);
    assertEquals("up to 4 inches", rangeGenerator.getRange());
  }

  // prediction amount is 0
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange9() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow. Additional snow accumulations of up to 4 inches.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 0);
    assertEquals("little to none", rangeGenerator.getRange());
  }

  // Feet forecast
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange10() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow expected. Total snow accumulations of 1 to 2 feet with locally higher amounts up to 3 feet possible. Winds gusting as high as 35 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 18);
    assertEquals("1 to 2 feet", rangeGenerator.getRange());
  }

  // Feet and inches
  // * WHAT...Heavy snow expected above 6000 feet. Total snow
  // accumulations of 4 to 12 inches, except 1 to 2 feet above 7000
  // feet. Winds gusting as high as 50 mph.
  // Show inches if lower bound is below 1 foot
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange11() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow expected above 6000 feet. Total snow accumulations of 4 to 12 inches, except 1 to 2 feet above 7000 feet. Winds gusting as high as 50 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 8);
    assertEquals("4 to 12 inches", rangeGenerator.getRange());
  }

  // Up to feet amount
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange12() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow expected above 6000 feet. Additional snow accumulations of up to 2 feet. Winds gusting as high as 50 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 12);
    assertEquals("up to 2 feet", rangeGenerator.getRange());
  }

  // Uses correct pluralization of feet/foot
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange13() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow expected above 6000 feet. Additional snow accumulations of up to 2 feet. Winds gusting as high as 50 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 3);
    assertEquals("up to 1 foot", rangeGenerator.getRange());
  }

  // Up to 1 inch
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange14() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow expected above 6000 feet. Additional snow accumulations of up to 2 inches. Winds gusting as high as 50 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 0.2);
    assertEquals("up to 1 inch", rangeGenerator.getRange());
  }

  // Singular foot in description
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange15() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow expected above 6000 feet. Additional snow accumulations of up to 1 foot. Winds gusting as high as 50 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 6);
    assertEquals("up to 1 foot", rangeGenerator.getRange());
  }

  // Singular inch in description
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange16() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow expected above 6000 feet. Additional snow accumulations of up to 1 inch. Winds gusting as high as 50 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 0.5);
    assertEquals("up to 1 inch", rangeGenerator.getRange());
  }

  // A few inches
  // A few inches is assumed to be 1 to 3 inches
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange17() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Light snow tapering off. Additional snow accumulations of a few inches. Winds gusting as high as 50 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 1);
    assertEquals("up to 2 inches", rangeGenerator.getRange());
  }

  // No range in description
  // Shouldn't really show the snowfall amount but to make this class generic and robust,
  // we'll assume a margin of error of 1 inch
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange18() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Light snow tapering off. Winds gusting as high as 50 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 1);
    assertEquals("up to 2 inches", rangeGenerator.getRange());
  }

  // Up to 2 feet, make sure value is rounded and not using integer division
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange19() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow expected above 6000 feet. Additional snow accumulations of up to 2 feet. Winds gusting as high as 50 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 11);
    assertEquals("up to 2 feet", rangeGenerator.getRange());
  }

  // Fallback margin is 1 by default, use setFallbackMargin to change
  // Fallback margin is used when the alert description doesn't contain a range
  @Test
  public void getRange_AlertAndPredictionAmount_PredictionRange20() {
    Alert alert = new DefaultAlert();
    alert.setDescription("WHAT...Heavy snow expected is expected. Winds gusting as high as 50 mph.");
    RangeGenerator rangeGenerator = new RangeGenerator(alert, 6);
    rangeGenerator.setFallbackMargin(2);
    assertEquals("4 to 8 inches", rangeGenerator.getRange());
  }
}
