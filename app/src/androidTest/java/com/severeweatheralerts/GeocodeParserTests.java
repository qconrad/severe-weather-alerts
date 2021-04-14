package com.severeweatheralerts;

import com.severeweatheralerts.JSONParsing.GeocodeParser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GeocodeParserTests {
  @Test
  public void getLatitude() {
    String geocodeString = "{\"standard\":{\"addresst\":{},\"city\":\"Chicago\",\"prov\":\"US\",\"countryname\":\"United States of America\",\"postal\":{},\"confidence\":\"0.90\"},\"longt\":\"-87.68808\",\"alt\":{\"loc\":{\"longt\":\"-87.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"41.841697\"}},\"elevation\":{},\"latt\":\"41.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals(41.85013, geocodeParser.getLatitude(), 0.001);
  }

  @Test
  public void getDifferentLatitude() {
    String geocodeString = "{\"standard\":{\"addresst\":{},\"city\":\"Chicago\",\"prov\":\"US\",\"countryname\":\"United States of America\",\"postal\":{},\"confidence\":\"0.90\"},\"longt\":\"-87.68808\",\"alt\":{\"loc\":{\"longt\":\"-87.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals(42.85013, geocodeParser.getLatitude(), 0.001);
  }

  @Test
  public void getLongitude() {
    String geocodeString = "{\"standard\":{\"addresst\":{},\"city\":\"Chicago\",\"prov\":\"US\",\"countryname\":\"United States of America\",\"postal\":{},\"confidence\":\"0.90\"},\"longt\":\"-87.68808\",\"alt\":{\"loc\":{\"longt\":\"-87.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals(-87.68808, geocodeParser.getLongitude(), 0.001);
  }

  @Test
  public void getDifferentLongitude() {
    String geocodeString = "{\"standard\":{\"addresst\":{},\"city\":\"Chicago\",\"prov\":\"US\",\"countryname\":\"United States of America\",\"postal\":{},\"confidence\":\"0.90\"},\"longt\":\"-88.68808\",\"alt\":{\"loc\":{\"longt\":\"-88.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals(-88.68808, geocodeParser.getLongitude(), 0.001);
  }

  @Test
  public void getCity() {
    String geocodeString = "{\"standard\":{\"addresst\":{},\"city\":\"Chicago\",\"prov\":\"US\",\"countryname\":\"United States of America\",\"postal\":{},\"confidence\":\"0.90\"},\"longt\":\"-88.68808\",\"alt\":{\"loc\":{\"longt\":\"-88.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals("Chicago", geocodeParser.getCity());
  }

  @Test
  public void getDifferentCity() {
    String geocodeString = "{\"standard\":{\"addresst\":{},\"city\":\"Des Monies\",\"prov\":\"US\",\"countryname\":\"United States of America\",\"postal\":{},\"confidence\":\"0.90\"},\"longt\":\"-88.68808\",\"alt\":{\"loc\":{\"longt\":\"-88.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals("Des Monies", geocodeParser.getCity());
  }

  @Test
  public void getAddress() {
    String geocodeString = "{\"standard\":{\"addresst\":\"Main Street\",\"city\":\"Des Monies\",\"prov\":\"US\",\"stnumber\":\"123\",\"countryname\":\"United States of America\",\"postal\":{},\"confidence\":\"0.90\"},\"longt\":\"-88.68808\",\"alt\":{\"loc\":{\"longt\":\"-88.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals("123 Main Street", geocodeParser.getAddress());
  }

  @Test
  public void getDifferentAddress() {
    String geocodeString = "{\"standard\":{\"addresst\":\"Main St\",\"city\":\"Des Monies\",\"prov\":\"US\",\"stnumber\":\"1234\",\"countryname\":\"United States of America\",\"postal\":{},\"confidence\":\"0.90\"},\"longt\":\"-88.68808\",\"alt\":{\"loc\":{\"longt\":\"-88.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals("1234 Main St", geocodeParser.getAddress());
  }

  @Test
  public void getPostal() {
    String geocodeString = "{\"standard\":{\"addresst\":\"Main St\",\"city\":\"Des Monies\",\"prov\":\"US\",\"stnumber\":\"1234\",\"countryname\":\"United States of America\",\"postal\":\"60000\",\"confidence\":\"0.90\"},\"longt\":\"-88.68808\",\"alt\":{\"loc\":{\"longt\":\"-88.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals("60000", geocodeParser.getPostal());
  }

  @Test
  public void getDifferentPostal() {
    String geocodeString = "{\"standard\":{\"addresst\":\"Main St\",\"city\":\"Des Monies\",\"prov\":\"US\",\"stnumber\":\"1234\",\"countryname\":\"United States of America\",\"postal\":\"61000\",\"confidence\":\"0.90\"},\"longt\":\"-88.68808\",\"alt\":{\"loc\":{\"longt\":\"-88.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals("61000", geocodeParser.getPostal());
  }

  @Test
  public void getPostal_EmptyObject_ReturnsNull() {
    String geocodeString = "{\"standard\":{\"addresst\":\"Main St\",\"city\":\"Des Monies\",\"prov\":\"US\",\"stnumber\":\"1234\",\"countryname\":\"United States of America\",\"postal\":\"{}\",\"confidence\":\"0.90\"},\"longt\":\"-88.68808\",\"alt\":{\"loc\":{\"longt\":\"-88.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"{}\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertNull(geocodeParser.getPostal());
  }

  @Test
  public void doesNotHaveError() {
    String geocodeString = "{\"standard\":{\"addresst\":\"Main St\",\"city\":\"Des Monies\",\"prov\":\"US\",\"stnumber\":\"1234\",\"countryname\":\"United States of America\",\"postal\":\"61000\",\"confidence\":\"0.90\"},\"longt\":\"-88.68808\",\"alt\":{\"loc\":{\"longt\":\"-88.745393\",\"prov\":\"IL\",\"city\":\"Chicago\",\"postal\":\"60804\",\"score\":\"20\",\"latt\":\"42.841697\"}},\"elevation\":{},\"latt\":\"42.85013\"}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertFalse(geocodeParser.hasError());
  }

  @Test
  public void hasError() {
    String geocodeString = "{\"error\":{\"description\":\"Your request produced no suggestions.\",\"code\":\"018\"}}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertTrue(geocodeParser.hasError());
  }

  @Test
  public void getError() {
    String geocodeString = "{\"error\":{\"description\":\"Your request produced no suggestions.\",\"code\":\"018\"}}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals("Your request produced no suggestions.", geocodeParser.getError());
  }

  @Test
  public void getDifferentError() {
    String geocodeString = "{\"error\":{\"description\":\"Your request produced suggestions.\",\"code\":\"018\"}}";
    GeocodeParser geocodeParser = new GeocodeParser(geocodeString);
    assertEquals("Your request produced suggestions.", geocodeParser.getError());
  }
}
