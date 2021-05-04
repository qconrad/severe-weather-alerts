package com.severeweatheralerts.Adapters;

import java.util.ArrayList;

import static com.severeweatheralerts.TextUtils.RegExMatcher.match;
import static com.severeweatheralerts.TextUtils.TextBeautifier.beautify;

public class DescriptionAdapter {
  private final String description;

  public DescriptionAdapter(String description) {
    this.description = description;
  }

  public String adaptDescription() {
    if (nullOrEmpty(description)) return null;
    return beautify(new ColumnLocationAdapter(removeCodes(description)).getListStyleLocations());
  }

  private String removeCodes(String description) {
    ArrayList<String> match = match("^[A-Z]{3}\n\n", description);
    if (match.size() > 0) return description.replace(match.get(0), "");
    return description;
  }

  private boolean nullOrEmpty(String text) {
    return text == null || text.equals("");
  }
}
