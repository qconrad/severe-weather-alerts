package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeywordHighlighterTests {
  final String keywordSurroundLeft =  "<font color='#FFFFFF'><b>";
  final String keywordSurroundRight =  "</b><font>";
  @Test
  public void highlight_DescriptionIsProvided_WhatIsEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* WHAT...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* WHAT..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void highlight_DescriptionIsProvided_WhereIsEmphasized() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* WHERE...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* WHERE..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }

  @Test
  public void highlight_DescriptionIsProvided_WhatIsEmphasizedButOtherTextIsNot() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* WHAT...Light snow expected";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* WHAT..." + keywordSurroundRight + "Light snow expected";
    assertEquals(expectedParse, output);
  }

  @Test
  public void highlight_DescriptionIsProvided_WhatAndWhereIsEmphasizedAndOtherTextIsNot() {
    KeywordEmphasizer kw = new KeywordEmphasizer();
    String input = "* WHAT...Light snow expected\n\n* WHERE...";
    String output = kw.emphasize(input);
    String expectedParse = keywordSurroundLeft + "* WHAT..." + keywordSurroundRight + "Light snow expected\n\n" + keywordSurroundLeft + "* WHERE..." + keywordSurroundRight;
    assertEquals(expectedParse, output);
  }
}
