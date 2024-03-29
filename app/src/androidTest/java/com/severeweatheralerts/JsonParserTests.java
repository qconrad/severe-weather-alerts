package com.severeweatheralerts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.severeweatheralerts.Adapters.UnadaptedAlert;
import com.severeweatheralerts.JSONParsing.AlertListParser;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class JsonParserTests {
  String SpecialWeatherStatementInput = "{\"@context\":[\"https://geojson.org/geojson-ld/geojson-context.jsonld\",{\"@version\":\"1.1\",\"wx\":\"https://api.weather.gov/ontology#\",\"@vocab\":\"https://api.weather.gov/ontology#\"}],\"type\":\"FeatureCollection\",\"features\":[{\"id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559554\",\"type\":\"Feature\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[-73.59,44.26],[-73.31,44.46],[-72.9,44.37],[-73.36,44],[-73.59,44.26]]]},\"properties\":{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559554\",\"@type\":\"wx:Alert\",\"id\":\"NWS-IDP-PROD-4559554\",\"areaDesc\":\"Portage; Brown; Waushara; Winnebago; Waupaca; Menominee; Shawano; Outagamie; Calumet; Wood\",\"geocode\":{\"UGC\":[\"WIZ036\",\"WIZ039\",\"WIZ045\",\"WIZ048\",\"WIZ037\",\"WIZ020\",\"WIZ031\",\"WIZ038\",\"WIZ049\",\"WIZ035\"],\"SAME\":[\"055097\",\"055009\",\"055137\",\"055139\",\"055135\",\"055078\",\"055115\",\"055087\",\"055015\",\"055141\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/WIZ036\",\"https://api.weather.gov/zones/forecast/WIZ039\",\"https://api.weather.gov/zones/forecast/WIZ045\",\"https://api.weather.gov/zones/forecast/WIZ048\",\"https://api.weather.gov/zones/forecast/WIZ037\",\"https://api.weather.gov/zones/forecast/WIZ020\",\"https://api.weather.gov/zones/forecast/WIZ031\",\"https://api.weather.gov/zones/forecast/WIZ038\",\"https://api.weather.gov/zones/forecast/WIZ049\",\"https://api.weather.gov/zones/forecast/WIZ035\"],\"references\":[],\"sent\":\"2020-11-26T03:12:00-06:00\",\"effective\":\"2020-11-26T03:12:00-06:00\",\"onset\":\"2020-11-26T03:12:00-06:00\",\"expires\":\"2020-11-26T09:00:00-06:00\",\"ends\":null,\"status\":\"Actual\",\"messageType\":\"Alert\",\"category\":\"Met\",\"severity\":\"Moderate\",\"certainty\":\"Observed\",\"urgency\":\"Expected\",\"event\":\"Special Weather Statement\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Green Bay WI\",\"headline\":\"Special Weather Statement issued November 26 at 3:12AM CST by NWS Green Bay WI\",\"description\":\"The combination of light winds and abundant low-level moisture\\nwill result in areas of fog and drizzle across central, northeast\\nand east central Wisconsin this morning. Some of the fog may\\nbecome locally dense. Poor visibilities and wet roads may result\\nin locally hazardous travel conditions for holiday travelers. The\\nfoggy conditions should improve by 9 am.\\n\\nMotorists are urged to slow down, keep a safe distance from other\\nvehicles, and use their low-beam headlights. Be prepared for rapid\\nchanges in visibility.\",\"instruction\":null,\"response\":\"Execute\",\"parameters\":{\"NWSheadline\":[\"AREAS OF FOG AND DRIZZLE THIS MORNING\"],\"EAS-ORG\":[\"WXR\"],\"PIL\":[\"GRBSPSGRB\"],\"BLOCKCHANNEL\":[\"CMAS\",\"EAS\",\"NWEM\"]}}}],\"title\":\"watches, warnings, and advisories\",\"updated\":\"2020-11-26T09:16:30+00:00\",\"pagination\":{\"next\":\"https://api.weather.gov/alerts?cursor=eyJ0IjoxNjA2MzM4Mzc3LCJpIjoiTldTLUlEUC1QUk9ELUtFRVBBTElWRS0yODUxNyJ9\"}}";
  String SmallCraftAdvisoryAndSpecialWeatherStatementInput = "{\"@context\":[\"https://geojson.org/geojson-ld/geojson-context.jsonld\",{\"@version\":\"1.1\",\"wx\":\"https://api.weather.gov/ontology#\",\"@vocab\":\"https://api.weather.gov/ontology#\"}],\"type\":\"FeatureCollection\",\"features\":[{\"id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559615-3770224\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559615-3770224\",\"@type\":\"wx:Alert\",\"id\":\"NWS-IDP-PROD-4559615-3770224\",\"areaDesc\":\"Sandy Hook NJ to Fire Island Inlet NY out 20 nm\",\"geocode\":{\"UGC\":[\"ANZ355\"],\"SAME\":[\"073355\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/ANZ355\"],\"references\":[{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4558114-3769815\",\"identifier\":\"NWS-IDP-PROD-4558114-3769815\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2020-11-25T15:22:00-05:00\"},{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4556947-3769503\",\"identifier\":\"NWS-IDP-PROD-4556947-3769503\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2020-11-25T04:39:00-05:00\"}],\"sent\":\"2020-11-26T04:52:00-05:00\",\"effective\":\"2020-11-26T04:52:00-05:00\",\"onset\":\"2020-11-26T04:52:00-05:00\",\"expires\":\"2020-11-26T16:00:00-05:00\",\"ends\":\"2020-11-27T01:00:00-05:00\",\"status\":\"Actual\",\"messageType\":\"Update\",\"category\":\"Met\",\"severity\":\"Minor\",\"certainty\":\"Likely\",\"urgency\":\"Expected\",\"event\":\"Small Craft Advisory\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Upton NY\",\"headline\":\"Small Craft Advisory issued November 26 at 4:52AM EST until November 27 at 1:00AM EST by NWS Upton NY\",\"description\":\"* WHAT...Southwest winds 10 to 20 kt with gusts up to 25 kt and\\nseas 3 to 6 feet.\\n\\n* WHERE...Sandy Hook NJ to Fire Island Inlet NY out 20 nm.\\n\\n* WHEN...Until 1 AM EST Friday.\\n\\n* IMPACTS...Conditions will be hazardous to small craft.\",\"instruction\":\"Inexperienced mariners, especially those operating smaller\\nvessels, should avoid navigating in hazardous conditions.\",\"response\":\"Avoid\",\"parameters\":{\"NWSheadline\":[\"SMALL CRAFT ADVISORY NOW IN EFFECT UNTIL 1 AM EST FRIDAY\"],\"VTEC\":[\"/O.EXT.KOKX.SC.Y.0108.000000T0000Z-201127T0600Z/\"],\"PIL\":[\"OKXMWWOKX\"],\"BLOCKCHANNEL\":[\"CMAS\",\"EAS\",\"NWEM\"],\"eventEndingTime\":[\"2020-11-27T06:00:00+00:00\"]},\"replacedBy\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.f2b344bad8e99c947eb0cd944633f7c118757e70.001.1\",\"replacedAt\": \"2021-04-29T19:10:00-05:00\"}},{\"id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559554\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559554\",\"@type\":\"wx:Alert\",\"id\":\"NWS-IDP-PROD-4559554\",\"areaDesc\":\"Portage; Brown; Waushara; Winnebago; Waupaca; Menominee; Shawano; Outagamie; Calumet; Wood\",\"geocode\":{\"UGC\":[\"WIZ036\",\"WIZ039\",\"WIZ045\",\"WIZ048\",\"WIZ037\",\"WIZ020\",\"WIZ031\",\"WIZ038\",\"WIZ049\",\"WIZ035\"],\"SAME\":[\"055097\",\"055009\",\"055137\",\"055139\",\"055135\",\"055078\",\"055115\",\"055087\",\"055015\",\"055141\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/WIZ036\",\"https://api.weather.gov/zones/forecast/WIZ039\",\"https://api.weather.gov/zones/forecast/WIZ045\",\"https://api.weather.gov/zones/forecast/WIZ048\",\"https://api.weather.gov/zones/forecast/WIZ037\",\"https://api.weather.gov/zones/forecast/WIZ020\",\"https://api.weather.gov/zones/forecast/WIZ031\",\"https://api.weather.gov/zones/forecast/WIZ038\",\"https://api.weather.gov/zones/forecast/WIZ049\",\"https://api.weather.gov/zones/forecast/WIZ035\"],\"references\":[],\"sent\":\"2020-11-26T03:12:00-06:00\",\"effective\":\"2020-11-26T03:12:00-06:00\",\"onset\":\"2020-11-26T03:12:00-06:00\",\"expires\":\"2020-11-26T09:00:00-06:00\",\"ends\":null,\"status\":\"Actual\",\"messageType\":\"Alert\",\"category\":\"Met\",\"severity\":\"Moderate\",\"certainty\":\"Observed\",\"urgency\":\"Expected\",\"event\":\"Special Weather Statement\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Green Bay WI\",\"headline\":\"Special Weather Statement issued November 26 at 3:12AM CST by NWS Green Bay WI\",\"description\":\"The combination of light winds and abundant low-level moisture\\nwill result in areas of fog and drizzle across central, northeast\\nand east central Wisconsin this morning. Some of the fog may\\nbecome locally dense. Poor visibilities and wet roads may result\\nin locally hazardous travel conditions for holiday travelers. The\\nfoggy conditions should improve by 9 am.\\n\\nMotorists are urged to slow down, keep a safe distance from other\\nvehicles, and use their low-beam headlights. Be prepared for rapid\\nchanges in visibility.\",\"instruction\":null,\"response\":\"Execute\",\"parameters\":{\"NWSheadline\":[\"AREAS OF FOG AND DRIZZLE THIS MORNING\"],\"EAS-ORG\":[\"WXR\"],\"PIL\":[\"GRBSPSGRB\"],\"BLOCKCHANNEL\":[\"CMAS\",\"EAS\",\"NWEM\"]}}}],\"title\":\"watches, warnings, and advisories\",\"updated\":\"2020-11-26T09:16:30+00:00\",\"pagination\":{\"next\":\"https://api.weather.gov/alerts?cursor=eyJ0IjoxNjA2MzM4Mzc3LCJpIjoiTldTLUlEUC1QUk9ELUtFRVBBTElWRS0yODUxNyJ9\"}}";
  String expiredReferences = "{\"@context\":[\"https://geojson.org/geojson-ld/geojson-context.jsonld\",{\"@version\":\"1.1\",\"wx\":\"https://api.weather.gov/ontology#\",\"@vocab\":\"https://api.weather.gov/ontology#\"}],\"type\":\"FeatureCollection\",\"features\":[{\"id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.25260e4f3a3a83db196c70c41ae30e141ec7b988.001.1\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.25260e4f3a3a83db196c70c41ae30e141ec7b988.001.1\",\"@type\":\"wx:Alert\",\"id\":\"urn:oid:2.49.0.1.840.0.25260e4f3a3a83db196c70c41ae30e141ec7b988.001.1\",\"areaDesc\":\"Gore and Elk Mountains/Central Mountain Valleys\",\"geocode\":{\"SAME\":[\"008037\",\"008045\",\"008097\"],\"UGC\":[\"COZ010\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/COZ010\"],\"references\":[{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.314a73baf7963280059a89ec5ad36907242f0649.001.1\",\"identifier\":\"urn:oid:2.49.0.1.840.0.314a73baf7963280059a89ec5ad36907242f0649.001.1\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2021-03-21T13:36:00-06:00\"}],\"sent\":\"2021-03-21T22:53:00-06:00\",\"effective\":\"2021-03-21T22:53:00-06:00\",\"onset\":\"2021-03-21T22:53:00-06:00\",\"expires\":\"2021-03-22T00:00:00-06:00\",\"ends\":\"2021-03-21T23:00:00-06:00\",\"status\":\"Actual\",\"messageType\":\"Update\",\"category\":\"Met\",\"severity\":\"Moderate\",\"certainty\":\"Likely\",\"urgency\":\"Expected\",\"event\":\"Winter Weather Advisory\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Grand Junction CO\",\"headline\":\"Winter Weather Advisory issued March 21 at 10:53PM MDT until March 21 at 11:00PM MDT by NWS Grand Junction CO\",\"description\":\"Occasional light snow showers will continue along the Continental\\nDivide tonight, though additional accumulations will be light and\\nimpacts minimal. Therefore, the Winter Weather Advisory will be\\nallowed to expire at 11 PM MDT.\",\"instruction\":\"\",\"response\":\"Execute\",\"parameters\":{\"PIL\":[\"GJTWSWGJT\"],\"NWSheadline\":[\"WINTER WEATHER ADVISORY WILL EXPIRE AT 11 PM MDT THIS EVENING ABOVE 9500 FEET\"],\"BLOCKCHANNEL\":[\"EAS\",\"NWEM\",\"CMAS\"],\"VTEC\":[\"/O.EXP.KGJT.WW.Y.0020.000000T0000Z-210322T0500Z/\"],\"eventEndingTime\":[\"2021-03-22T05:00:00+00:00\"],\"expiredReferences\":[\"w-nws.webmaster@noaa.gov,urn:oid:2.49.0.1.840.0.eccf59274026bdea1c5880007055754dd5755bb4.001.1,2021-03-21T04:16:00-06:00 w-nws.webmaster@noaa.gov,urn:oid:2.49.0.1.840.0.983b5665cc8db630b185297896d02addc61ef95a.001.1,2021-03-20T21:09:00-06:00 w-nws.webmaster@noaa.gov,urn:oid:2.49.0.1.840.0.b88f92148d7ac7ba08326a6be5a46702825c5150.001.1,2021-03-20T14:32:00-06:00\"]}}}]}";
  String eventMotion = "{\"@context\":[\"https://geojson.org/geojson-ld/geojson-context.jsonld\",{\"@version\":\"1.1\",\"wx\":\"https://api.weather.gov/ontology#\",\"@vocab\":\"https://api.weather.gov/ontology#\"}],\"type\":\"FeatureCollection\",\"features\":[{\"id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.25260e4f3a3a83db196c70c41ae30e141ec7b988.001.1\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.25260e4f3a3a83db196c70c41ae30e141ec7b988.001.1\",\"@type\":\"wx:Alert\",\"id\":\"urn:oid:2.49.0.1.840.0.25260e4f3a3a83db196c70c41ae30e141ec7b988.001.1\",\"areaDesc\":\"Gore and Elk Mountains/Central Mountain Valleys\",\"geocode\":{\"SAME\":[\"008037\",\"008045\",\"008097\"],\"UGC\":[\"COZ010\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/COZ010\"],\"references\":[{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.314a73baf7963280059a89ec5ad36907242f0649.001.1\",\"identifier\":\"urn:oid:2.49.0.1.840.0.314a73baf7963280059a89ec5ad36907242f0649.001.1\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2021-03-21T13:36:00-06:00\"}],\"sent\":\"2021-03-21T22:53:00-06:00\",\"effective\":\"2021-03-21T22:53:00-06:00\",\"onset\":\"2021-03-21T22:53:00-06:00\",\"expires\":\"2021-03-22T00:00:00-06:00\",\"ends\":\"2021-03-21T23:00:00-06:00\",\"status\":\"Actual\",\"messageType\":\"Update\",\"category\":\"Met\",\"severity\":\"Moderate\",\"certainty\":\"Likely\",\"urgency\":\"Expected\",\"event\":\"Winter Weather Advisory\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Grand Junction CO\",\"headline\":\"Winter Weather Advisory issued March 21 at 10:53PM MDT until March 21 at 11:00PM MDT by NWS Grand Junction CO\",\"description\":\"Occasional light snow showers will continue along the Continental\\nDivide tonight, though additional accumulations will be light and\\nimpacts minimal. Therefore, the Winter Weather Advisory will be\\nallowed to expire at 11 PM MDT.\",\"instruction\":\"\",\"response\":\"Execute\",\"parameters\":{\"PIL\":[\"GJTWSWGJT\"],\"eventMotionDescription\":[\"2021-05-22T04:24:00-00:00...storm...220DEG...49KT...39.89,-102.05\"],\"NWSheadline\":[\"WINTER WEATHER ADVISORY WILL EXPIRE AT 11 PM MDT THIS EVENING ABOVE 9500 FEET\"],\"BLOCKCHANNEL\":[\"EAS\",\"NWEM\",\"CMAS\"],\"VTEC\":[\"/O.EXP.KGJT.WW.Y.0020.000000T0000Z-210322T0500Z/\"],\"eventEndingTime\":[\"2021-03-22T05:00:00+00:00\"],\"expiredReferences\":[\"w-nws.webmaster@noaa.gov,urn:oid:2.49.0.1.840.0.eccf59274026bdea1c5880007055754dd5755bb4.001.1,2021-03-21T04:16:00-06:00 w-nws.webmaster@noaa.gov,urn:oid:2.49.0.1.840.0.983b5665cc8db630b185297896d02addc61ef95a.001.1,2021-03-20T21:09:00-06:00 w-nws.webmaster@noaa.gov,urn:oid:2.49.0.1.840.0.b88f92148d7ac7ba08326a6be5a46702825c5150.001.1,2021-03-20T14:32:00-06:00\"]}}}]}";

  @Test
  public void useAppContext() {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    assertEquals("com.severeweatheralerts", appContext.getPackageName());
  }

  @Test
  public void newJsonAlertsParser_emptyJSONString_throwsException() {
    try {
      new AlertListParser("");
      fail("IllegalArgumentException was not called on invalid JSON");
    } catch(IllegalArgumentException e){
      assertTrue(true);
    }
  }

  @Test
  public void newJsonAlertsParser_validString_doesNotThrowExceptions() {
    try {
      new AlertListParser(SpecialWeatherStatementInput);
      assertTrue(true);
    } catch(IllegalArgumentException e){
      fail("Valid JSON was provided but IllegalArgumentException was thrown.");
    }
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementInput_ReturnsTwoParsedAlert() {
    AlertListParser parser = new AlertListParser(SpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals(1, parsed.size());
  }

  @Test
  public void parseAlerts_SmallCraftAndSpecialInputGiven_ReturnsTwoParsedAlerts() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals(2, parsed.size());
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementInputGiven_FirstAlertHasThatName() {
    AlertListParser parser = new AlertListParser(SpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String name = parsed.get(0).getName();
    assertEquals("Special Weather Statement", name);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstAlertHasCorrectName() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String name = parsed.get(0).getName();
    assertEquals("Small Craft Advisory", name);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondAlertHasCorrectName() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String name = parsed.get(1).getName();
    assertEquals("Special Weather Statement", name);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstDescriptionIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String desc = "* WHAT...Southwest winds 10 to 20 kt with gusts up to 25 kt and\nseas 3 to 6 feet.\n\n* WHERE...Sandy Hook NJ to Fire Island Inlet NY out 20 nm.\n\n* WHEN...Until 1 AM EST Friday.\n\n* IMPACTS...Conditions will be hazardous to small craft.";
    String result = parsed.get(0).getDescription();
    assertEquals(desc, result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondDescriptionIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String desc = "The combination of light winds and abundant low-level moisture\nwill result in areas of fog and drizzle across central, northeast\nand east central Wisconsin this morning. Some of the fog may\nbecome locally dense. Poor visibilities and wet roads may result\nin locally hazardous travel conditions for holiday travelers. The\nfoggy conditions should improve by 9 am.\n\nMotorists are urged to slow down, keep a safe distance from other\nvehicles, and use their low-beam headlights. Be prepared for rapid\nchanges in visibility.";
    String result = parsed.get(1).getDescription();
    assertEquals(desc, result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstInstructionIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String instruction = "Inexperienced mariners, especially those operating smaller\nvessels, should avoid navigating in hazardous conditions.";
    String result = parsed.get(0).getInstruction();
    assertEquals(instruction, result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondInstructionIsNull() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getInstruction();
    assertNull(result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstSentIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getSent();
    assertEquals("2020-11-26T04:52:00-05:00", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondSentIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getSent();
    assertEquals("2020-11-26T03:12:00-06:00", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondOnsetIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getOnset();
    assertEquals("2020-11-26T03:12:00-06:00", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondExpiresIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getExpires();
    assertEquals("2020-11-26T09:00:00-06:00", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstEndsIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getEnds();
    assertEquals("2020-11-27T01:00:00-05:00", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondEndsIsNull() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getEnds();
    assertNull(result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstNwsHeadlineIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getNwsHeadline();
    assertEquals("SMALL CRAFT ADVISORY NOW IN EFFECT UNTIL 1 AM EST FRIDAY", result);
  }

  String alertWithoutNwsHeadline = "{\"@context\":[\"https://geojson.org/geojson-ld/geojson-context.jsonld\",{\"@version\":\"1.1\",\"wx\":\"https://api.weather.gov/ontology#\",\"@vocab\":\"https://api.weather.gov/ontology#\"}],\"type\":\"FeatureCollection\",\"features\":[{\"id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4569386\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4569386\",\"@type\":\"wx:Alert\",\"id\":\"NWS-IDP-PROD-4569386\",\"areaDesc\":\"Foothills of the Blue Mountains of Washington; Foothills of the Northern Blue Mountains of Oregon; Lower Columbia Basin of Washington; Yakima Valley; Lower Columbia Basin of Oregon\",\"geocode\":{\"UGC\":[\"WAZ029\",\"ORZ507\",\"WAZ028\",\"WAZ027\",\"ORZ044\"],\"SAME\":[\"053013\",\"053071\",\"041059\",\"053005\",\"053021\",\"053039\",\"053077\",\"041049\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/WAZ029\",\"https://api.weather.gov/zones/forecast/ORZ507\",\"https://api.weather.gov/zones/forecast/WAZ028\",\"https://api.weather.gov/zones/forecast/WAZ027\",\"https://api.weather.gov/zones/forecast/ORZ044\"],\"references\":[],\"sent\":\"2020-11-29T23:33:00-08:00\",\"effective\":\"2020-11-29T23:33:00-08:00\",\"onset\":\"2020-11-29T23:33:00-08:00\",\"expires\":\"2020-11-30T07:00:00-08:00\",\"ends\":null,\"status\":\"Actual\",\"messageType\":\"Alert\",\"category\":\"Met\",\"severity\":\"Moderate\",\"certainty\":\"Observed\",\"urgency\":\"Expected\",\"event\":\"Special Weather Statement\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Pendleton OR\",\"headline\":\"Special Weather Statement issued November 29 at 11:33PM PST by NWS Pendleton OR\",\"description\":\"Areas of dense fog have developed across portions of the Lower\\nColumbia Basin and the foothills of the Blue Mountains in Oregon\\nand Washington, as in the Yakima Valley. Visibility of a quarter\\nmile or less expected. Road and air temperatures are at or\\nslightly below freezing, leading to some slick roadways overnight\\ninto the morning hours.\",\"instruction\":null,\"response\":\"Execute\",\"parameters\":{\"EAS-ORG\":[\"WXR\"],\"PIL\":[\"PDTSPSPDT\"],\"BLOCKCHANNEL\":[\"CMAS\",\"EAS\",\"NWEM\"]}}}],\"title\":\"watches, warnings, and advisories\",\"updated\":\"2020-11-26T09:16:30+00:00\",\"pagination\":{\"next\":\"https://api.weather.gov/alerts?cursor=eyJ0IjoxNjA2MzM4Mzc3LCJpIjoiTldTLUlEUC1QUk9ELUtFRVBBTElWRS0yODUxNyJ9\"}}";
  @Test
  public void parseAlerts_alertWithoutNwsHeadlineInput_FirstNwsHeadlineIsCorrect() {
    AlertListParser parser = new AlertListParser(alertWithoutNwsHeadline);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getNwsHeadline();
    assertNull(result);
  }

  @Test
  public void parseAlerts_alertWithoutNwsHeadlineInput_SeverityIsModerate() {
    AlertListParser parser = new AlertListParser(alertWithoutNwsHeadline);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getSeverity();
    assertEquals("Moderate", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstSeverityIsMinor() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getSeverity();
    assertEquals("Minor", result);
  }

  @Test
  public void getUrgency_Correct() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getUrgency();
    assertEquals("Expected", result);
  }

  @Test
  public void getCertainty_Correct() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getCertainty();
    assertEquals("Likely", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstTypeIsUpdate() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getType();
    assertEquals("Update", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondTypeIsAlert() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getType();
    assertEquals("Alert", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstIdIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String name = parsed.get(0).getId();
    assertEquals("NWS-IDP-PROD-4559615-3770224", name);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstReferenceIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String id = parsed.get(0).getReference(0);
    assertEquals("NWS-IDP-PROD-4558114-3769815", id);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondReferenceIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String id = parsed.get(0).getReference(1);
    assertEquals("NWS-IDP-PROD-4556947-3769503", id);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_ReferenceCountIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals(2, parsed.get(0).getReferenceCount());
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SenderIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals("NWS Upton NY", parsed.get(0).getSender());
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SenderCodeIsCorrect() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals("OKX", parsed.get(0).getSenderCode());
  }

  @Test
  public void parseAlerts_GeometryNull_DoesNotHaveGeometry() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertFalse(parsed.get(0).hasGeometry());
  }

  @Test
  public void parseAlerts_GeometryGiven_HasGeometry() {
    AlertListParser parser = new AlertListParser(SpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertTrue(parsed.get(0).hasGeometry());
  }

  @Test
  public void parseAlerts_NoGeometryGiven_OneZoneLink() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals(1, parsed.get(0).getZoneLinkCount());
  }

  @Test
  public void parseAlerts_10ZoneLinksGiven_10ZoneLinksReturned() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals(10, parsed.get(1).getZoneLinkCount());
  }

  @Test
  public void parseAlerts_ZoneLinkGiven_CorrectLinkReturn() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals("https://api.weather.gov/zones/forecast/WIZ036", parsed.get(1).getZoneLink(0));
  }

  @Test
  public void parseAlerts_SecondZoneLinkGiven_CorrectLinkReturn() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals("https://api.weather.gov/zones/forecast/WIZ039", parsed.get(1).getZoneLink(1));
  }

  @Test
  public void parseAlerts_GeometryGivenInSecondAlert_FirstCoordLatitudeParsed() {
    String SmallCraftSpecialButPolygonInSecondAlert = "{\"@context\":[\"https://geojson.org/geojson-ld/geojson-context.jsonld\",{\"@version\":\"1.1\",\"wx\":\"https://api.weather.gov/ontology#\",\"@vocab\":\"https://api.weather.gov/ontology#\"}],\"type\":\"FeatureCollection\",\"features\":[{\"id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559615-3770224\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559615-3770224\",\"@type\":\"wx:Alert\",\"id\":\"NWS-IDP-PROD-4559615-3770224\",\"areaDesc\":\"Sandy Hook NJ to Fire Island Inlet NY out 20 nm\",\"geocode\":{\"UGC\":[\"ANZ355\"],\"SAME\":[\"073355\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/ANZ355\"],\"references\":[{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4558114-3769815\",\"identifier\":\"NWS-IDP-PROD-4558114-3769815\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2020-11-25T15:22:00-05:00\"},{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4556947-3769503\",\"identifier\":\"NWS-IDP-PROD-4556947-3769503\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2020-11-25T04:39:00-05:00\"}],\"sent\":\"2020-11-26T04:52:00-05:00\",\"effective\":\"2020-11-26T04:52:00-05:00\",\"onset\":\"2020-11-26T04:52:00-05:00\",\"expires\":\"2020-11-26T16:00:00-05:00\",\"ends\":\"2020-11-27T01:00:00-05:00\",\"status\":\"Actual\",\"messageType\":\"Update\",\"category\":\"Met\",\"severity\":\"Minor\",\"certainty\":\"Likely\",\"urgency\":\"Expected\",\"event\":\"Small Craft Advisory\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Upton NY\",\"headline\":\"Small Craft Advisory issued November 26 at 4:52AM EST until November 27 at 1:00AM EST by NWS Upton NY\",\"description\":\"* WHAT...Southwest winds 10 to 20 kt with gusts up to 25 kt and\\nseas 3 to 6 feet.\\n\\n* WHERE...Sandy Hook NJ to Fire Island Inlet NY out 20 nm.\\n\\n* WHEN...Until 1 AM EST Friday.\\n\\n* IMPACTS...Conditions will be hazardous to small craft.\",\"instruction\":\"Inexperienced mariners, especially those operating smaller\\nvessels, should avoid navigating in hazardous conditions.\",\"response\":\"Avoid\",\"parameters\":{\"NWSheadline\":[\"SMALL CRAFT ADVISORY NOW IN EFFECT UNTIL 1 AM EST FRIDAY\"],\"VTEC\":[\"/O.EXT.KOKX.SC.Y.0108.000000T0000Z-201127T0600Z/\"],\"PIL\":[\"OKXMWWOKX\"],\"BLOCKCHANNEL\":[\"CMAS\",\"EAS\",\"NWEM\"],\"eventEndingTime\":[\"2020-11-27T06:00:00+00:00\"]}}},{\"id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559554\",\"type\":\"Feature\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[-73.59,44.26],[-73.31,44.46],[-72.9,44.37],[-73.36,44],[-73.59,44.26]]]},\"properties\":{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559554\",\"@type\":\"wx:Alert\",\"id\":\"NWS-IDP-PROD-4559554\",\"areaDesc\":\"Portage; Brown; Waushara; Winnebago; Waupaca; Menominee; Shawano; Outagamie; Calumet; Wood\",\"geocode\":{\"UGC\":[\"WIZ036\",\"WIZ039\",\"WIZ045\",\"WIZ048\",\"WIZ037\",\"WIZ020\",\"WIZ031\",\"WIZ038\",\"WIZ049\",\"WIZ035\"],\"SAME\":[\"055097\",\"055009\",\"055137\",\"055139\",\"055135\",\"055078\",\"055115\",\"055087\",\"055015\",\"055141\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/WIZ036\",\"https://api.weather.gov/zones/forecast/WIZ039\",\"https://api.weather.gov/zones/forecast/WIZ045\",\"https://api.weather.gov/zones/forecast/WIZ048\",\"https://api.weather.gov/zones/forecast/WIZ037\",\"https://api.weather.gov/zones/forecast/WIZ020\",\"https://api.weather.gov/zones/forecast/WIZ031\",\"https://api.weather.gov/zones/forecast/WIZ038\",\"https://api.weather.gov/zones/forecast/WIZ049\",\"https://api.weather.gov/zones/forecast/WIZ035\"],\"references\":[],\"sent\":\"2020-11-26T03:12:00-06:00\",\"effective\":\"2020-11-26T03:12:00-06:00\",\"onset\":\"2020-11-26T03:12:00-06:00\",\"expires\":\"2020-11-26T09:00:00-06:00\",\"ends\":null,\"status\":\"Actual\",\"messageType\":\"Alert\",\"category\":\"Met\",\"severity\":\"Moderate\",\"certainty\":\"Observed\",\"urgency\":\"Expected\",\"event\":\"Special Weather Statement\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Green Bay WI\",\"headline\":\"Special Weather Statement issued November 26 at 3:12AM CST by NWS Green Bay WI\",\"description\":\"The combination of light winds and abundant low-level moisture\\nwill result in areas of fog and drizzle across central, northeast\\nand east central Wisconsin this morning. Some of the fog may\\nbecome locally dense. Poor visibilities and wet roads may result\\nin locally hazardous travel conditions for holiday travelers. The\\nfoggy conditions should improve by 9 am.\\n\\nMotorists are urged to slow down, keep a safe distance from other\\nvehicles, and use their low-beam headlights. Be prepared for rapid\\nchanges in visibility.\",\"instruction\":null,\"response\":\"Execute\",\"parameters\":{\"NWSheadline\":[\"AREAS OF FOG AND DRIZZLE THIS MORNING\"],\"EAS-ORG\":[\"WXR\"],\"PIL\":[\"GRBSPSGRB\"],\"BLOCKCHANNEL\":[\"CMAS\",\"EAS\",\"NWEM\"]}}}],\"title\":\"watches, warnings, and advisories\",\"updated\":\"2020-11-26T09:16:30+00:00\",\"pagination\":{\"next\":\"https://api.weather.gov/alerts?cursor=eyJ0IjoxNjA2MzM4Mzc3LCJpIjoiTldTLUlEUC1QUk9ELUtFRVBBTElWRS0yODUxNyJ9\"}}";
    AlertListParser parser = new AlertListParser(SmallCraftSpecialButPolygonInSecondAlert);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals(44.25, parsed.get(1).getPolygon().getCoordinate(0).getLat(), 0.01);
  }

  @Test
  public void parseAlerts_expiredReferencesGiven_InReferences() {
    AlertListParser parser = new AlertListParser(expiredReferences);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals("urn:oid:2.49.0.1.840.0.eccf59274026bdea1c5880007055754dd5755bb4.001.1", parsed.get(0).getReference(1));
  }

  @Test
  public void parseAlerts_expiredReferencesGiven_SecondCorrect() {
    AlertListParser parser = new AlertListParser(expiredReferences);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals("urn:oid:2.49.0.1.840.0.983b5665cc8db630b185297896d02addc61ef95a.001.1", parsed.get(0).getReference(2));
  }

  @Test
  public void parseAlerts_replacedByParsed() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals("urn:oid:2.49.0.1.840.0.f2b344bad8e99c947eb0cd944633f7c118757e70.001.1", parsed.get(0).getReplacedBy());
  }

  @Test
  public void parseAlerts_replacedAtParsed() {
    AlertListParser parser = new AlertListParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals("2021-04-29T19:10:00-05:00", parsed.get(0).getReplacedAt());
  }

  @Test
  public void parseAlerts_eventMotionDescription() {
    AlertListParser parser = new AlertListParser(eventMotion);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals("2021-05-22T04:24:00-00:00...storm...220DEG...49KT...39.89,-102.05", parsed.get(0).getMotionDescription());
  }

  String noPIL = "{\"@context\":[\"https://geojson.org/geojson-ld/geojson-context.jsonld\",{\"@version\":\"1.1\",\"wx\":\"https://api.weather.gov/ontology#\",\"@vocab\":\"https://api.weather.gov/ontology#\"}],\"type\":\"FeatureCollection\",\"features\":[{\"id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.f2b9e8dae4814a9f6d19c82d7bb9e78ddf95b8ed.002.1\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.f2b9e8dae4814a9f6d19c82d7bb9e78ddf95b8ed.002.1\",\"@type\":\"wx:Alert\",\"id\":\"urn:oid:2.49.0.1.840.0.f2b9e8dae4814a9f6d19c82d7bb9e78ddf95b8ed.002.1\",\"areaDesc\":\"Northern Middlesex; Northern New London; Southern New London; Eastern Passaic; Western Bergen; Rockland; Northern Westchester\",\"geocode\":{\"SAME\":[\"009007\",\"009011\",\"034031\",\"034003\",\"036087\",\"036119\"],\"UGC\":[\"CTZ007\",\"CTZ008\",\"CTZ012\",\"NJZ004\",\"NJZ103\",\"NYZ069\",\"NYZ070\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/CTZ007\",\"https://api.weather.gov/zones/forecast/CTZ008\",\"https://api.weather.gov/zones/forecast/CTZ012\",\"https://api.weather.gov/zones/forecast/NJZ004\",\"https://api.weather.gov/zones/forecast/NJZ103\",\"https://api.weather.gov/zones/forecast/NYZ069\",\"https://api.weather.gov/zones/forecast/NYZ070\"],\"references\":[{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.60982544a6fc15220595c438a8aaae63a2c6bf71.002.2\",\"identifier\":\"urn:oid:2.49.0.1.840.0.60982544a6fc15220595c438a8aaae63a2c6bf71.002.2\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2021-11-03T03:58:00-04:00\"},{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.60982544a6fc15220595c438a8aaae63a2c6bf71.001.2\",\"identifier\":\"urn:oid:2.49.0.1.840.0.60982544a6fc15220595c438a8aaae63a2c6bf71.001.2\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2021-11-03T03:58:00-04:00\"},{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.e24fed4b4bceb3c4132c7c5db2402f9b02eb7253.002.2\",\"identifier\":\"urn:oid:2.49.0.1.840.0.e24fed4b4bceb3c4132c7c5db2402f9b02eb7253.002.2\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2021-11-03T08:28:00-04:00\"},{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.e24fed4b4bceb3c4132c7c5db2402f9b02eb7253.004.1\",\"identifier\":\"urn:oid:2.49.0.1.840.0.e24fed4b4bceb3c4132c7c5db2402f9b02eb7253.004.1\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2021-11-03T08:28:00-04:00\"},{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.f2a1f37ce7c326c5eada266a4f6aa972d4164947.002.2\",\"identifier\":\"urn:oid:2.49.0.1.840.0.f2a1f37ce7c326c5eada266a4f6aa972d4164947.002.2\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2021-11-03T09:38:00-04:00\"},{\"@id\":\"https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.f2a1f37ce7c326c5eada266a4f6aa972d4164947.003.1\",\"identifier\":\"urn:oid:2.49.0.1.840.0.f2a1f37ce7c326c5eada266a4f6aa972d4164947.003.1\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2021-11-03T09:38:00-04:00\"}],\"sent\":\"2021-11-03T15:23:00-04:00\",\"effective\":\"2021-11-03T15:23:00-04:00\",\"onset\":\"2021-11-04T00:00:00-04:00\",\"expires\":\"2021-11-04T05:00:00-04:00\",\"ends\":\"2021-11-04T09:00:00-04:00\",\"status\":\"Actual\",\"messageType\":\"Update\",\"category\":\"Met\",\"severity\":\"Moderate\",\"certainty\":\"Likely\",\"urgency\":\"Expected\",\"event\":\"Freeze Warning\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Upton NY\",\"headline\":\"Freeze Warning issued November 3 at 3:23PM EDT until November 4 at 9:00AM EDT by NWS Upton NY\",\"description\":\"* WHAT...Sub-freezing temperatures as low as 30 expected.\\n\\n* WHERE...In New Jersey, Eastern Passaic and Western Bergen\\nCounties. In Connecticut, Northern New London, Southern New\\nLondon and Northern Middlesex Counties. In New York, Northern\\nWestchester and Rockland Counties.\\n\\n* WHEN...From midnight tonight to 9 AM EDT Thursday.\\n\\n* IMPACTS...Frost and freeze conditions will kill crops, other\\nsensitive vegetation and possibly damage unprotected outdoor\\nplumbing.\",\"instruction\":\"Take steps now to protect tender plants from the cold. To prevent\\nfreezing and possible bursting of outdoor water pipes they should\\nbe wrapped, drained, or allowed to drip slowly. Those that have\\nin-ground sprinkler systems should drain them and cover above-\\nground pipes to protect them from freezing.\",\"response\":\"Execute\",\"parameters\":{\"AWIPSidentifier\":[\"NPWOKX\"],\"WMOidentifier\":[\"WWUS71 KOKX 031923\"],\"NWSheadline\":[\"FREEZE WARNING NOW IN EFFECT FROM MIDNIGHT TONIGHT TO 9 AM EDT THURSDAY\"],\"BLOCKCHANNEL\":[\"EAS\",\"NWEM\",\"CMAS\"],\"VTEC\":[\"/O.EXT.KOKX.FZ.W.0006.211104T0400Z-211104T1300Z/\"],\"eventEndingTime\":[\"2021-11-04T13:00:00+00:00\"]}}}]}";
  @Test
  public void parseAlerts_NoPILInParameters_SenderCodeIsCorrect() {
    AlertListParser parser = new AlertListParser(noPIL);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals("OKX", parsed.get(0).getSenderCode());
  }
}
