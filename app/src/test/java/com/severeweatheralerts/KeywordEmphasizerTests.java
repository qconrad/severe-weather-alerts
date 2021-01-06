package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeywordEmphasizerTests {
  final String keywordSurroundLeft =  "<font color='#FFFFFF'><b>";
  final String keywordSurroundRight =  "</b></font>";
  @Test
  public void emphasize_DescriptionIsProvided_WhatIsEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* WHAT...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* WHAT..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvided_WhereIsEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* WHERE...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* WHERE..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvided_WhatIsEmphasizedButOtherTextIsNot() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* WHAT...Light snow expected";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* WHAT..." + keywordSurroundRight + "Light snow expected";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvided_NewLinesAreHtmlLineBreaks() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* WHAT...Light snow expected\n\n* WHERE...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* WHAT..." + keywordSurroundRight + "Light snow expected<br><br>" + keywordSurroundLeft + "* WHERE..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvided_WhatAndWhereIsEmphasizedAndOtherTextIsNot() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* WHAT...Light snow expected\n\n* WHERE...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* WHAT..." + keywordSurroundRight + "Light snow expected<br><br>" + keywordSurroundLeft + "* WHERE..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithColons_KeywordsAreEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* Affected area: California";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* Affected area:" + keywordSurroundRight + " California";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithHazardKeyword_Emphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "HAZARD...Tornado";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "HAZARD..." + keywordSurroundRight + "Tornado";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithSourceKeyword_Emphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "SOURCE...Radar indicated.";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "SOURCE..." + keywordSurroundRight + "Radar indicated.";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithImpactKeyword_Emphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "IMPACT...Expect damage to roofs siding and trees.";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "IMPACT..." + keywordSurroundRight + "Expect damage to roofs siding and trees.";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithTime_NotEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "It is currently 8:00";
    String output = kw.emphasize(input);
    String expectedParse = "It is currently 8:00";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithMultipleMatches_ShorterOneIsEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* Recent Activity...The maximum river stage ending at 6:30 PM";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* Recent Activity..." + keywordSurroundRight + "The maximum river stage ending at 6:30 PM";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithTimeAndStar_NotEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* At 6:30 PM";
    String output = kw.emphasize(input);
    String expectedParse = "* At 6:30 PM";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_ExcessivelyLongMatchIsProvided_NotEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* Moderate flooding is occurring and moderate flooding is forecast.\n* Recent Activity...The max";
    String output = kw.emphasize(input);
    String expectedParse = "* Moderate flooding is occurring and moderate flooding is forecast.<br>" + keywordSurroundLeft + "* Recent Activity..." + keywordSurroundRight + "The max";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvided_NotEmphasizedAcrossNewLine() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* Minor flooding is forecast.\n* Forecast...";
    String output = kw.emphasize(input);
    String expectedParse = "* Minor flooding is forecast.<br>" + keywordSurroundLeft + "* Forecast..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithoutStars_EmphasizedCategory() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "Locations impacted include...\nYour house";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft  + "Locations impacted include..." + keywordSurroundRight + "<br>Your house";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_ThreeLetterDaysOfTheWeekProvided_Emphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "WED...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft  + "WED..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_KeywordProvidedNextToShortMatchAcrossNewline_DaysOfWeekEmphasizedAndPreviousLineIsNot() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "Heavy freezing spray.\nTHU...N winds 20 kt. Seas 6 ft. Heavy freezing spray.";
    String output = kw.emphasize(input);
    String expectedParse = "Heavy freezing spray.<br>" + keywordSurroundLeft  + "THU..." + keywordSurroundRight + "N winds 20 kt. Seas 6 ft. Heavy freezing spray.";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_IncludingTheFollowingHighwaysProvided_Emphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "This includes the following highways...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft  + "This includes the following highways..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_TimeInTitle_Emphasiszed() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "Locations impacted through 11pm include...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft  + "Locations impacted through 11pm include..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_UrlProvided_NotEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* View in detail at https://weather.gov";
    String output = kw.emphasize(input);
    String expectedParse = "* View in detail at https://weather.gov";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_KeywordProvided_Emphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* Details: details here";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* Details:" + keywordSurroundRight + " details here";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_InsecureUrlProvided_NotEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* View in detail at http://weather.gov";
    String output = kw.emphasize(input);
    String expectedParse = "* View in detail at http://weather.gov";
    assertEquals(expectedParse, output);
  }
}
