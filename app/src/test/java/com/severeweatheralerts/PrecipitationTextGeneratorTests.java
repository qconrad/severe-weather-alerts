package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.Generators.OneHourPrecipitation.PrecipitationTextGenerator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class PrecipitationTextGeneratorTests {
  @Test
  public void empty() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("No forecast found", precipitationTextGenerator.getText());
  }

  @Test
  public void heavyRainIn2Mins() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 0.2));
    forecast.add(new ForecastTime(new Date(60000), 1.0));
    forecast.add(new ForecastTime(new Date(120000), 3.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Heavy rain in 2 minutes", precipitationTextGenerator.getText());
  }

  @Test
  public void heavyRainIn3Mins() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 0.2));
    forecast.add(new ForecastTime(new Date(60000), 1.0));
    forecast.add(new ForecastTime(new Date(180000), 3.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Heavy rain in 3 minutes", precipitationTextGenerator.getText());
  }

  @Test
  public void heavyRainOneMinute() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 0.2));
    forecast.add(new ForecastTime(new Date(60000), 2.5));
    forecast.add(new ForecastTime(new Date(180000), 3.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Heavy rain in 1 minute", precipitationTextGenerator.getText());
  }

  @Test
  public void heavyRainOccurring_HeavyRainFor() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 3.0));
    forecast.add(new ForecastTime(new Date(180000), 2.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Heavy rain for 3 minutes", precipitationTextGenerator.getText());
  }

  @Test
  public void HeavyRainIn_HailIn() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 0.0));
    forecast.add(new ForecastTime(new Date(180000), 2.5));
    forecast.add(new ForecastTime(new Date(360000), 3.5));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Heavy rain in 3 minutes, hail in 6 minutes", precipitationTextGenerator.getText());
  }

  @Test
  public void heavyRainOccurring_HailIn() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 3.0));
    forecast.add(new ForecastTime(new Date(60000), 4.0));
    forecast.add(new ForecastTime(new Date(180000), 2.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Heavy rain for 3 minutes, hail in 1 minute", precipitationTextGenerator.getText());
  }

  @Test
  public void extraPoints_HailIn() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 3.0));
    forecast.add(new ForecastTime(new Date(60000), 4.0));
    forecast.add(new ForecastTime(new Date(180000), 2.0));
    forecast.add(new ForecastTime(new Date(190000), 1.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Heavy rain for 3 minutes, hail in 1 minute", precipitationTextGenerator.getText());
  }

  @Test
  public void HailFor() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 4.0));
    forecast.add(new ForecastTime(new Date(60000), 3.0));
    forecast.add(new ForecastTime(new Date(120000), 3.0));
    forecast.add(new ForecastTime(new Date(120000), 1.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Heavy rain for 2 minutes, hail for 1 minute", precipitationTextGenerator.getText());
  }

  @Test
  public void ModerateRainForHour() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 2.0));
    forecast.add(new ForecastTime(new Date(60000), 2.0));
    forecast.add(new ForecastTime(new Date(120000), 2.0));
    forecast.add(new ForecastTime(new Date(120000), 2.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Light to moderate rain", precipitationTextGenerator.getText());
  }

  @Test
  public void ModerateRainForHour_DifferentValues() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 2.1));
    forecast.add(new ForecastTime(new Date(60000), 2.3));
    forecast.add(new ForecastTime(new Date(120000), 2.1));
    forecast.add(new ForecastTime(new Date(120000), 2.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Light to moderate rain", precipitationTextGenerator.getText());
  }

  @Test
  public void moderateRainOccurring_HeavyRainIn() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 2.0));
    forecast.add(new ForecastTime(new Date(180000), 3.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Heavy rain in 3 minutes", precipitationTextGenerator.getText());
  }

  @Test
  public void moderateRainFor() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 2.0));
    forecast.add(new ForecastTime(new Date(180000), 1.4));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertEquals("Light to moderate rain for 3 minutes", precipitationTextGenerator.getText());
  }

  @Test
  public void nothing_Null() {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    forecast.add(new ForecastTime(new Date(0), 0.0));
    forecast.add(new ForecastTime(new Date(180000), 0.0));
    PrecipitationTextGenerator precipitationTextGenerator = new PrecipitationTextGenerator(forecast, new Date(0));
    assertNull(precipitationTextGenerator.getText());
  }
}
