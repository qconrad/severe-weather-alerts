package com.severeweatheralerts;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.severeweatheralerts.JSONParsing.AlertParser;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class JsonParserTests {
  @Test
  public void useAppContext() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    assertEquals("com.severeweatheralerts", appContext.getPackageName());
  }

  @Test
  public void newJsonAlertsParser_emptyJSONString_throwsException() {
    try {
      new AlertParser("");
      fail("IllegalArgumentException was not called on invalid JSON");
    } catch(IllegalArgumentException e){
      assertTrue(true);
    }
  }

  // String that contains one special weather statement
  String SpecialWeatherStatementInput = "{\"@context\":[\"https://geojson.org/geojson-ld/geojson-context.jsonld\",{\"@version\":\"1.1\",\"wx\":\"https://api.weather.gov/ontology#\",\"@vocab\":\"https://api.weather.gov/ontology#\"}],\"type\":\"FeatureCollection\",\"features\":[{\"id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559554\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559554\",\"@type\":\"wx:Alert\",\"id\":\"NWS-IDP-PROD-4559554\",\"areaDesc\":\"Portage; Brown; Waushara; Winnebago; Waupaca; Menominee; Shawano; Outagamie; Calumet; Wood\",\"geocode\":{\"UGC\":[\"WIZ036\",\"WIZ039\",\"WIZ045\",\"WIZ048\",\"WIZ037\",\"WIZ020\",\"WIZ031\",\"WIZ038\",\"WIZ049\",\"WIZ035\"],\"SAME\":[\"055097\",\"055009\",\"055137\",\"055139\",\"055135\",\"055078\",\"055115\",\"055087\",\"055015\",\"055141\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/WIZ036\",\"https://api.weather.gov/zones/forecast/WIZ039\",\"https://api.weather.gov/zones/forecast/WIZ045\",\"https://api.weather.gov/zones/forecast/WIZ048\",\"https://api.weather.gov/zones/forecast/WIZ037\",\"https://api.weather.gov/zones/forecast/WIZ020\",\"https://api.weather.gov/zones/forecast/WIZ031\",\"https://api.weather.gov/zones/forecast/WIZ038\",\"https://api.weather.gov/zones/forecast/WIZ049\",\"https://api.weather.gov/zones/forecast/WIZ035\"],\"references\":[],\"sent\":\"2020-11-26T03:12:00-06:00\",\"effective\":\"2020-11-26T03:12:00-06:00\",\"onset\":\"2020-11-26T03:12:00-06:00\",\"expires\":\"2020-11-26T09:00:00-06:00\",\"ends\":null,\"status\":\"Actual\",\"messageType\":\"Alert\",\"category\":\"Met\",\"severity\":\"Moderate\",\"certainty\":\"Observed\",\"urgency\":\"Expected\",\"event\":\"Special Weather Statement\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Green Bay WI\",\"headline\":\"Special Weather Statement issued November 26 at 3:12AM CST by NWS Green Bay WI\",\"description\":\"The combination of light winds and abundant low-level moisture\\nwill result in areas of fog and drizzle across central, northeast\\nand east central Wisconsin this morning. Some of the fog may\\nbecome locally dense. Poor visibilities and wet roads may result\\nin locally hazardous travel conditions for holiday travelers. The\\nfoggy conditions should improve by 9 am.\\n\\nMotorists are urged to slow down, keep a safe distance from other\\nvehicles, and use their low-beam headlights. Be prepared for rapid\\nchanges in visibility.\",\"instruction\":null,\"response\":\"Execute\",\"parameters\":{\"NWSheadline\":[\"AREAS OF FOG AND DRIZZLE THIS MORNING\"],\"EAS-ORG\":[\"WXR\"],\"PIL\":[\"GRBSPSGRB\"],\"BLOCKCHANNEL\":[\"CMAS\",\"EAS\",\"NWEM\"]}}}],\"title\":\"watches, warnings, and advisories\",\"updated\":\"2020-11-26T09:16:30+00:00\",\"pagination\":{\"next\":\"https://api.weather.gov/alerts?cursor=eyJ0IjoxNjA2MzM4Mzc3LCJpIjoiTldTLUlEUC1QUk9ELUtFRVBBTElWRS0yODUxNyJ9\"}}";
  @Test
  public void newJsonAlertsParser_validString_doesNotThrowExceptions() {
    try {
      new AlertParser(SpecialWeatherStatementInput);
      assertTrue(true);
    } catch(IllegalArgumentException e){
      fail("Valid JSON was provided but IllegalArgumentException was thrown.");
    }
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementInput_ReturnsTwoParsedAlert() {
    AlertParser parser = new AlertParser(SpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals(1, parsed.size());
  }

  String SmallCraftAdvisoryAndSpecialWeatherStatementInput = "{\"@context\":[\"https://geojson.org/geojson-ld/geojson-context.jsonld\",{\"@version\":\"1.1\",\"wx\":\"https://api.weather.gov/ontology#\",\"@vocab\":\"https://api.weather.gov/ontology#\"}],\"type\":\"FeatureCollection\",\"features\":[{\"id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559615-3770224\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559615-3770224\",\"@type\":\"wx:Alert\",\"id\":\"NWS-IDP-PROD-4559615-3770224\",\"areaDesc\":\"Sandy Hook NJ to Fire Island Inlet NY out 20 nm\",\"geocode\":{\"UGC\":[\"ANZ355\"],\"SAME\":[\"073355\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/ANZ355\"],\"references\":[{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4558114-3769815\",\"identifier\":\"NWS-IDP-PROD-4558114-3769815\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2020-11-25T15:22:00-05:00\"},{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4556947-3769503\",\"identifier\":\"NWS-IDP-PROD-4556947-3769503\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"sent\":\"2020-11-25T04:39:00-05:00\"}],\"sent\":\"2020-11-26T04:52:00-05:00\",\"effective\":\"2020-11-26T04:52:00-05:00\",\"onset\":\"2020-11-26T04:52:00-05:00\",\"expires\":\"2020-11-26T16:00:00-05:00\",\"ends\":\"2020-11-27T01:00:00-05:00\",\"status\":\"Actual\",\"messageType\":\"Update\",\"category\":\"Met\",\"severity\":\"Minor\",\"certainty\":\"Likely\",\"urgency\":\"Expected\",\"event\":\"Small Craft Advisory\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Upton NY\",\"headline\":\"Small Craft Advisory issued November 26 at 4:52AM EST until November 27 at 1:00AM EST by NWS Upton NY\",\"description\":\"* WHAT...Southwest winds 10 to 20 kt with gusts up to 25 kt and\\nseas 3 to 6 feet.\\n\\n* WHERE...Sandy Hook NJ to Fire Island Inlet NY out 20 nm.\\n\\n* WHEN...Until 1 AM EST Friday.\\n\\n* IMPACTS...Conditions will be hazardous to small craft.\",\"instruction\":\"Inexperienced mariners, especially those operating smaller\\nvessels, should avoid navigating in hazardous conditions.\",\"response\":\"Avoid\",\"parameters\":{\"NWSheadline\":[\"SMALL CRAFT ADVISORY NOW IN EFFECT UNTIL 1 AM EST FRIDAY\"],\"VTEC\":[\"/O.EXT.KOKX.SC.Y.0108.000000T0000Z-201127T0600Z/\"],\"PIL\":[\"OKXMWWOKX\"],\"BLOCKCHANNEL\":[\"CMAS\",\"EAS\",\"NWEM\"],\"eventEndingTime\":[\"2020-11-27T06:00:00+00:00\"]}}},{\"id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559554\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4559554\",\"@type\":\"wx:Alert\",\"id\":\"NWS-IDP-PROD-4559554\",\"areaDesc\":\"Portage; Brown; Waushara; Winnebago; Waupaca; Menominee; Shawano; Outagamie; Calumet; Wood\",\"geocode\":{\"UGC\":[\"WIZ036\",\"WIZ039\",\"WIZ045\",\"WIZ048\",\"WIZ037\",\"WIZ020\",\"WIZ031\",\"WIZ038\",\"WIZ049\",\"WIZ035\"],\"SAME\":[\"055097\",\"055009\",\"055137\",\"055139\",\"055135\",\"055078\",\"055115\",\"055087\",\"055015\",\"055141\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/WIZ036\",\"https://api.weather.gov/zones/forecast/WIZ039\",\"https://api.weather.gov/zones/forecast/WIZ045\",\"https://api.weather.gov/zones/forecast/WIZ048\",\"https://api.weather.gov/zones/forecast/WIZ037\",\"https://api.weather.gov/zones/forecast/WIZ020\",\"https://api.weather.gov/zones/forecast/WIZ031\",\"https://api.weather.gov/zones/forecast/WIZ038\",\"https://api.weather.gov/zones/forecast/WIZ049\",\"https://api.weather.gov/zones/forecast/WIZ035\"],\"references\":[],\"sent\":\"2020-11-26T03:12:00-06:00\",\"effective\":\"2020-11-26T03:12:00-06:00\",\"onset\":\"2020-11-26T03:12:00-06:00\",\"expires\":\"2020-11-26T09:00:00-06:00\",\"ends\":null,\"status\":\"Actual\",\"messageType\":\"Alert\",\"category\":\"Met\",\"severity\":\"Moderate\",\"certainty\":\"Observed\",\"urgency\":\"Expected\",\"event\":\"Special Weather Statement\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Green Bay WI\",\"headline\":\"Special Weather Statement issued November 26 at 3:12AM CST by NWS Green Bay WI\",\"description\":\"The combination of light winds and abundant low-level moisture\\nwill result in areas of fog and drizzle across central, northeast\\nand east central Wisconsin this morning. Some of the fog may\\nbecome locally dense. Poor visibilities and wet roads may result\\nin locally hazardous travel conditions for holiday travelers. The\\nfoggy conditions should improve by 9 am.\\n\\nMotorists are urged to slow down, keep a safe distance from other\\nvehicles, and use their low-beam headlights. Be prepared for rapid\\nchanges in visibility.\",\"instruction\":null,\"response\":\"Execute\",\"parameters\":{\"NWSheadline\":[\"AREAS OF FOG AND DRIZZLE THIS MORNING\"],\"EAS-ORG\":[\"WXR\"],\"PIL\":[\"GRBSPSGRB\"],\"BLOCKCHANNEL\":[\"CMAS\",\"EAS\",\"NWEM\"]}}}],\"title\":\"watches, warnings, and advisories\",\"updated\":\"2020-11-26T09:16:30+00:00\",\"pagination\":{\"next\":\"https://api.weather.gov/alerts?cursor=eyJ0IjoxNjA2MzM4Mzc3LCJpIjoiTldTLUlEUC1QUk9ELUtFRVBBTElWRS0yODUxNyJ9\"}}";
  @Test
  public void parseAlerts_SmallCraftAndSpecialInputGiven_ReturnsTwoParsedAlerts() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals(2, parsed.size());
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementInputGiven_FirstAlertHasThatName() {
    AlertParser parser = new AlertParser(SpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String name = parsed.get(0).getName();
    assertEquals("Special Weather Statement", name);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstAlertHasCorrectName() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String name = parsed.get(0).getName();
    assertEquals("Small Craft Advisory", name);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondAlertHasCorrectName() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String name = parsed.get(1).getName();
    assertEquals("Special Weather Statement", name);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstDescriptionIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String desc = "* WHAT...Southwest winds 10 to 20 kt with gusts up to 25 kt and\nseas 3 to 6 feet.\n\n* WHERE...Sandy Hook NJ to Fire Island Inlet NY out 20 nm.\n\n* WHEN...Until 1 AM EST Friday.\n\n* IMPACTS...Conditions will be hazardous to small craft.";
    String result = parsed.get(0).getDescription();
    assertEquals(desc, result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondDescriptionIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String desc = "The combination of light winds and abundant low-level moisture\nwill result in areas of fog and drizzle across central, northeast\nand east central Wisconsin this morning. Some of the fog may\nbecome locally dense. Poor visibilities and wet roads may result\nin locally hazardous travel conditions for holiday travelers. The\nfoggy conditions should improve by 9 am.\n\nMotorists are urged to slow down, keep a safe distance from other\nvehicles, and use their low-beam headlights. Be prepared for rapid\nchanges in visibility.";
    String result = parsed.get(1).getDescription();
    assertEquals(desc, result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstInstructionIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String instruction = "Inexperienced mariners, especially those operating smaller\nvessels, should avoid navigating in hazardous conditions.";
    String result = parsed.get(0).getInstruction();
    assertEquals(instruction, result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondInstructionIsNull() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getInstruction();
    assertNull(result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstSentIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getSent();
    assertEquals("2020-11-26T04:52:00-05:00", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondSentIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getSent();
    assertEquals("2020-11-26T03:12:00-06:00", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondOnsetIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getOnset();
    assertEquals("2020-11-26T03:12:00-06:00", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondExpiresIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getExpires();
    assertEquals("2020-11-26T09:00:00-06:00", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstEndsIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getEnds();
    assertEquals("2020-11-27T01:00:00-05:00", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondEndsIsNull() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getEnds();
    assertNull(result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstNwsHeadlineIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getNwsHeadline();
    assertEquals("SMALL CRAFT ADVISORY NOW IN EFFECT UNTIL 1 AM EST FRIDAY", result);
  }

  String alertWithoutNwsHeadline = "{\"@context\":[\"https://geojson.org/geojson-ld/geojson-context.jsonld\",{\"@version\":\"1.1\",\"wx\":\"https://api.weather.gov/ontology#\",\"@vocab\":\"https://api.weather.gov/ontology#\"}],\"type\":\"FeatureCollection\",\"features\":[{\"id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4569386\",\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"@id\":\"https://api.weather.gov/alerts/NWS-IDP-PROD-4569386\",\"@type\":\"wx:Alert\",\"id\":\"NWS-IDP-PROD-4569386\",\"areaDesc\":\"Foothills of the Blue Mountains of Washington; Foothills of the Northern Blue Mountains of Oregon; Lower Columbia Basin of Washington; Yakima Valley; Lower Columbia Basin of Oregon\",\"geocode\":{\"UGC\":[\"WAZ029\",\"ORZ507\",\"WAZ028\",\"WAZ027\",\"ORZ044\"],\"SAME\":[\"053013\",\"053071\",\"041059\",\"053005\",\"053021\",\"053039\",\"053077\",\"041049\"]},\"affectedZones\":[\"https://api.weather.gov/zones/forecast/WAZ029\",\"https://api.weather.gov/zones/forecast/ORZ507\",\"https://api.weather.gov/zones/forecast/WAZ028\",\"https://api.weather.gov/zones/forecast/WAZ027\",\"https://api.weather.gov/zones/forecast/ORZ044\"],\"references\":[],\"sent\":\"2020-11-29T23:33:00-08:00\",\"effective\":\"2020-11-29T23:33:00-08:00\",\"onset\":\"2020-11-29T23:33:00-08:00\",\"expires\":\"2020-11-30T07:00:00-08:00\",\"ends\":null,\"status\":\"Actual\",\"messageType\":\"Alert\",\"category\":\"Met\",\"severity\":\"Moderate\",\"certainty\":\"Observed\",\"urgency\":\"Expected\",\"event\":\"Special Weather Statement\",\"sender\":\"w-nws.webmaster@noaa.gov\",\"senderName\":\"NWS Pendleton OR\",\"headline\":\"Special Weather Statement issued November 29 at 11:33PM PST by NWS Pendleton OR\",\"description\":\"Areas of dense fog have developed across portions of the Lower\\nColumbia Basin and the foothills of the Blue Mountains in Oregon\\nand Washington, as in the Yakima Valley. Visibility of a quarter\\nmile or less expected. Road and air temperatures are at or\\nslightly below freezing, leading to some slick roadways overnight\\ninto the morning hours.\",\"instruction\":null,\"response\":\"Execute\",\"parameters\":{\"EAS-ORG\":[\"WXR\"],\"PIL\":[\"PDTSPSPDT\"],\"BLOCKCHANNEL\":[\"CMAS\",\"EAS\",\"NWEM\"]}}}],\"title\":\"watches, warnings, and advisories\",\"updated\":\"2020-11-26T09:16:30+00:00\",\"pagination\":{\"next\":\"https://api.weather.gov/alerts?cursor=eyJ0IjoxNjA2MzM4Mzc3LCJpIjoiTldTLUlEUC1QUk9ELUtFRVBBTElWRS0yODUxNyJ9\"}}";
  @Test
  public void parseAlerts_alertWithoutNwsHeadlineInput_FirstNwsHeadlineIsCorrect() {
    AlertParser parser = new AlertParser(alertWithoutNwsHeadline);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getNwsHeadline();
    assertNull(result);
  }

  @Test
  public void parseAlerts_alertWithoutNwsHeadlineInput_SeverityIsModerate() {
    AlertParser parser = new AlertParser(alertWithoutNwsHeadline);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getSeverity();
    assertEquals("Moderate", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstSeverityIsMinor() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getSeverity();
    assertEquals("Minor", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstTypeIsUpdate() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(0).getType();
    assertEquals("Update", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondTypeIsAlert() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String result = parsed.get(1).getType();
    assertEquals("Alert", result);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstIdIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String name = parsed.get(0).getId();
    assertEquals("NWS-IDP-PROD-4559615-3770224", name);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_FirstReferenceIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String id = parsed.get(0).getReference(0);
    assertEquals("NWS-IDP-PROD-4558114-3769815", id);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_SecondReferenceIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    String id = parsed.get(0).getReference(1);
    assertEquals("NWS-IDP-PROD-4556947-3769503", id);
  }

  @Test
  public void parseAlerts_SpecialWeatherStatementAndSmallCraftInputGiven_ReferenceCountIsCorrect() {
    AlertParser parser = new AlertParser(SmallCraftAdvisoryAndSpecialWeatherStatementInput);
    ArrayList<UnadaptedAlert> parsed = parser.getParsedAlerts();
    assertEquals(2, parsed.get(0).getReferenceCount());
  }
}
