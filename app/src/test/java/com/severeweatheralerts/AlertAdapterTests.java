package com.severeweatheralerts;

import com.severeweatheralerts.Adapters.AlertAdapter;

import org.junit.Test;

import java.util.ArrayList;

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
    pa.setSent("2020-11-26T04:52:00-05:00");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606387920000L, aa.getAdaptedAlerts().get(0).getSentTime().getTime());
  }

  @Test
  public void adaptAlerts_BeginTimeStringIsProvided_CorrectlyConvertedToDateObject() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setOnset("2020-11-26T04:52:00-05:00");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606387920000L, aa.getAdaptedAlerts().get(0).getStartTime().getTime());
  }

  @Test
  public void adaptAlerts_EndTimeStringIsProvided_CorrectlyConvertedToDateObject() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setEnds("2020-11-26T05:52:00-05:00");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606391520000L, aa.getAdaptedAlerts().get(0).getEndTime().getTime());
  }

  @Test
  public void adaptAlerts_ExpireTimeWithoutEndStringIsProvided_ExpireTimeIsUsedAsEndTime() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T06:52:00-05:00");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606395120000L, aa.getAdaptedAlerts().get(0).getEndTime().getTime());
  }
  // TODO: check and make sure till time isn't set

  @Test
  public void adaptAlerts_ExpiresAndEndTimeProvided_NextUpdateTimeIsExpireTime() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T04:52:00-05:00");
    pa.setEnds("2020-11-26T06:52:00-05:00");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606387920000L, aa.getAdaptedAlerts().get(0).getExpectedUpdateTime().getTime());
  }

  @Test
  public void adaptAlerts_ExpiresAndEndTimeProvided_EndTimeIsEndTime() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T04:52:00-05:00");
    pa.setEnds("2020-11-26T06:52:00-05:00");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertEquals(1606395120000L, aa.getAdaptedAlerts().get(0).getEndTime().getTime());
  }

  @Test
  public void adaptAlerts_ExpiresTimeWithNoEndTimeProvided_UpdateTillTimeIsNull() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T04:52:00-05:00");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertNull(aa.getAdaptedAlerts().get(0).getExpectedUpdateTime());
  }

  @Test
  public void adaptAlerts_ExpiresAndEndsTimeAreTheSame_IsLikelyTheLastUpdate() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T04:52:00-05:00");
    pa.setEnds("2020-11-26T04:52:00-05:00");
    ArrayList<UnadaptedAlert> alerts = new ArrayList<>();
    alerts.add(pa);
    AlertAdapter aa = new AlertAdapter(alerts);
    assertTrue(aa.getAdaptedAlerts().get(0).isLikelyLastUpdate());
  }

  @Test
  public void adaptAlerts_ExpiresAndEndsTimeAreDifferent_IsNotLikelyTheLastUpdate() {
    UnadaptedAlert pa = new UnadaptedAlert();
    pa.setExpires("2020-11-26T04:52:00-05:00");
    pa.setEnds("2020-11-26T05:52:00-05:00");
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
}
