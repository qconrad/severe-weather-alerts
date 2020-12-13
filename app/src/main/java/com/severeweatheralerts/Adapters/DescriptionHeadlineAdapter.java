package com.severeweatheralerts.Adapters;

import java.util.ArrayList;

import static com.severeweatheralerts.TextUtils.RegExMatcher.match;
import static com.severeweatheralerts.TextUtils.TitleCaseConvertor.toTitleCase;

public class DescriptionHeadlineAdapter {
  private String description;

  public DescriptionHeadlineAdapter(String description) {
    this.description = description;
  }

  public String adaptDescriptionHeadline() {
    ArrayList<String> headlineDescMatches = matchHeadlines(description);
    if (noMatches(headlineDescMatches)) return null;
    return combineAndPrettyUpDescHeadlines(headlineDescMatches);
  }

  private String combineAndPrettyUpDescHeadlines(ArrayList<String> headlineList) {
    return toTitleCase(removePeriodsAndTrim(combineAllHeadlines(headlineList)));
  }

  private boolean noMatches(ArrayList<String> headlineDescMatches) {
    return headlineDescMatches.isEmpty();
  }

  private ArrayList<String> matchHeadlines(String text) {
    return match("\\.\\.\\..*\\.\\.\\.\\n", text);
  }

  private String combineAllHeadlines(ArrayList<String> headlineList) {
    StringBuilder largeHeadline = new StringBuilder();
    for (int i = 0; i < headlineList.size(); i++)
      largeHeadline.append(headlineList.get(i)).append("\n");
    return largeHeadline.toString();
  }

  private String removePeriodsAndTrim(String text) {
    return text.replaceAll("\\.", "").trim();
  }
}
