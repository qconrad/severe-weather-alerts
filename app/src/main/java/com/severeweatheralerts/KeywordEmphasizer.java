package com.severeweatheralerts;

import java.util.ArrayList;

import static com.severeweatheralerts.TextUtils.RegExMatcher.multiLineMatch;

public class KeywordEmphasizer {
  public KeywordEmphasizer() {}
  public String emphasize(String input) {
    String output = input;
    ArrayList<String> keywords = getKeywords(input);
    for (int i = 0; i < keywords.size(); i++)
      output = emphasizeKeyword(output, removeNewLines(keywords.get(i)));
    return replaceNewLinesWithHtmlLineBreaks(output);
  }

  protected String removeNewLines(String text) {
    return text.replace("\n", "");
  }

  protected String replaceNewLinesWithHtmlLineBreaks(String text) {
    return text.replace("\n", "<br>");
  }

  protected String emphasizeKeyword(String text, String keyword) {
    if (keyword.contains("http")) return text;
    String htmlSurroundLeft = "<font color='#FFFFFF'><b>";
    String htmlSurroundRight = "</b></font>";
    return text.replace(keyword, htmlSurroundLeft + keyword + htmlSurroundRight);
  }

  protected ArrayList<String> getKeywords(String input) {
    return multiLineMatch("^.{3,40}?(\\D:|\\.\\.\\.)", input);
  }
}
