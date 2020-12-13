package com.severeweatheralerts.Adapters;

public class DescriptionHeadlineRemover {
  private String description;

  public DescriptionHeadlineRemover(String description) {
    this.description = description;
  }

  public String removeHeadlinesFromDescription() {
    if (descriptionIsNull()) return null;
    return removeHeadlines(description);
  }

  private String removeHeadlines(String desc) {
    return desc.replaceAll("(^|\n)\\.\\.\\..*\\.\\.\\.\n", "").trim();
  }

  private boolean descriptionIsNull() {
    return description == null;
  }
}
