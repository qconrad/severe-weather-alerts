package com.severeweatheralerts.Adapters;

import java.util.ArrayList;

import static com.severeweatheralerts.TextUtils.RegExMatcher.match;

/*
Certain offices will use this format
Locations impacted include...
This location...            That location...
Another location...         Another location...

While pretty and all, it does not wrap well. Additionally, it throws off the keyword highlighter
because it is indistinguishable from a section headline. This class makes this format appear like the rest
i.e.
Locations impacted include...
This location, That location, Another location, Another location.
 */
public class ColumnLocationAdapter {
  private final String description;

  public ColumnLocationAdapter(String description) {
    this.description = description;
  }

  public String getListStyleLocations() {
    String columnLocations = getColumnLocations(description);
    if (textHasColumnStyleLocations(columnLocations))
      return description.replace(columnLocations, getListStyleLocations(columnLocations));
    return description;
  }

  private String getListStyleLocations(String columnLocations) {
    return addCommasBetweenLocations(addPeriodToEnd(columnLocations));
  }

  private boolean textHasColumnStyleLocations(String columnLocations) {
    return columnLocations != null;
  }

  private String addCommasBetweenLocations(String fixed) {
    return fixed.replaceAll("\\.\\.\\.([\n ])*", ", ");
  }

  private String addPeriodToEnd(String columnLocations) {
    return columnLocations.replaceAll("\\.\\.\\.\n\n", ".\n\n").replaceAll("\\.\\.\\.$", ".");
  }

  private String getColumnLocations(String descriptionText) {
    ArrayList<String> match = match("\\.\\.\\. {3}(\n|.)*\\.\\.\\.(\\n\\n|$)", descriptionText);
    if (match.size() > 0) return match.get(0);
    return null;
  }
}
