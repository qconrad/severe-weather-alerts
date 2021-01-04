package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeywordHighlighterTests {
  final String keywordSurroundLeft =  "<font color='#FFFFFF'><b>";
  final String keywordSurroundRight =  "</b><font>";
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
  public void emphasize_DescriptionIsProvided_WhatAndWhereIsEmphasizedAndOtherTextIsNot() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* WHAT...Light snow expected\n\n* WHERE...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* WHAT..." + keywordSurroundRight + "Light snow expected\n\n" + keywordSurroundLeft + "* WHERE..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithColons_KeywordsAreEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* Affected area: California\n\n";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* Affected area:" + keywordSurroundRight + " California\n\n";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithHazardKeyword_Emphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "HAZARD...Tornado\n\n";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "HAZARD..." + keywordSurroundRight + "Tornado\n\n";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithSourceKeyword_Emphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "SOURCE...Radar indicated.\n\n";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "SOURCE..." + keywordSurroundRight + "Radar indicated.\n\n";
    assertEquals(expectedParse, output);
  }

  @Test
  public void emphasize_DescriptionIsProvidedWithImpactKeyword_Emphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "IMPACT...Expect damage to roofs siding and trees.\n\n";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "IMPACT..." + keywordSurroundRight + "Expect damage to roofs siding and trees.\n\n";
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
}
