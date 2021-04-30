package com.severeweatheralerts.Graphics;

import com.severeweatheralerts.Graphics.Bounds.Bounds;

public class URL {
  public String getCountyMap(Bounds bounds) {
    return "https://digital.weather.gov/wms.php?LAYERS=cwa,counties,states&FORMAT=image%2Fpng&SERVICE=WMS&TRANSPARENT=TRUE&VERSION=1.3.0&EXCEPTIONS=INIMAGE&REQUEST=GetMap&STYLES=&CRS=EPSG%3A3857&BBOX=" + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop() + "&WIDTH=512&HEIGHT=512";
  }

  public String getTotalSnow(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "totalsnowamt");
  }

  public String getTotalSnowPoints(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "totalsnowamt.points");
  }

  public String getTotalRain(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "totalqpf");
  }

  public String getTotalRainPoints(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "totalqpf.points");
  }

  public String getWindGusts(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "windgust");
  }

  public String getWindGustsPoints(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "windgust.points.english");
  }

  public String getSpcOutlook(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "convoutlook");
  }

  public String getLows(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "mint");
  }

  public String getLowsPoints(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "mint.points");
  }

  public String getApparentTemperature(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "apparentt");
  }

  public String getApparentTemperaturePoints(Bounds bounds, String region, String date) {
    return getParameter(bounds, region, date, "apparentt.points");
  }

  private String getParameter(Bounds bounds, String region, String date, String parameter) {
    return "https://digital.weather.gov/wms.php?LAYERS=ndfd." + region + "." + parameter + "&FORMAT=image%2Fpng&TRANSPARENT=TRUE&VERSION=1.3.0&VT=" + date.replace(":", "%3A") + "&EXCEPTIONS=INIMAGE&SERVICE=WMS&REQUEST=GetMap&STYLES=&CRS=EPSG%3A3857&BBOX=" + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop() + "&WIDTH=512&HEIGHT=512";
  }

  public String getMapTimes(String type, String region) {
    return "https://digital.weather.gov/scripts/get_multivt.php?&grid=ndfd&elmt=" + type + "&region=" + region;
  }

  public String getCompositeRadarImage(Bounds bounds) {
    return "https://opengeo.ncep.noaa.gov/geoserver/conus/conus_bref_qcd/ows?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&FORMAT=image%2Fpng&TRANSPARENT=true&LAYERS=conus_bref_qcd&WIDTH=512&HEIGHT=512&SRS=EPSG%3A3857&BBOX=" + + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop();
  }

  public String getRadarReflectivity(Bounds bounds, String radarStation) {
    return "https://opengeo.ncep.noaa.gov/geoserver/" + radarStation + "/" + radarStation + "_bref_raw/ows?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&FORMAT=image%2Fpng&TRANSPARENT=true&LAYERS=" + radarStation + "_bref_raw&WIDTH=512&HEIGHT=512&SRS=EPSG%3A3857&BBOX=" + + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop();
  }

  public String getRadarRainfall(Bounds bounds, String radarStation) {
    return "https://opengeo.ncep.noaa.gov/geoserver/" + radarStation + "/" + radarStation + "_bdsa/ows?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&FORMAT=image%2Fpng&TRANSPARENT=true&LAYERS=" + radarStation + "_bdsa&WIDTH=512&HEIGHT=512&SRS=EPSG%3A3857&BBOX=" + + bounds.getLeft() + "," + bounds.getBottom() + "," + bounds.getRight() + "," + bounds.getTop();
  }

  public String getPointInfo(double lat, double lon) {
    return "https://api.weather.gov/points/" + lat + "," + lon;
  }
}
