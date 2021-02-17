package com.severeweatheralerts.Graphics.URLGeneration;

import com.severeweatheralerts.Graphics.Bounds.Bound;

public class URL {
  public String getCountyMap(Bound bounds) {
    return "https://digital.weather.gov/wms.php?LAYERS=cwa,counties,states&FORMAT=image%2Fpng&VERSION=1.3.0&EXCEPTIONS=INIMAGE&SERVICE=WMS&REQUEST=GetMap&STYLES=&CRS=EPSG%3A3857&BBOX=" + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop() + "&WIDTH=512&HEIGHT=512";
  }

  public String getTotalSnow(Bound bounds, String region, String date) {
    return "https://digital.weather.gov/wms.php?LAYERS=ndfd." + region + ".totalsnowamt&FORMAT=image%2Fpng&VERSION=1.3.0&VT=" + date + "&EXCEPTIONS=INIMAGE&SERVICE=WMS&REQUEST=GetMap&STYLES=&CRS=EPSG%3A3857&BBOX=" + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop() + "&WIDTH=512&HEIGHT=512";
  }

  public String getTotalSnowPoints(Bound bounds, String region, String date) {
    return "https://digital.weather.gov/wms.php?LAYERS=ndfd." + region + ".totalsnowamt.points&FORMAT=image%2Fpng&VERSION=1.3.0&VT=" + date + "&EXCEPTIONS=INIMAGE&SERVICE=WMS&REQUEST=GetMap&STYLES=&CRS=EPSG%3A3857&BBOX=" + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop() + "&WIDTH=512&HEIGHT=512";
  }

  public String getMapTimes(String type, String region) {
    return "https://digital.weather.gov/scripts/get_multivt.php?&grid=ndfd&elmt=" + type + "&region=" + region;
  }
}
