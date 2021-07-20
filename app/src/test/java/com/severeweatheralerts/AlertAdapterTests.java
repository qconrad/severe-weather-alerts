package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.AlertAdapter;
import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Adapters.GeoJSONPolygon;
import com.severeweatheralerts.Adapters.UnadaptedAlert;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.Alerts.NWS.WinterStormWarning;
import com.severeweatheralerts.Alerts.NWS.WinterWeatherAdvisory;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class AlertAdapterTests {
  @Test
  public void adaptAlerts_OneWinterStormWarningIsAdded_AlertListWithThatNameIsReturned() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setName("Winter Storm Warning");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("Winter Storm Warning", aa.getAdaptedAlerts().get(0).getName());
  }

  @Test
  public void adaptAlerts_OneWinterWeatherAdvisoryIsAdded_DescriptionIsParsedCorrectlyAndNewLinesAreReadable() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setName("Winter Weather Advisory");
    pa.setDescription("* WHAT...Lake effect snow and strong winds expected. Total snow\naccumulations of 2 to 5 inches across Porter County and\nnortheast Jasper County. North winds gusting as high as 45 mph\nalong the Lake Michigan shoreline and to 40 mph inland through\nthis afternoon.\n\n* WHERE...Porter and Jasper Counties.\n\n* WHEN...Through 6 AM CST Tuesday.\n\n* IMPACTS...Lake effect snow can produce rapidly changing\nconditions, including sharp reductions in visibility, over\nshort distances. Plan on slippery road conditions, especially\nunder any heavier lake effect snow bands. Additionally, the\nstrong north winds will result in difficult travel for high\nprofile vehicles on west to east oriented roads. The hazardous\nconditions due to snow and wind will likely impact the\ninterstate 80, 90 and 94 corridors through northwest Indiana,\ndeveloping this morning and persisting through early Tuesday\nmorning.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    String expectedParse = "* WHAT...Lake effect snow and strong winds expected. Total snow accumulations of 2 to 5 inches across Porter County and northeast Jasper County. North winds gusting as high as 45 mph along the Lake Michigan shoreline and to 40 mph inland through this afternoon.\n\n* WHERE...Porter and Jasper Counties.\n\n* WHEN...Through 6 AM CST Tuesday.\n\n* IMPACTS...Lake effect snow can produce rapidly changing conditions, including sharp reductions in visibility, over short distances. Plan on slippery road conditions, especially under any heavier lake effect snow bands. Additionally, the strong north winds will result in difficult travel for high profile vehicles on west to east oriented roads. The hazardous conditions due to snow and wind will likely impact the interstate 80, 90 and 94 corridors through northwest Indiana, developing this morning and persisting through early Tuesday morning.";
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void adaptAlerts_OneMinorSeverityAlertIsAdded_SeverityIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setSeverity("Minor");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Severity.MINOR, aa.getAdaptedAlerts().get(0).getSeverity());
  }

  @Test
  public void adaptAlerts_OneModerateSeverityAlertIsAdded_SeverityIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setSeverity("Moderate");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Severity.MODERATE, aa.getAdaptedAlerts().get(0).getSeverity());
  }

  @Test
  public void adaptAlerts_OneSevereAlertSeverityIsAdded_SeverityIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setSeverity("Severe");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Severity.SEVERE, aa.getAdaptedAlerts().get(0).getSeverity());
  }

  @Test
  public void adaptAlerts_OneExtremeSeverityAlertIsAdded_SeverityIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setSeverity("Extreme");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Severity.EXTREME, aa.getAdaptedAlerts().get(0).getSeverity());
  }

  @Test
  public void adaptAlerts_OneUnkownSeverityAlertIsAdded_SeverityIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setSeverity("Unknown");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Severity.UNKNOWN, aa.getAdaptedAlerts().get(0).getSeverity());
  }

  @Test
  public void adaptAlerts_HeadlineGiven_LargeHeadlineIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setNwsHeadline("WINTER WEATHER ADVISORY REMAINS IN EFFECT UNTIL 6 AM CST TUESDAY");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_HeadlineGiven_SmallHeadlineIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "WINTER WEATHER ADVISORY REMAINS IN EFFECT UNTIL 6 AM CST TUESDAY";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(headline, aa.getAdaptedAlerts().get(0).getSmallHeadline());
  }

  @Test
  public void adaptAlerts_HeadlineGiven_SmallHeadlineHasLineBreaksForEachStatement() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "SMALL CRAFT ADVISORY NOW IN EFFECT UNTIL 10 PM EST THIS EVENING... ...SMALL CRAFT ADVISORY REMAINS IN EFFECT FROM 9 AM TO 6 PM EST TUESDAY... ...GALE WARNING IS CANCELLED";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "SMALL CRAFT ADVISORY NOW IN EFFECT UNTIL 10 PM EST THIS EVENING\n\nSMALL CRAFT ADVISORY REMAINS IN EFFECT FROM 9 AM TO 6 PM EST TUESDAY\n\nGALE WARNING IS CANCELLED";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getSmallHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineHeadlineGiven_SmallHeadlineIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "LIGHT SNOW AND STRONG WINDS RETURN TO NORTHEAST AND EAST CENTRAL NM";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getSmallHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineHeadlineGiven_LargeHeadlineReturnsThatHeadlineInHeadlineCase() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "LIGHT SNOW AND STRONG WINDS RETURN TO NORTHEAST AND EAST CENTRAL NM";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "Light Snow and Strong Winds Return to Northeast and East Central NM";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineHeadlineGiven_LargeHeadlineIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "A STRONG THUNDERSTORM WILL AFFECT CENTRAL LITCHFIELD COUNTY";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "A Strong Thunderstorm Will Affect Central Litchfield County";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineHeadlineGiven_LargeHeadlineHasCommasAndTitleCase() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "A LINE OF DOWNPOURS WITH GUSTY WINDS WILL AFFECT NORTHERN BERKSHIRE...NORTHEASTERN COLUMBIA...SOUTHERN WASHINGTON...EASTERN RENSSELAER...BENNINGTON AND WESTERN WINDHAM COUNTIES";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "A Line of Downpours With Gusty Winds Will Affect Northern Berkshire, Northeastern Columbia, Southern Washington, Eastern Rensselaer, Bennington and Western Windham Counties";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineHeadlineAlreadyInTitleCaseGiven_SetAsLargeHeadlineAndFormattingUntouched() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "Forecast flooding increased from Minor to Moderate severity and increased in duration until late tonight";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "Forecast flooding increased from Minor to Moderate severity and increased in duration until late tonight";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineHeadlineGiven_LargeHeadlineHasCorrectTitleCase() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "HEAVY RAIN IN EASTERN MASSACHUSETTS AND EASTERN RHODE ISLAND MAY CAUSE LOCALIZED STREET FLOODING EARLY THIS EVENING";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "Heavy Rain in Eastern Massachusetts and Eastern Rhode Island May Cause Localized Street Flooding Early This Evening";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineIsExpireText_LargeHeadlineIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "THE SEVERE THUNDERSTORM WARNING FOR NORTHEASTERN MERCER...SOMERSET AND NORTHWESTERN MIDDLESEX COUNTIES WILL EXPIRE AT 615 PM EST";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineAndDescriptionHeadlineGiven_LargeHeadlineIsFromDescriptionAndCorrectCase() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String desc = "...WINTER WEATHER ADVISORY ISSUED FOR 4 NORTHEAST ALABAMA COUNTIES...\n\n.Scattered light snow showers have started to cause a few slick\nspots on roads in some higher elevations of Blount, St. Clair,\nEtowah, and Cherokee Counties.\n\n* WHAT...Light snow showers causing some slick spots on roads.\n\n* WHERE...Blount, Etowah, Cherokee and St. Clair Counties.\n\n* WHEN...Until midnight CST tonight.\n\n* IMPACTS...The hazardous conditions could impact the evening\ncommute.}";
    pa.setDescription(desc);
    String nwsHeadline = "WINTER WEATHER ADVISORY IN EFFECT UNTIL MIDNIGHT CST TONIGHT";
    pa.setNwsHeadline(nwsHeadline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("Winter Weather Advisory Issued for 4 Northeast Alabama Counties", aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineAndDescriptionHeadlineGiven_LargeHeadlineIsFromDescriptionAndCorrectCase2() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String desc = "...Additional Snowfall Accumulation Expected Tonight...\n\n* WHAT...Snow. Total snow accumulations of 1 to 2 inches.\n\n* WHERE...Cannon and De Kalb Counties.\n\n* WHEN...From noon today to 6 AM CST Tuesday.\n\n* IMPACTS...Plan on slippery road conditions. The hazardous\nconditions could impact the evening and Tuesday morning commute.";
    pa.setDescription(desc);
    String nwsHeadline = "WINTER WEATHER ADVISORY IN EFFECT UNTIL 6 AM CST TUESDAY";
    pa.setNwsHeadline(nwsHeadline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("Additional Snowfall Accumulation Expected Tonight", aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineAndDescriptionHeadlineGiven_SmallHeadlineIsNwsHeadline() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String desc = "...WINTER WEATHER ADVISORY ISSUED FOR 4 NORTHEAST ALABAMA COUNTIES...\n\n.Scattered light snow showers have started to cause a few slick\nspots on roads in some higher elevations of Blount, St. Clair,\nEtowah, and Cherokee Counties.\n\n* WHAT...Light snow showers causing some slick spots on roads.\n\n* WHERE...Blount, Etowah, Cherokee and St. Clair Counties.\n\n* WHEN...Until midnight CST tonight.\n\n* IMPACTS...The hazardous conditions could impact the evening\ncommute.";
    pa.setDescription(desc);
    String nwsHeadline = "WINTER WEATHER ADVISORY IN EFFECT UNTIL MIDNIGHT CST TONIGHT";
    pa.setNwsHeadline(nwsHeadline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(nwsHeadline, aa.getAdaptedAlerts().get(0).getSmallHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineAndDescriptionHeadlineGiven_DescriptionDoesNotContainThatHeadline() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String desc = "...WINTER WEATHER ADVISORY ISSUED FOR 4 NORTHEAST ALABAMA COUNTIES...\n\n.Scattered light snow showers have started to cause a few slick\nspots on roads in some higher elevations of Blount, St. Clair,\nEtowah, and Cherokee Counties.\n\n* WHAT...Light snow showers causing some slick spots on roads.\n\n* WHERE...Blount, Etowah, Cherokee and St. Clair Counties.\n\n* WHEN...Until midnight CST tonight.\n\n* IMPACTS...The hazardous conditions could impact the evening\ncommute.";
    pa.setDescription(desc);
    String nwsHeadline = "WINTER WEATHER ADVISORY IN EFFECT UNTIL MIDNIGHT CST TONIGHT";
    pa.setNwsHeadline(nwsHeadline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String actualDesc = aa.getAdaptedAlerts().get(0).getDescription();
    assertFalse("Description contains headline that should have been removed. Actual Description: \n" + actualDesc, actualDesc.contains("...WINTER WEATHER ADVISORY ISSUED FOR 4 NORTHEAST ALABAMA COUNTIES..."));
  }

  @Test
  public void adaptAlerts_NwsHeadlineAndDescriptionHeadlineGiven_DescriptionIsCorrectlyParsedAndDoesntHavePeriod() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String desc = "...WINTER WEATHER ADVISORY ISSUED FOR 4 NORTHEAST ALABAMA COUNTIES...\n\n.Scattered light snow showers have started to cause a few slick\nspots on roads in some higher elevations of Blount, St. Clair,\nEtowah, and Cherokee Counties.\n\n* WHAT...Light snow showers causing some slick spots on roads.\n\n* WHERE...Blount, Etowah, Cherokee and St. Clair Counties.\n\n* WHEN...Until midnight CST tonight.\n\n* IMPACTS...The hazardous conditions could impact the evening\ncommute.";
    pa.setDescription(desc);
    String nwsHeadline = "WINTER WEATHER ADVISORY IN EFFECT UNTIL MIDNIGHT CST TONIGHT";
    pa.setNwsHeadline(nwsHeadline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "Scattered light snow showers have started to cause a few slick spots on roads in some higher elevations of Blount, St. Clair, Etowah, and Cherokee Counties.\n\n* WHAT...Light snow showers causing some slick spots on roads.\n\n* WHERE...Blount, Etowah, Cherokee and St. Clair Counties.\n\n* WHEN...Until midnight CST tonight.\n\n* IMPACTS...The hazardous conditions could impact the evening commute.";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void adaptAlerts_SevereThunderstormWarningTextIsAdded_DescriptionIsParsedCorrectly() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("The National Weather Service in Mount Holly NJ has issued a\n\n* Severe Thunderstorm Warning for...\nNorthwestern Cumberland County in southern New Jersey...\nSalem County in southern New Jersey...\nCentral Camden County in southern New Jersey...\nGloucester County in southern New Jersey...\n\n* Until 530 PM EST.\n\n* At 448 PM EST, a severe thunderstorm was located over Newport\nMeadows, or 8 miles west of Bridgeton, moving northeast at 50 mph.\n\nHAZARD...60 mph wind gusts.\n\nSOURCE...Radar indicated.\n\nIMPACT...Damage to roofs, siding, trees, and power lines is\npossible.\n\n* Locations impacted include...\nCamden, Vineland, Bridgeton, Gloucester City, Deptford, Glassboro,\nLindenwold, Bellmawr, Woodbury, Pitman, Clayton, Berlin, Magnolia,\nWestville, Woodstown, Alloway, Woodbury Heights, Quinton, Brooklawn\nand Chesilhurst.\n\nThis includes the following highways...\nNew Jersey Turnpike near exit 3.\nInterstate 76 in New Jersey between mile markers 0 and 1.\nInterstate 295 in New Jersey between mile markers 24 and 31.\nInterstate 676 in New Jersey near mile marker 0.\nAtlantic City Expressway between mile markers 32 and 44.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    String expectedParse = "The National Weather Service in Mount Holly NJ has issued a\n\n* Severe Thunderstorm Warning for...\nNorthwestern Cumberland County in southern New Jersey...\nSalem County in southern New Jersey...\nCentral Camden County in southern New Jersey...\nGloucester County in southern New Jersey...\n\n* Until 530 PM EST.\n\n* At 448 PM EST, a severe thunderstorm was located over Newport Meadows, or 8 miles west of Bridgeton, moving northeast at 50 mph.\n\nHAZARD...60 mph wind gusts.\n\nSOURCE...Radar indicated.\n\nIMPACT...Damage to roofs, siding, trees, and power lines is possible.\n\n* Locations impacted include...\nCamden, Vineland, Bridgeton, Gloucester City, Deptford, Glassboro, Lindenwold, Bellmawr, Woodbury, Pitman, Clayton, Berlin, Magnolia, Westville, Woodstown, Alloway, Woodbury Heights, Quinton, Brooklawn and Chesilhurst.\n\nThis includes the following highways...\nNew Jersey Turnpike near exit 3.\nInterstate 76 in New Jersey between mile markers 0 and 1.\nInterstate 295 in New Jersey between mile markers 24 and 31.\nInterstate 676 in New Jersey near mile marker 0.\nAtlantic City Expressway between mile markers 32 and 44.";
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void adaptAlerts_InstructionIsProvided_InstructionIsParsedCorrectly() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setInstruction("Protecting yourself from immediate threats to life and safety shall\ntake priority.  Whenever possible, as long as it does not cause\ngreater harm, all COVID-19 protective action guidance should be\nfollowed.\n\nA Tornado Watch remains in effect until 700 PM EST for southern New\nJersey.\n\nFor your protection move to an interior room on the lowest floor of a\nbuilding.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    String expectedParse = "Protecting yourself from immediate threats to life and safety shall take priority.  Whenever possible, as long as it does not cause greater harm, all COVID-19 protective action guidance should be followed.\n\nA Tornado Watch remains in effect until 700 PM EST for southern New Jersey.\n\nFor your protection move to an interior room on the lowest floor of a building.";
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getInstruction());
  }

  @Test
  public void adaptAlerts_HeadlineWithSpacesAfterDotsGiven_CommasAreCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setNwsHeadline("SNOW REDUCING VISIBILITY WILL AFFECT CENTRAL STILLWATER... WHEATLAND...NORTH CENTRAL GOLDEN VALLEY...SWEET GRASS AND CENTRAL PARK COUNTIES");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "Snow Reducing Visibility Will Affect Central Stillwater, Wheatland, North Central Golden Valley, Sweet Grass and Central Park Counties";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineIsProvided_TitleCaseIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setNwsHeadline("A FEW SNOW FLURRIES POSSIBLE THIS EVENING ACROSS THE NORTHERN UPSTATE AND THE FOOTHILLS AND WESTERN PIEDMONT OF NORTH CAROLINA");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "A Few Snow Flurries Possible This Evening Across the Northern Upstate and the Foothills and Western Piedmont of North Carolina";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineWithTheAsFirstWordIsProvided_FirstWordIsCapatalized() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setNwsHeadline("THE STORM IS COMING");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "The Storm Is Coming";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_AlertTypeIsProvided_TypeIsPost() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setType("Alert");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Type.POST, aa.getAdaptedAlerts().get(0).getType());
  }

  @Test
  public void adaptAlerts_UpdateTypeIsProvided_TypeIsUpdate() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setType("Update");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Type.UPDATE, aa.getAdaptedAlerts().get(0).getType());
  }

  @Test
  public void adaptAlerts_CancelTypeIsProvided_TypeIsCancel() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setType("Cancel");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Type.CANCEL, aa.getAdaptedAlerts().get(0).getType());
  }

  @Test
  public void adaptAlerts_InstructionIsGivenWithCancelType_InstructionIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setType("Cancel");
    pa.setInstruction("This is a test instruction");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getInstruction());
  }

  @Test
  public void adaptAlerts_ExpirationHeadlineIsProvided_SmallHeadlineIsSet() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "WINTER STORM WARNING WILL EXPIRE AT NOON AKST TODAY";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(headline, aa.getAdaptedAlerts().get(0).getSmallHeadline());
  }

  @Test
  public void adaptAlerts_ExpirationHeadlineIsProvided_LargeHeadlineIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setNwsHeadline("WINTER STORM WARNING WILL EXPIRE AT NOON AKST TODAY");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_SentTimeStringIsProvided_CorrectlyConvertedToDateObject() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setSent("2020-11-26T04:52:00-0600");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606387920000L, aa.getAdaptedAlerts().get(0).getSentTime().getTime());
  }

  @Test
  public void adaptAlerts_DifferentTimeZoneProvided_CorrectTime() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setSent("2021-01-18T02:00:00-0000");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1610935200000L, aa.getAdaptedAlerts().get(0).getSentTime().getTime());
  }

  @Test
  public void adaptAlerts_BeginTimeStringIsProvided_CorrectlyConvertedToDateObject() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setOnset("2020-11-26T04:52:00-0600");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606387920000L, aa.getAdaptedAlerts().get(0).getStartTime().getTime());
  }

  @Test
  public void adaptAlerts_EndTimeStringIsProvided_CorrectlyConvertedToDateObject() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setEnds("2020-11-26T05:52:00-0600");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606391520000L, aa.getAdaptedAlerts().get(0).getEndTime().getTime());
  }

  @Test
  public void adaptAlerts_ExpireTimeWithoutEndStringIsProvided_ExpireTimeIsUsedAsEndTime() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T06:52:00-0600");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606395120000L, aa.getAdaptedAlerts().get(0).getEndTime().getTime());
  }

  @Test
  public void adaptAlerts_ExpiresAndEndTimeProvided_NextUpdateTimeIsExpireTime() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T04:52:00-0600");
    pa.setEnds("2020-11-26T06:52:00-0600");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606387920000L, aa.getAdaptedAlerts().get(0).getExpectedUpdateTime().getTime());
  }

  @Test
  public void adaptAlerts_ExpiresAndEndTimeProvided_EndTimeIsEndTime() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T04:52:00-0600");
    pa.setEnds("2020-11-26T06:52:00-0600");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606395120000L, aa.getAdaptedAlerts().get(0).getEndTime().getTime());
  }

  @Test
  public void adaptAlerts_ExpiresTimeWithNoEndTimeProvided_UpdateTillTimeIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T04:52:00-0500");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getExpectedUpdateTime());
  }

  @Test
  public void adaptAlerts_ExpiresAndEndsTimeAreTheSame_IsLikelyTheLastUpdate() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T04:52:00-0500");
    pa.setEnds("2020-11-26T04:52:00-0500");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertTrue(aa.getAdaptedAlerts().get(0).isLikelyLastUpdate());
  }

  @Test
  public void adaptAlerts_ExpiresAndEndsTimeAreDifferent_IsNotLikelyTheLastUpdate() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T04:52:00-0500");
    pa.setEnds("2020-11-26T05:52:00-0500");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertFalse(aa.getAdaptedAlerts().get(0).isLikelyLastUpdate());
  }

  @Test
  public void adaptAlerts_CancellationAlertIsGiven_LargeHeadlineNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setType("Cancel");
    pa.setDescription("The Winter Weather Advisory has been cancelled and is no longer in effect.");
    pa.setNwsHeadline("WINTER WEATHER ADVISORY IS CANCELLED");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_CancellationAlertIsGiven_SmallHeadlineSet() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setType("Cancel");
    pa.setDescription("The Winter Weather Advisory has been cancelled and is no longer in effect.");
    pa.setNwsHeadline("WINTER WEATHER ADVISORY IS CANCELLED");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("WINTER WEATHER ADVISORY IS CANCELLED", aa.getAdaptedAlerts().get(0).getSmallHeadline());
  }

  @Test
  public void adaptAlerts_MultipleHeadlinesInDescription_LargeHeadlineIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("...Snow Ending but Strong Winds with Blowing Snow Still Possible Today...\n\n...Prolonged, Dangerous Cold and Wind Chills this Week...\n\n.Light snow will continue\n* WHAT...Light snow");
    pa.setNwsHeadline("...WINTER STORM WARNING REMAINS IN EFFECT UNTIL NOON CST TODAY... ...WIND CHILL ADVISORY IN EFFECT FROM 6 PM THIS EVENING TO 6 AM CST TUESDAY... ...WIND CHILL WARNING IN EFFECT FROM 6 AM TUESDAY TO NOON CST THURSDAY...");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("Snow Ending but Strong Winds with Blowing Snow Still Possible Today\n\nProlonged, Dangerous Cold and Wind Chills this Week", aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_MultipleHeadlinesInDescription_SmallHeadlineContainsAllThreeHeadlinesNWSHeadline() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("...Snow Ending but Strong Winds with Blowing Snow Still Possible Today...\n\n...Prolonged, Dangerous Cold and Wind Chills this Week...\n\n.Light snow will continue\n* WHAT...Light snow");
    pa.setNwsHeadline("WINTER STORM WARNING REMAINS IN EFFECT UNTIL NOON CST TODAY... ...WIND CHILL ADVISORY IN EFFECT FROM 6 PM THIS EVENING TO 6 AM CST TUESDAY... ...WIND CHILL WARNING IN EFFECT FROM 6 AM TUESDAY TO NOON CST THURSDAY");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("WINTER STORM WARNING REMAINS IN EFFECT UNTIL NOON CST TODAY\n\nWIND CHILL ADVISORY IN EFFECT FROM 6 PM THIS EVENING TO 6 AM CST TUESDAY\n\nWIND CHILL WARNING IN EFFECT FROM 6 AM TUESDAY TO NOON CST THURSDAY", aa.getAdaptedAlerts().get(0).getSmallHeadline());
  }

  @Test
  public void adaptAlerts_MultipleHeadlinesInDescription_NoneOfTheHeadlinesAreInTheDescription() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("...Snow Ending but Strong Winds with Blowing Snow Still Possible Today...\n\n...Prolonged, Dangerous Cold and Wind Chills this Week...\n\n.Light snow will continue\n* WHAT...Light snow");
    pa.setNwsHeadline("...WINTER STORM WARNING REMAINS IN EFFECT UNTIL NOON CST TODAY... ...WIND CHILL ADVISORY IN EFFECT FROM 6 PM THIS EVENING TO 6 AM CST TUESDAY... ...WIND CHILL WARNING IN EFFECT FROM 6 AM TUESDAY TO NOON CST THURSDAY...");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String headline1 = "Snow Ending but Strong Winds with Blowing Snow Still Possible Today";
    String headline2 = "Prolonged, Dangerous Cold and Wind Chills this Week";
    assertFalse("The first headline is still in the description.\nHeadline:\n" + headline1 + "\nDescription:\n" + aa.getAdaptedAlerts().get(0).getDescription(), aa.getAdaptedAlerts().get(0).getDescription().contains(headline1));
    assertFalse("The second headline is still in the description.\nHeadline:\n" + headline2 + "\nDescription:\n" + aa.getAdaptedAlerts().get(0).getDescription(), aa.getAdaptedAlerts().get(0).getDescription().contains(headline2));
  }

  @Test
  public void adaptAlerts_MultipleHeadlinesInDescription_DescriptionIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("...Snow Ending but Strong Winds with Blowing Snow Still Possible Today...\n\n...Prolonged, Dangerous Cold and Wind Chills this Week...\n\n.Light snow will continue\n\n* WHAT...Light snow");
    pa.setNwsHeadline("...WINTER STORM WARNING REMAINS IN EFFECT UNTIL NOON CST TODAY... ...WIND CHILL ADVISORY IN EFFECT FROM 6 PM THIS EVENING TO 6 AM CST TUESDAY... ...WIND CHILL WARNING IN EFFECT FROM 6 AM TUESDAY TO NOON CST THURSDAY...");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("Light snow will continue\n\n* WHAT...Light snow", aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void adaptAlerts_HeadlineIsExipred_LargeHeadlineIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setNwsHeadline("SMALL CRAFT ADVISORY HAS EXPIRED");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_HeadlineIsExipred_SmallHeadlineIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "SMALL CRAFT ADVISORY HAS EXPIRED";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(headline, aa.getAdaptedAlerts().get(0).getSmallHeadline());
  }

  @Test
  public void adaptAlerts_MultipleNwsHeadlinesAreGiven_SmallHeadlineIsCorrect() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "HEAVY FREEZING SPRAY WARNING TONIGHT AND FRIDAY... ...SMALL CRAFT ADVISORY FRIDAY";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "HEAVY FREEZING SPRAY WARNING TONIGHT AND FRIDAY\n\nSMALL CRAFT ADVISORY FRIDAY";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getSmallHeadline());
  }

  @Test
  public void adaptAlerts_MultipleNwsHeadlinesAreGiven_LargeHeadlineIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "HEAVY FREEZING SPRAY WARNING TONIGHT AND FRIDAY... ...SMALL CRAFT ADVISORY FRIDAY";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineHeadlineForSigWeatherAdvisoryGiven_SmallHeadlineDoesntHaveCommas() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "SIGNIFICANT WEATHER ADVISORY FOR NORTHEASTERN WILL...EASTERN DUPAGE...COOK...WEST CENTRAL PORTER AND LAKE COUNTIES UNTIL 145 AM";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expected = "SIGNIFICANT WEATHER ADVISORY FOR NORTHEASTERN WILL, EASTERN DUPAGE, COOK, WEST CENTRAL PORTER AND LAKE COUNTIES UNTIL 145 AM";
    assertEquals(expected, aa.getAdaptedAlerts().get(0).getSmallHeadline());
  }

  @Test
  public void adaptAlerts_NwsHeadlineHeadlineForSigWeatherAdvisoryGiven_LargeHeadlineIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    String headline = "SIGNIFICANT WEATHER ADVISORY FOR NORTHEASTERN WILL...EASTERN DUPAGE...COOK...WEST CENTRAL PORTER AND LAKE COUNTIES UNTIL 145 AM";
    pa.setNwsHeadline(headline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_EndNotSet_LikelyLastUpdateReturnsTrue() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T06:52:00-05:00");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertTrue(aa.getAdaptedAlerts().get(0).isLikelyLastUpdate());
  }

  @Test
  public void adaptAlerts_OneWinterWeatherAdvisoryIsAdded_HeadlineFromDescriptionIsLargeHeadline() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setName("Winter Weather Advisory");
    pa.setDescription("...Several Inches of Snow and Some Freezing Rain Expected mid to\nlate Tuesday afternoon through Early Wednesday Morning...\n\n* WHAT...Snow, at times heavy in the early evening,\ntransitioning to mixed precipitation including freezing rain.\nTotal snow and sleet accumulations of 1 to 5 inches, highest\nfrom Interstate 88 and north. Ice accumulations of up to one\nto two tenths of an inch are possible away from the Lake\nMichigan shore and just inland.\n\n");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    String expectedParse = "Several Inches of Snow and Some Freezing Rain Expected mid to late Tuesday afternoon through Early Wednesday Morning";
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_TwoSpacesInHeadline_DoesNotCrashDueToEmptyWordSplitInTitleCaseConverter() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setName("Winter Weather Advisory");
    pa.setNwsHeadline("THE FLOOD WARNING  CONTINUES FOR THE FOLLOWING RIVERS IN TEXAS");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    new AlertAdapter(alerts);
  }

  @Test
  public void adaptAlerts_WinterStormWarningIsGiven_IsOfTypeWinterStormWarning() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setName("Winter Storm Warning");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertThat(aa.getAdaptedAlerts().get(0), instanceOf(WinterStormWarning.class));
  }

  @Test
  public void adaptAlerts_WinterWeatherAdvisoryIsGiven_IsOfTypeWinterWeatherAdvisory() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setName("Winter Weather Advisory");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertThat(aa.getAdaptedAlerts().get(0), instanceOf(WinterWeatherAdvisory.class));
  }

  @Test
  public void adaptAlerts_UnknownAlertNameGiven_DefaultAlertReturned() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setName("Random Alert Name String");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertThat(aa.getAdaptedAlerts().get(0), instanceOf(DefaultAlert.class));
  }

  @Test
  public void adaptAlerts_TornadoWarningTextGiven_LargeHeadlineIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setName("Tornado Warning");
    pa.setDescription("The National Weather Service in Sacramento has issued a\n\n* Tornado Warning for...\nEast central Tehama County in northern California...\nNorth central Butte County in northern California...\n\n* Until 400 PM PST.\n\n* At 334 PM PST, a severe thunderstorm capable of producing a tornado\nwas located 7 miles west of Butte Meadows, or 21 miles south of\nMineral, moving northeast at 20 mph.\n\nHAZARD...Tornado.\n\nSOURCE...Radar indicated rotation.\n\nIMPACT...Flying debris will be dangerous to those caught without\nshelter. Mobile homes will be damaged or destroyed.\nDamage to roofs, windows, and vehicles will occur.  Tree\ndamage is likely.\n\n* This dangerous storm will be near...\nButte Meadows around 350 PM PST.\nMill Creek around 355 PM PST.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void adaptAlerts_LineWithPeriodAtEndIsGiven_NewlineIsRemoved() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("* IMPACTS...Winds may blow down trees and power lines. Power\noutages are possible. Loose objects, including holiday\ndecorations, will be blown around. Travel will be difficult.\nBoat moorages should be monitored.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "* IMPACTS...Winds may blow down trees and power lines. Power outages are possible. Loose objects, including holiday decorations, will be blown around. Travel will be difficult. Boat moorages should be monitored.";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void adaptAlerts_FloodWarningTextIsGiven_BulletPointsAreOnSeperateLines() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("* Recent Activity...The maximum river stage in the 24 hours ending\nat 9:00 AM CST Tuesday was 15.8 feet.\n* Forecast...The river is expected to fall below flood stage late\nthis afternoon and continue falling to 12.6 feet Sunday morning.\n* Impact...At 15.0 feet, Minor lowland flooding begins as water\nescapes the main channel and inundates the flood plain.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "* Recent Activity...The maximum river stage in the 24 hours ending at 9:00 AM CST Tuesday was 15.8 feet.\n* Forecast...The river is expected to fall below flood stage late this afternoon and continue falling to 12.6 feet Sunday morning.\n* Impact...At 15.0 feet, Minor lowland flooding begins as water escapes the main channel and inundates the flood plain.";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void adaptAlerts_GaleWarningWithDayByDayForecast_EachDayOnSeperateLine() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("Sea forecasts represent the average of the highest\none-third of the combined windwave and swell height.\n\n.TODAY...SE wind 25 kt increasing to 35 kt in the late morning.\nGusts to 55 kt. Seas building to 7 ft, except 13 ft near ocean\nentrances. Rain.\n.TONIGHT...SE wind 30 kt diminishing to 20 kt late. Seas 5 ft,\nexcept 11 ft near ocean entrances. Rain.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "Sea forecasts represent the average of the highest one-third of the combined windwave and swell height.\n\nTODAY...SE wind 25 kt increasing to 35 kt in the late morning. Gusts to 55 kt. Seas building to 7 ft, except 13 ft near ocean entrances. Rain.\nTONIGHT...SE wind 30 kt diminishing to 20 kt late. Seas 5 ft, except 11 ft near ocean entrances. Rain.";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void adaptAlerts_DenseFogAdvisoryTextProvided_ThereAreNoLineBreaks() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("Surface observations across Deep South Texas and the Rio Grande\nValley indicate that visibilities are improving across the region.\nAs a result, the Dense Fog Advisory has been allowed to expire.\nHowever, motorists are cautioned that isolated areas of dense fog\ncontinue to persist, but this fog should dissipate within the\nhour.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "Surface observations across Deep South Texas and the Rio Grande Valley indicate that visibilities are improving across the region. As a result, the Dense Fog Advisory has been allowed to expire. However, motorists are cautioned that isolated areas of dense fog continue to persist, but this fog should dissipate within the hour.";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void adaptAlerts_ImpactStatementAndHighwaysProvided_LineBreaksOnEachHighway() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("IMPACT...Dangerous life-threatening travel.\n\nThis includes the following highways...\nInterstate 25 in Wyoming between mile markers 1 and 10.\nInterstate 80 in Wyoming between mile markers 345 and 361.\n\nLocations...");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "IMPACT...Dangerous life-threatening travel.\n\nThis includes the following highways...\nInterstate 25 in Wyoming between mile markers 1 and 10.\nInterstate 80 in Wyoming between mile markers 345 and 361.\n\nLocations...";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void linkReferences_IdProvided_ReturnsCorrectID() {
    UnadaptedAlert alert = new UnadaptedAlert();
    alert.setId("testId1");
    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert);
    AlertAdapter aa = new AlertAdapter(unAdaptedAlerts);
    assertEquals("testId1", aa.getAdaptedAlerts().get(0).getNwsId());
  }

  @Test
  public void linkReferences_OneReferenceIsLinked_ReturnsCorrectID() {
    UnadaptedAlert alert = new UnadaptedAlert();
    alert.addReferenceId("testId1");
    UnadaptedAlert reference = new UnadaptedAlert();
    reference.setId("testId1");
    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert);
    unAdaptedAlerts.add(reference);
    AlertAdapter aa = new AlertAdapter(unAdaptedAlerts);
    assertEquals("testId1", aa.getAdaptedAlerts().get(0).getReference(0).getNwsId());
  }

  @Test
  public void linkReferences_TwoReferencesAdded_SortedBySendTimeMostRecentFirst() {
    UnadaptedAlert alert = new UnadaptedAlert();
    UnadaptedAlert reference1 = new UnadaptedAlert();
    UnadaptedAlert reference2 = new UnadaptedAlert();
    reference1.setSent("2021-01-06T14:37:00-0600");
    reference2.setSent("2021-01-06T18:30:00-0600");
    reference1.setId("reference1");
    reference2.setId("reference2");
    alert.addReferenceId("reference1");
    alert.addReferenceId("reference2");
    ArrayList<UnadaptedAlert> unAdaptedAlerts = new ArrayList<>();
    unAdaptedAlerts.add(alert);
    unAdaptedAlerts.add(reference1);
    unAdaptedAlerts.add(reference2);
    AlertAdapter aa = new AlertAdapter(unAdaptedAlerts);
    assertEquals("reference2", aa.getAdaptedAlerts().get(0).getReference(0).getNwsId());
  }

  @Test
  public void adaptAlerts_3NewLinesUsed_ReducedToTwo() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("end of paragraph.\n\n\nNew paragraph");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "end of paragraph.\n\nNew paragraph";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void adaptAlerts_SenderProvided_SenderReturn() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setSender("NWS Chicago IL");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "NWS Chicago IL";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getSender());
  }

  @Test
  public void adaptAlerts_DifferentSenderProvided_SenderReturned() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setSender("NWS Lincoln IL");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "NWS Lincoln IL";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getSender());
  }

  @Test
  public void adaptAlerts_SenderCodeProvided_SenderCodeReturned() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setSenderCode("LOT");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "lot";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getSenderCode());
  }

  @Test
  public void adaptAlerts_DifferentSenderCodeProvided_SenderCodeReturned() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setSenderCode("ILX");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "ilx";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getSenderCode());
  }

  @Test
  public void adaptAlerts_DescriptionIsEmptyString_DescriptionReturnsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setName("Winter Weather Advisory");
    pa.setDescription("");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void adaptAlerts_InstructionIsProvided_NoSingleNewLines() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setInstruction("For your protection move to an interior room on the lowest floor of a\nbuilding.\n\nWind damage with this storm will occur without lightning.\nDo not wait for the sound of thunder before taking cover. SEEK\nSHELTER IMMEDIATELY inside a sturdy structure and stay away from\nwindows.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    String expectedParse = "For your protection move to an interior room on the lowest floor of a building.\n\nWind damage with this storm will occur without lightning. Do not wait for the sound of thunder before taking cover. SEEK SHELTER IMMEDIATELY inside a sturdy structure and stay away from windows.";
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getInstruction());
  }

  @Test
  public void adaptAlerts_PolygonGiven_HasGeometry() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setPolygon(new GeoJSONPolygon());
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertTrue(aa.getAdaptedAlerts().get(0).hasGeometry());
  }

  @Test
  public void adaptAlerts_NoPolygonGiven_DoesNotHaveGeometry() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertFalse(aa.getAdaptedAlerts().get(0).hasGeometry());
  }

  @Test
  public void adaptAlerts_PolygonGivenWithOneCoordinate_HasOneCoordinate() {
    UnadaptedAlert ua = new UnadaptedAlert();
    GeoJSONPolygon geoJSONPolygon = new GeoJSONPolygon();
    geoJSONPolygon.addCoordinate(new GCSCoordinate(1.0, 0.0));
    ua.setPolygon(geoJSONPolygon);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1, aa.getAdaptedAlerts().get(0).getPolygon(0).getCoordinateCount());
  }

  @Test
  public void adaptAlerts_PolygonGivenWithTwoCoordinates_HasTwoCoordinates() {
    UnadaptedAlert ua = new UnadaptedAlert();
    GeoJSONPolygon geoJSONPolygon = new GeoJSONPolygon();
    geoJSONPolygon.addCoordinate(new GCSCoordinate(1.0, 0.0));
    geoJSONPolygon.addCoordinate(new GCSCoordinate(1.0, 0.0));
    ua.setPolygon(geoJSONPolygon);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(2, aa.getAdaptedAlerts().get(0).getPolygon(0).getCoordinateCount());
  }

  @Test
  public void adaptAlerts_PolygonWithCoordinateGiven_XCorrect() {
    UnadaptedAlert ua = new UnadaptedAlert();
    GeoJSONPolygon geoJSONPolygon = new GeoJSONPolygon();
    geoJSONPolygon.addCoordinate(new GCSCoordinate(0.0, 1.0));
    ua.setPolygon(geoJSONPolygon);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(111319.49, aa.getAdaptedAlerts().get(0).getPolygon(0).getCoordinate(0).getX(), 0.01);
  }

  @Test
  public void getZoneCount_NoZoneLinksGiven_CountIs0() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(0, aa.getAdaptedAlerts().get(0).getZoneLinkCount());
  }

  @Test
  public void getZoneCount_OneZoneLinkGiven_CountIs1() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.addZoneLink("testLink");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1, aa.getAdaptedAlerts().get(0).getZoneLinkCount());
  }

  @Test
  public void getZoneCount_TwoZoneLinksGiven_CountIs2() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.addZoneLink("testLink");
    ua.addZoneLink("testLink");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(2, aa.getAdaptedAlerts().get(0).getZoneLinkCount());
  }

  @Test
  public void getZone_ZoneLinkGiven_CorrectReturn() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.addZoneLink("testLink");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("testLink", aa.getAdaptedAlerts().get(0).getZone(0));
  }

  @Test
  public void getZone_DifferentZoneLinkGiven_CorrectReturn() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.addZoneLink("testLink2");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("testLink2", aa.getAdaptedAlerts().get(0).getZone(0));
  }

  @Test
  public void getZone_TwoZoneLinksGiven_FirstOneIsCorrect() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.addZoneLink("testLink1");
    ua.addZoneLink("testLink2");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("testLink1", aa.getAdaptedAlerts().get(0).getZone(0));
  }

  @Test
  public void getLargeHeadline_NWSHeadlineWithLakeEffectProvided_IsLargeHeadline() {
    UnadaptedAlert ua = new UnadaptedAlert();
    String nwsHeadline = "Moderate to Possibly Heavy Lake Effect Snow on Wednesday";
    ua.setNwsHeadline(nwsHeadline);
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(nwsHeadline, aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void LikelyCertainty() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setCertainty("Likely");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Certainty.LIKELY, aa.getAdaptedAlerts().get(0).getCertainty());
  }

  @Test
  public void UnknownCertainty() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setCertainty("Unknown");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Certainty.UNKNOWN, aa.getAdaptedAlerts().get(0).getCertainty());
  }

  @Test
  public void UnlikelyCertainty() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setCertainty("Unlikely");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Certainty.UNLIKELY, aa.getAdaptedAlerts().get(0).getCertainty());
  }

  @Test
  public void PossibleCertainty() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setCertainty("Possible");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Certainty.POSSIBLE, aa.getAdaptedAlerts().get(0).getCertainty());
  }

  @Test
  public void ObservedCertainty() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setCertainty("Observed");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Certainty.OBSERVED, aa.getAdaptedAlerts().get(0).getCertainty());
  }

  @Test
  public void UnknownUrgency() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setCertainty("Unknown");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Urgency.UNKNOWN, aa.getAdaptedAlerts().get(0).getUrgency());
  }

  @Test
  public void PastUrgency() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setUrgency("Past");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Urgency.PAST, aa.getAdaptedAlerts().get(0).getUrgency());
  }

  @Test
  public void FutureUrgency() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setUrgency("Future");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Urgency.FUTURE, aa.getAdaptedAlerts().get(0).getUrgency());
  }

  @Test
  public void ExpectedUrgency() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setUrgency("Expected");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Urgency.EXPECTED, aa.getAdaptedAlerts().get(0).getUrgency());
  }

  @Test
  public void ImmediateUrgency() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setUrgency("Immediate");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Urgency.IMMEDIATE, aa.getAdaptedAlerts().get(0).getUrgency());
  }

  @Test
  public void invalidTextIsUnknown() {
    UnadaptedAlert ua = new UnadaptedAlert();
    ua.setCertainty("unknown test");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(ua);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(Alert.Urgency.UNKNOWN, aa.getAdaptedAlerts().get(0).getUrgency());
  }

  @Test
  public void instructionIsEmptyString_getInstructionReturnsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setInstruction("");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getInstruction());
  }

  @Test
  public void descriptionHasCodeAtBeginning_Removed() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("CCA\n\nThis is a test alert");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("This is a test alert", aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void descriptionHasDifferentCodeAtBeginning_Removed() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("CCB\n\nThis is a test alert");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("This is a test alert", aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void descriptionHasCodeNotAtBeginning_NotRemoved() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("This is a test alert\n\nCCB\n\nMore");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("This is a test alert\n\nCCB\n\nMore", aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void columnLocations_NotInHeadline() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("At 225 AM CDT, Doppler radar was tracking strong thunderstorms along\na line extending from near Danville to 8 miles southeast of Fourche\nValley to 6 miles east of Mount Ida to 7 miles south of Norman.\nMovement was east at 40 mph.\n\nHalf inch hail and winds in excess of 40 mph will be possible with\nthese storms.\n\nLocations impacted include...\nHot Springs...                    Russellville...\nHot Springs Village...            Morrilton...\nDanville...                       Perryville...\nMount Ida...                      Dardanelle...\nAtkins...                         Pottsville...\nOla...                            Oppelo...\nMountain Pine...                  Norman...\nPerry...                          Adona...\nSequoya Park...                   Meyers...\nJessieville...                    Petit Jean River WMA...\n\nA Tornado Watch remains in effect until 800 AM CDT for central,\nwestern and southwestern Arkansas.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void differentColumnLocations_NotInHeadline() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("At 243 AM CDT, Doppler radar and automated rain gauges indicated\nthunderstorms producing heavy rain across the warned area. Between 2\nand 5 inches of rain have fallen. Flash flooding is ongoing or\nexpected to begin shortly.\n\nHAZARD...Flash flooding caused by thunderstorms.\n\nSOURCE...Doppler radar and automated gauges.\n\nIMPACT...Flooding of small creeks and streams, urban areas,\nhighways, streets and underpasses as well as other\ndrainage and low lying areas.\n\nSome locations that will experience flash flooding include...\nFort Smith...                      Van Buren...\nMcalester...                       Sallisaw...\nPoteau...                          Ozark...\nWilburton...                       Stigler...\nCharleston...                      Greenwood...\nAlma...                            Barling...\nPocola...                          Muldrow...\nHeavener...                        Roland...\nLavaca...                          Spiro...\nHartshorne...                      Krebs...\n\nAdditional rainfall amounts of 1 to 3 inches are possible in the\nwarned area, mainly across parts of Sebastian and Franklin counties.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getLargeHeadline());
  }

  @Test
  public void columnLocations_ChangedToCommas() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("Some locations that will experience flash flooding include...\nFort Smith...                      Van Buren...\nMcalester...                       Sallisaw...\nPoteau...                          Ozark...\nWilburton...                       Stigler...\nCharleston...                      Greenwood...\nAlma...                            Barling...\nPocola...                          Muldrow...\nHeavener...                        Roland...\nLavaca...                          Spiro...\nHartshorne...                      Krebs...\n\nAdditional rainfall");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("Some locations that will experience flash flooding include...\nFort Smith, Van Buren, Mcalester, Sallisaw, Poteau, Ozark, Wilburton, Stigler, Charleston, Greenwood, Alma, Barling, Pocola, Muldrow, Heavener, Roland, Lavaca, Spiro, Hartshorne, Krebs.\n\nAdditional rainfall", aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void replacedByNonExistentAlert_DiscontinuedAt() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setReplacedBy("https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.f2b344bad8e99c947eb0cd944633f7c118757e70.001.1");
    pa.setReplacedAt("2021-04-29T19:10:00-0500");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1619741400000L, aa.getAdaptedAlerts().get(0).getDiscontinuedAt().getTime());
  }

  @Test
  public void replacedByNonExistentAlert_TimeDifferent_DiscontinuedAt() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setReplacedBy("https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.f2b344bad8e99c947eb0cd944633f7c118757e70.001.1");
    pa.setReplacedAt("2021-04-29T18:10:00-0500");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1619737800000L, aa.getAdaptedAlerts().get(0).getDiscontinuedAt().getTime());
  }

  @Test
  public void replacedByAlertInList_DiscontinuedNull() {
    UnadaptedAlert update = new UnadaptedAlert();
    update.setId("https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.f2b344bad8e99c947eb0cd944633f7c118757e70.001.1");
    UnadaptedAlert post = new UnadaptedAlert();
    post.setReplacedBy("https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.f2b344bad8e99c947eb0cd944633f7c118757e70.001.1");
    post.setReplacedAt("2021-04-29T18:10:00-0500");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(post);
    alerts.add(update);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getDiscontinuedAt());
  }

  @Test
  public void replacedByAlertInList_DDiscontinuedNull() {
    UnadaptedAlert cancel = new UnadaptedAlert();
    cancel.setId("https://api.weather.gov/alerts/urn:oid:3.49.0.1.840.0.f2b344bad8e99c947eb0cd944633f7c118757e70.001.1");
    UnadaptedAlert update = new UnadaptedAlert();
    update.setId("https://api.weather.gov/alerts/urn:oid:2.49.0.1.840.0.f2b344bad8e99c947eb0cd944633f7c118757e70.001.1");
    UnadaptedAlert post = new UnadaptedAlert();
    post.setReplacedBy("https://api.weather.gov/alerts/urn:oid:2.49.0.3.840.0.f2b344bad8e99c947eb0cd944633f7c118757e70.001.1");
    post.setReplacedAt("2021-04-29T18:10:00-0500");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(cancel);
    alerts.add(update);
    alerts.add(post);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1619737800000L, aa.getAdaptedAlerts().get(2).getDiscontinuedAt().getTime());
  }

  @Test
  public void columnLocations_DoesNotEndWithNewLines_ChangedToCommas() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("Locations impacted include...\nMountain Home...                  Mountain View...\nBull Shoals...                    Melbourne...\nGassville...                      Calico Rock...\nFlippin...                        Lakeview in Baxter County...\nOxford...                         Norfork...\nSalesville...                     Pineville...\nBriarcliff...                     Arkawana...\nBull Shoals State Park...         Lone Star...\nWideman...                        Herron...\nColfax...                         Gid...");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("Locations impacted include...\nMountain Home, Mountain View, Bull Shoals, Melbourne, Gassville, Calico Rock, Flippin, Lakeview in Baxter County, Oxford, Norfork, Salesville, Pineville, Briarcliff, Arkawana, Bull Shoals State Park, Lone Star, Wideman, Herron, Colfax, Gid.", aa.getAdaptedAlerts().get(0).getDescription());
  }

  @Test
  public void columnLocationsProvided_OtherAreasOfDescriptionNotTouched() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("At 1226 AM CDT, severe thunderstorms were located along a line\nextending from near Gassville to near Culp to near Blanchard Springs\nCampground to Mountain View, moving northeast at 40 mph.\n\nHAZARD...60 mph wind gusts and quarter size hail.\n\nSOURCE...Radar indicated.\n\nIMPACT...Hail damage to vehicles is expected. Expect wind damage to\nroofs, siding, and trees.\n\nLocations impacted include...\nMountain Home...                  Mountain View...\nBull Shoals...                    Melbourne...\nGassville...                      Calico Rock...\nFlippin...                        Lakeview in Baxter County...\nOxford...                         Norfork...\nSalesville...                     Pineville...\nBriarcliff...                     Arkawana...\nBull Shoals State Park...         Lone Star...\nWideman...                        Herron...\nColfax...                         Gid...");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("At 1226 AM CDT, severe thunderstorms were located along a line extending from near Gassville to near Culp to near Blanchard Springs Campground to Mountain View, moving northeast at 40 mph.\n\nHAZARD...60 mph wind gusts and quarter size hail.\n\nSOURCE...Radar indicated.\n\nIMPACT...Hail damage to vehicles is expected. Expect wind damage to roofs, siding, and trees.\n\nLocations impacted include...\nMountain Home, Mountain View, Bull Shoals, Melbourne, Gassville, Calico Rock, Flippin, Lakeview in Baxter County, Oxford, Norfork, Salesville, Pineville, Briarcliff, Arkawana, Bull Shoals State Park, Lone Star, Wideman, Herron, Colfax, Gid.", aa.getAdaptedAlerts().get(0).getDescription());
  }


  @Test
  public void instructionEllipsesConvertedToCommas() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setInstruction("People outdoors should seek shelter immediately. If you can hear\nthunder...you are close enough to be struck by lightning. Motorists\nshould slow down and be prepared for possible loss of control due to\nhydroplaning.");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("People outdoors should seek shelter immediately. If you can hear thunder, you are close enough to be struck by lightning. Motorists should slow down and be prepared for possible loss of control due to hydroplaning.", aa.getAdaptedAlerts().get(0).getInstruction());
  }

  @Test
  public void removeCoordinatesInInstruction() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setInstruction("People outdoors should seek shelter immediately. If you can hear\nthunder...you are close enough to be struck by lightning. Motorists\nshould slow down and be prepared for possible loss of control due to\nhydroplaning.\n\nLAT...LON 3214 8220 3207 8221 3211 8262 3213 8263\n3215 8261 3217 8262 3232 8258 3234 8224\n3228 8223 3224 8221 3223 8221 3218 8219\n3217 8218\nTIME...MOT...LOC 0516Z 272DEG 27KT 3218 8249\n\nHAIL...0.25IN\nWIND...50MPH");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals("People outdoors should seek shelter immediately. If you can hear thunder, you are close enough to be struck by lightning. Motorists should slow down and be prepared for possible loss of control due to hydroplaning.", aa.getAdaptedAlerts().get(0).getInstruction());
  }

  @Test
  public void motionVectorHeading() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setMotionDescription("2021-05-22T04:24:00-00:00...storm...220DEG...49KT...39.89,-102.05");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(220, aa.getAdaptedAlerts().get(0).getMotionVector().getHeading());
  }

  @Test
  public void motionVectorDifferentHeading() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setMotionDescription("2021-05-22T04:24:00-00:00...storm...221DEG...49KT...39.89,-102.05");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(221, aa.getAdaptedAlerts().get(0).getMotionVector().getHeading());
  }

  @Test
  public void motionVectorSpeed() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setMotionDescription("2021-05-22T04:24:00-00:00...storm...221DEG...49KT...39.89,-102.05");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(49, aa.getAdaptedAlerts().get(0).getMotionVector().getVelocity());
  }

  @Test
  public void differentMotionVectorSpeed() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setMotionDescription("2021-05-22T04:24:00-00:00...storm...221DEG...50KT...39.89,-102.05");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(50, aa.getAdaptedAlerts().get(0).getMotionVector().getVelocity());
  }

  @Test
  public void hurricaneLocalStatementLooksPretty() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setDescription("This product covers portions of southwest Alabama...northwest Florida...south\ncentral Alabama...and inland southeast Mississippi.\n\n**POTENTIAL TROPICAL CYCLONE THREE NOW BRINGING HEAVY RAINFALL AND\nFLASH FLOODING ALONG WITH GUSTY WINDS...ISOLATED TORNADOES...AND\nMINOR COASTAL FLOODING**\n\nNEW INFORMATION\n---------------\n\n* CHANGES TO WATCHES AND WARNINGS:\n- None\n\n* CURRENT WATCHES AND WARNINGS:\n- A Tropical Storm Warning is in effect for Baldwin Central,\nBaldwin Coastal, Escambia Coastal, Mobile Central, Mobile\nCoastal, Okaloosa Coastal, and Santa Rosa Coastal\n\n* STORM INFORMATION:\n- About 210 miles southwest of Mobile AL or about 250 miles\nwest-southwest of Pensacola FL\n- 28.9N 90.9W\n- Storm Intensity 45 mph\n- Movement North or 10 degrees at 13 mph");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    String expectedParse = "This product covers portions of southwest Alabama...northwest Florida...south central Alabama...and inland southeast Mississippi.\n\n**POTENTIAL TROPICAL CYCLONE THREE NOW BRINGING HEAVY RAINFALL AND FLASH FLOODING ALONG WITH GUSTY WINDS...ISOLATED TORNADOES...AND MINOR COASTAL FLOODING**\n\nNEW INFORMATION:\n\n* CHANGES TO WATCHES AND WARNINGS:\n- None\n\n* CURRENT WATCHES AND WARNINGS:\n- A Tropical Storm Warning is in effect for Baldwin Central, Baldwin Coastal, Escambia Coastal, Mobile Central, Mobile Coastal, Okaloosa Coastal, and Santa Rosa Coastal\n\n* STORM INFORMATION:\n- About 210 miles southwest of Mobile AL or about 250 miles west-southwest of Pensacola FL\n- 28.9N 90.9W\n- Storm Intensity 45 mph\n- Movement North or 10 degrees at 13 mph";
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(expectedParse, aa.getAdaptedAlerts().get(0).getDescription());
  }
}