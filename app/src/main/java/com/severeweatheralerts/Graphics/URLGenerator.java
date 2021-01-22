package com.severeweatheralerts.Graphics;

public class URLGenerator {
  public String getCountyMap(Bounds bounds) {
    return "https://digital.weather.gov/wms.php?LAYERS=counties,states&FORMAT=image%2Fpng&VERSION=1.3.0&EXCEPTIONS=INIMAGE&SERVICE=WMS&REQUEST=GetMap&STYLES=&CRS=EPSG%3A3857&BBOX=" + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop() + "&WIDTH=512&HEIGHT=512";
  }
}
