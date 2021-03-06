package com.severeweatheralerts.TextUtils;

import java.util.ArrayList;

import static com.severeweatheralerts.TextUtils.RegExMatcher.multiLineMatch;

public class KeywordEmphasizer {
  public KeywordEmphasizer() {}
  public String emphasize(String input) {
    String output = input;
    for (String keyword : getKeywords(input))
      if (validKeyword(keyword)) output = emphasizeKeyword(output, removeNewLines(keyword));
    return replaceNewLinesWithHtmlLineBreaks(output);
  }

  protected boolean validKeyword(String keyword) {
    return !hasURL(keyword) && !isLocation(keyword);
  }

  private boolean isLocation(String text) {
    return text.matches(".* in .*\\.\\.\\.");
  }

  private boolean hasURL(String text) {
    return text.contains("http");
  }

  protected String removeNewLines(String text) {
    return text.replace("\n", "");
  }

  protected String replaceNewLinesWithHtmlLineBreaks(String text) {
    return text.replace("\n", "<br>");
  }

  protected String emphasizeKeyword(String text, String keyword) {
    String htmlSurroundLeft = "<font color='#FFFFFF'><b>";
    String htmlSurroundRight = "</b></font>";
    return text.replace(keyword, htmlSurroundLeft + keyword + htmlSurroundRight);
  }

  protected ArrayList<String> getKeywords(String input) {
    return multiLineMatch("^.{3,60}?(\\D:|\\.\\.\\.)", input);
  }
}
