package com.severeweatheralerts.Graphics.GridData;

public class MapRegion {
  private final String senderCode;

  public MapRegion(String senderCode) {
    this.senderCode = senderCode;
  }

  public String get() {
    if (senderCode.equals("afg") || senderCode.equals("afc")) return "alaska";
    if (senderCode.equals("hfo")) return "hawaii";
    if (senderCode.equals("gum")) return "guam";
    if (senderCode.equals("sju")) return "puertori";
    return "conus";
  }
}
