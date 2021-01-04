package com.severeweatheralerts;

import java.util.ArrayList;

import static com.severeweatheralerts.TextUtils.RegExMatcher.match;

public class KeywordEmphasizer {
  public String emphasize(String input) {
    String output = input;
    ArrayList<String> keywords = getKeywords(input);
    for (int i = 0; i < keywords.size(); i++)
      output = highlightKeyword(output, keywords.get(i));
    return output;
  }

  protected String highlightKeyword(String text, String keyword) {
    String htmlSurroundLeft = "<font color='#FFFFFF'><b>";
    String htmlSurroundRight = "</b><font>";
    return text.replace(keyword, htmlSurroundLeft + keyword + htmlSurroundRight);
  }

  protected ArrayList<String> getKeywords(String input) {
    return match("(\\* .*(:|\\.\\.\\.))|(HAZARD\\.\\.\\.)|(SOURCE\\.\\.\\.)|(IMPACT\\.\\.\\.)", input);
  }
}
