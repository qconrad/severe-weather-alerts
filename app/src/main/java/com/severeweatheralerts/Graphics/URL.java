package com.severeweatheralerts.Graphics;

import com.severeweatheralerts.Graphics.Bounds.Bound;

public class URL {
  public String getCountyMap(Bound bounds) {
    return "https://digital.weather.gov/wms.php?LAYERS=cwa,counties,states&FORMAT=image%2Fpng&VERSION=1.3.0&EXCEPTIONS=INIMAGE&REQUEST=GetMap&STYLES=&CRS=EPSG%3A3857&BBOX=" + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop() + "&WIDTH=512&HEIGHT=512";
  }

  public String getTotalSnow(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "totalsnowamt");
  }

  public String getTotalSnowPoints(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "totalsnowamt.points");
  }

  public String getTotalRain(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "totalqpf");
  }

  public String getTotalRainPoints(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "totalqpf.points");
  }

  public String getWindGusts(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "windgust");
  }

  public String getWindGustsPoints(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "windgust.points.english");
  }

  public String getSpcOutlook(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "convoutlook");
  }

  public String getSpcOutlookPoints(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "convoutlook.points");
  }

  public String getLows(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "mint");
  }

  public String getLowsPoints(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "mint.points");
  }

  public String getApparentTemperature(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "apparentt");
  }

  public String getApparentTemperaturePoints(Bound bounds, String region, String date) {
    return getParameter(bounds, region, date, "apparentt.points");
  }

  private String getParameter(Bound bounds, String region, String date, String parameter) {
    return "https://digital.weather.gov/wms.php?LAYERS=ndfd." + region + "." + parameter + "&FORMAT=image%2Fpng&VERSION=1.3.0&VT=" + date + "&EXCEPTIONS=INIMAGE&REQUEST=GetMap&STYLES=&CRS=EPSG%3A3857&BBOX=" + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop() + "&WIDTH=512&HEIGHT=512";
  }

  public String getMapTimes(String type, String region) {
    return "https://digital.weather.gov/scripts/get_multivt.php?&grid=ndfd&elmt=" + type + "&region=" + region;
  }

  public String getRadarImage(Bound bounds) {
    return "https://opengeo.ncep.noaa.gov/geoserver/conus/conus_bref_qcd/ows?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&FORMAT=image%2Fpng&TRANSPARENT=true&LAYERS=conus_bref_qcd&WIDTH=512&HEIGHT=512&SRS=EPSG%3A3857&BBOX=" + + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop();
  }

  public String getPointInfo(double lat, double lon) {
    return "https://api.weather.gov/points/" + lat + "," + lon;
  }
}
