package com.severeweatheralerts.Adapters;

public class HeadlineAdapter {
  private final NwsHeadlineAdapter nwsHeadlineAdapter;
  private final String descHeadline;

  public HeadlineAdapter(String nwsHeadline, String description) {
    this.nwsHeadlineAdapter = new NwsHeadlineAdapter(nwsHeadline);
    descHeadline = new DescriptionHeadlineAdapter(description).adaptDescriptionHeadline();
  }

  public String getLargeHeadline() {
    if (descHeadline != null) return descHeadline;
    return nwsHeadlineAdapter.adaptNwsHeadlineLarge();
  }

  public String getSmallHeadline() {
    return nwsHeadlineAdapter.adaptNwsHeadlineSmall();
  }
}
