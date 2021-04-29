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
    return beautify(makeColumnLocationsCommas(removeCodes(description)));
  }

  /*
  Certain offices will use this format
  Locations impacted include...
  This location...            That location...
  Another location...         Another location...

  While pretty and all, it does not wrap well. Additionally, it throws off the keyword highlighter
  because it is indistinguishable from a section headline. This helper makes this format appear like the rest
  i.e.
  Locations impacted include...
  This location, That location, Another location, Another location.
   */
  private String makeColumnLocationsCommas(String description) {
    ArrayList<String> columnHeadlines = match("\\.\\.\\.[^\n](\n|.)*?\\.\\.\\.\\n\\n", description);
    if (columnHeadlines.size() > 0 && columnHeadlines.get(0).contains("   ")) {
      String fixed = columnHeadlines.get(0).replaceAll("\\.\\.\\.\n\n", ".\n\n");
      fixed = fixed.replaceAll("\\.\\.\\.(\n| )*", ", ");
      return description.replace(columnHeadlines.get(0), fixed);
    }
    return description;
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
