package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.JSONParsing.GridDataParser;

import org.json.JSONException;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GridDataParserTests {

  String snowGridData = "{\"properties\":{\"@id\":\"https://api.weather.gov/gridpoints/LOT/69,69\",\"@type\":\"wx:Gridpoint\",\"updateTime\":\"2021-02-18T02:15:23+00:00\",\"validTimes\":\"2021-02-17T20:00:00+00:00/P7DT5H\",\"elevation\":{\"unitCode\":\"wmoUnit:m\",\"value\":185.0136},\"forecastOffice\":\"https://api.weather.gov/offices/LOT\",\"gridId\":\"LOT\",\"gridX\":\"69\",\"gridY\":\"69\",\"snowfallAmount\":{\"uom\":\"wmoUnit:mm\",\"values\":[{\"validTime\":\"2021-02-17T20:00:00+00:00/PT4H\",\"value\":0},{\"validTime\":\"2021-02-18T00:00:00+00:00/PT6H\",\"value\":0},{\"validTime\":\"2021-02-18T06:00:00+00:00/PT6H\",\"value\":5.08},{\"validTime\":\"2021-02-18T12:00:00+00:00/PT6H\",\"value\":7.62}]}}}";
  String differentSnowGridData = "{\"properties\":{\"@id\":\"https://api.weather.gov/gridpoints/LOT/69,69\",\"@type\":\"wx:Gridpoint\",\"updateTime\":\"2021-02-18T02:15:23+00:00\",\"validTimes\":\"2021-02-17T20:00:00+00:00/P7DT5H\",\"elevation\":{\"unitCode\":\"wmoUnit:m\",\"value\":185.0136},\"forecastOffice\":\"https://api.weather.gov/offices/LOT\",\"gridId\":\"LOT\",\"gridX\":\"69\",\"gridY\":\"69\",\"snowfallAmount\":{\"uom\":\"wmoUnit:mm\",\"values\":[{\"validTime\":\"2021-02-17T20:00:00+00:00/PT4H\",\"value\":0},{\"validTime\":\"2021-02-18T06:00:00+00:00/PT6H\",\"value\":5.08},{\"validTime\":\"2021-02-18T12:00:00+00:00/PT6H\",\"value\":7.62}]}}}";
  @Test
  public void getSnowFallAmount_SnowGridDataGiven_IsCorrectType() throws JSONException {
    GridDataParser gridDataParser = new GridDataParser(snowGridData);
    assertEquals(gridDataParser.getParameter("snowfallAmount").getClass(), Parameter.class);
  }

  @Test
  public void invalidJsonGiven_JSONExceptionThrown() {
    try {
      new GridDataParser("invalid json");
      fail("exception was not thrown");
    } catch (Exception ignored) {}
  }

  @Test
  public void getSnowFallAmount_SnowGridDataGiven_CountCorrect() throws JSONException {
    GridDataParser gridDataParser = new GridDataParser(snowGridData);
    assertEquals(4, gridDataParser.getParameter("snowfallAmount").getCount());
  }

  @Test
  public void getSnowFallAmount_DifferentSnowGridDataGiven_CountCorrect() throws JSONException {
    GridDataParser gridDataParser = new GridDataParser(differentSnowGridData);
    assertEquals(3, gridDataParser.getParameter("snowfallAmount").getCount());
  }

  @Test
  public void getSnowFallAmount_FirstItemIsCorrectType() throws JSONException {
    GridDataParser gridDataParser = new GridDataParser(snowGridData);
    assertEquals(gridDataParser.getParameter("snowfallAmount").get(0).getClass(), ForecastTime.class);
  }

  @Test
  public void getSnowFallAmount_FirstItemValueIsCorrect() throws JSONException {
    GridDataParser gridDataParser = new GridDataParser(snowGridData);
    assertEquals(0.0, gridDataParser.getParameter("snowfallAmount").get(0).getValue(), 0.001);
  }

  @Test
  public void getSnowFallAmount_ThirdItemValueIsCorrect() throws JSONException {
    GridDataParser gridDataParser = new GridDataParser(snowGridData);
    assertEquals(5.0800000000000001, gridDataParser.getParameter("snowfallAmount").get(2).getValue(), 0.001);
  }

  @Test
  public void getSnowFallAmount_getDate_CorrectType() throws JSONException {
    GridDataParser gridDataParser = new GridDataParser(snowGridData);
    assertEquals(gridDataParser.getParameter("snowfallAmount").get(0).getDate().getClass(), Date.class);
 }

  @Test
  public void getSnowFallAmount_getDate_CorrectTime() throws JSONException {
    GridDataParser gridDataParser = new GridDataParser(snowGridData);
    assertEquals(1613592000000L, gridDataParser.getParameter("snowfallAmount").get(0).getDate().getTime());
  }

  @Test
  public void getSnowFallAmount_DifferentDate_CorrectTime() throws JSONException {
    GridDataParser gridDataParser = new GridDataParser(snowGridData);
    assertEquals(1613628000000L, gridDataParser.getParameter("snowfallAmount").get(2).getDate().getTime());
  }
}
