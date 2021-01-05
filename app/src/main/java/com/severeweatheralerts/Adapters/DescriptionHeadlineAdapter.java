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
    cleanUpHeadLines(headlineList);
    return combineAllHeadlines(headlineList);
  }

  private void cleanUpHeadLines(ArrayList<String> headlineList) {
    for (int i = 0; i < headlineList.size(); i++) {
      headlineList.set(i, toTitleCase(removePeriodsAndNewLinesFromText(headlineList.get(i))));
    }
  }

  private String removePeriodsAndNewLinesFromText(String text) {
    return text.trim().replace("\n", " ").replaceAll("\\.", "");
  }

  private boolean noMatches(ArrayList<String> headlineDescMatches) {
    return headlineDescMatches.isEmpty();
  }

  private ArrayList<String> matchHeadlines(String text) {
    return match("\\.\\.\\.[^\n](\n|.)*?\\.\\.\\.\\n\\n", text);
  }

  private String combineAllHeadlines(ArrayList<String> headlineList) {
    StringBuilder largeHeadline = new StringBuilder();
    for (int i = 0; i < headlineList.size(); i++)
      largeHeadline.append(headlineList.get(i)).append("\n\n");
    return largeHeadline.toString().trim();
  }
}
