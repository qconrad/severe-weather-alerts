package com.severeweatheralerts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class URLGeneratorTests {
  @Test
  public void getCountyMap_BoundsGiven_BoundsInURL() {
    Bounds bounds = new Bounds(1, 2, 3, 4);
    String expectedUrl = "https://digital.weather.gov/wms.php?LAYERS=counties,states&FORMAT=image%2Fpng&VERSION=1.3.0&EXCEPTIONS=INIMAGE&SERVICE=WMS&REQUEST=GetMap&STYLES=&CRS=EPSG%3A3857&BBOX=4.0,3.0,2.0,1.0&WIDTH=512&HEIGHT=512";
    assertEquals(expectedUrl, new URLGenerator().getCountyMap(bounds));
  }

  @Test
  public void getCountyMap_DifferentBoundsGiven_BoundsInURL() {
    Bounds bounds = new Bounds(4, 3, 2, 1);
    String expectedUrl = "https://digital.weather.gov/wms.php?LAYERS=counties,states&FORMAT=image%2Fpng&VERSION=1.3.0&EXCEPTIONS=INIMAGE&SERVICE=WMS&REQUEST=GetMap&STYLES=&CRS=EPSG%3A3857&BBOX=1.0,2.0,3.0,4.0&WIDTH=512&HEIGHT=512";
    assertEquals(expectedUrl, new URLGenerator().getCountyMap(bounds));
  }
}
