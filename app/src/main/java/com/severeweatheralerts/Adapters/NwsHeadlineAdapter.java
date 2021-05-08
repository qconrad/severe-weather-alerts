package com.severeweatheralerts.Adapters;

import static com.severeweatheralerts.TextUtils.RegExMatcher.match;
import static com.severeweatheralerts.TextUtils.TitleCaseConverter.toTitleCase;

public class NwsHeadlineAdapter {
  private final String nwsHeadline;

  public NwsHeadlineAdapter(String nwsHeadline) {
    this.nwsHeadline = nwsHeadline;
  }

  public String adaptNwsHeadlineSmall() {
    if (nwsHeadlineIsNull() || shouldBeLargeHeadline()) return null;
    return smallHeadlineFormat();
  }

  public String adaptNwsHeadlineLarge() {
    if (nwsHeadlineIsNull() || shouldBeSmallHeadline()) return null;
    return largeHeadlineFormat();
  }

  private String largeHeadlineFormat() {
    return toTitleCase(replaceEllipsesWithCommas(nwsHeadline));
  }

  private boolean nwsHeadlineIsNull() {
    return nwsHeadline == null;
  }

  private String replaceEllipsesWithCommas(String nwsHeadline) {
    return nwsHeadline.replaceAll("\\.\\.\\. *", ", ");
  }

  private String smallHeadlineFormat() {
    return replaceEllipsesWithCommas(multilineSeparateHeadlines());
  }

  private String multilineSeparateHeadlines() {
    return nwsHeadline.replaceAll("\\.\\.\\. \\.\\.\\.", "\n\n");
  }

  private boolean shouldBeLargeHeadline() {
    return !shouldBeSmallHeadline() ;
  }

  private boolean shouldBeSmallHeadline() {
    return containsTime() ||
           hasMultipleHeadlines() ||
           caseInsensitiveContains("in effect") ||
           caseInsensitiveContains("expire") ||
           caseInsensitiveContains("cancel");
  }

  private boolean hasMultipleHeadlines() {
    return nwsHeadline.contains("... ...");
  }

  private boolean caseInsensitiveContains(String text) {
    return nwsHeadline.toLowerCase().contains(text);
  }

  private boolean containsTime() {
    return !match("\\d\\d [A|P]M", nwsHeadline).isEmpty();
  }
}
