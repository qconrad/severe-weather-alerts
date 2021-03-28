package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.GridData.MapRegion;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapRegionTests {
  @Test
  public void getRegion_defaultIsConus() {
    MapRegion mapRegion = new MapRegion("lot");
    assertEquals("conus", mapRegion.get());
  }

  @Test
  public void getRegion_sjuGiven_ReturnsPuertori() {
    MapRegion mapRegion = new MapRegion("sju");
    assertEquals("puertori", mapRegion.get());
  }

  @Test
  public void getRegion_gumGiven_ReturnsGuam() {
    MapRegion mapRegion = new MapRegion("gum");
    assertEquals("guam", mapRegion.get());
  }

  @Test
  public void getRegion_hfoGiven_ReturnsHawaii() {
    MapRegion mapRegion = new MapRegion("hfo");
    assertEquals("hawaii", mapRegion.get());
  }

  @Test
  public void getRegion_afgGiven_ReturnsAlaska() {
    MapRegion mapRegion = new MapRegion("afg");
    assertEquals("alaska", mapRegion.get());
  }
}
