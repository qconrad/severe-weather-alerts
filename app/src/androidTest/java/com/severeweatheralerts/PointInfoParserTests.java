package com.severeweatheralerts;

import com.severeweatheralerts.JSONParsing.PointInfoParser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointInfoParserTests {
  String pointInfoJson = "{ \"@context\": [ \"https://geojson.org/geojson-ld/geojson-context.jsonld\", { \"@version\": \"1.1\", \"wx\": \"https://api.weather.gov/ontology#\", \"s\": \"https://schema.org/\", \"geo\": \"http://www.opengis.net/ont/geosparql#\", \"unit\": \"http://codes.wmo.int/common/unit/\", \"@vocab\": \"https://api.weather.gov/ontology#\", \"geometry\": { \"@id\": \"s:GeoCoordinates\", \"@type\": \"geo:wktLiteral\" }, \"city\": \"s:addressLocality\", \"state\": \"s:addressRegion\", \"distance\": { \"@id\": \"s:Distance\", \"@type\": \"s:QuantitativeValue\" }, \"bearing\": { \"@type\": \"s:QuantitativeValue\" }, \"value\": { \"@id\": \"s:value\" }, \"unitCode\": { \"@id\": \"s:unitCode\", \"@type\": \"@id\" }, \"forecastOffice\": { \"@type\": \"@id\" }, \"forecastGridData\": { \"@type\": \"@id\" }, \"publicZone\": { \"@type\": \"@id\" }, \"county\": { \"@type\": \"@id\" } } ], \"id\": \"https://api.weather.gov/points/35,-81\", \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ -81, 35 ] }, \"properties\": { \"@id\": \"https://api.weather.gov/points/35,-81\", \"@type\": \"wx:Point\", \"cwa\": \"GSP\", \"forecastOffice\": \"https://api.weather.gov/offices/GSP\", \"gridId\": \"GSP\", \"gridX\": 113, \"gridY\": 53, \"forecast\": \"https://api.weather.gov/gridpoints/GSP/113,53/forecast\", \"forecastHourly\": \"https://api.weather.gov/gridpoints/GSP/113,53/forecast/hourly\", \"forecastGridData\": \"https://api.weather.gov/gridpoints/GSP/113,53\", \"observationStations\": \"https://api.weather.gov/gridpoints/GSP/113,53/stations\", \"relativeLocation\": { \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ -80.988933000000003, 35.014048000000003 ] }, \"properties\": { \"city\": \"Riverview\", \"state\": \"SC\", \"distance\": { \"value\": 1859.0424436098001, \"unitCode\": \"unit:m\" }, \"bearing\": { \"value\": 212, \"unitCode\": \"unit:degrees_true\" } } }, \"forecastZone\": \"https://api.weather.gov/zones/forecast/SCZ009\", \"county\": \"https://api.weather.gov/zones/county/SCC091\", \"fireWeatherZone\": \"https://api.weather.gov/zones/fire/SCZ009\", \"timeZone\": \"America/New_York\", \"radarStation\": \"KGSP\" } }";
  String pointInfoJsonDifferent = "{ \"@context\": [ \"https://geojson.org/geojson-ld/geojson-context.jsonld\", { \"@version\": \"1.1\", \"wx\": \"https://api.weather.gov/ontology#\", \"s\": \"https://schema.org/\", \"geo\": \"http://www.opengis.net/ont/geosparql#\", \"unit\": \"http://codes.wmo.int/common/unit/\", \"@vocab\": \"https://api.weather.gov/ontology#\", \"geometry\": { \"@id\": \"s:GeoCoordinates\", \"@type\": \"geo:wktLiteral\" }, \"city\": \"s:addressLocality\", \"state\": \"s:addressRegion\", \"distance\": { \"@id\": \"s:Distance\", \"@type\": \"s:QuantitativeValue\" }, \"bearing\": { \"@type\": \"s:QuantitativeValue\" }, \"value\": { \"@id\": \"s:value\" }, \"unitCode\": { \"@id\": \"s:unitCode\", \"@type\": \"@id\" }, \"forecastOffice\": { \"@type\": \"@id\" }, \"forecastGridData\": { \"@type\": \"@id\" }, \"publicZone\": { \"@type\": \"@id\" }, \"county\": { \"@type\": \"@id\" } } ], \"id\": \"https://api.weather.gov/points/35,-81\", \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ -81, 35 ] }, \"properties\": { \"@id\": \"https://api.weather.gov/points/35,-81\", \"@type\": \"wx:Point\", \"cwa\": \"GSP\", \"forecastOffice\": \"https://api.weather.gov/offices/GSP\", \"gridId\": \"GSP\", \"gridX\": 113, \"gridY\": 53, \"forecast\": \"https://api.weather.gov/gridpoints/GSP/113,53/forecast\", \"forecastHourly\": \"https://api.weather.gov/gridpoints/GSP/113,53/forecast/hourly\", \"forecastGridData\": \"https://api.weather.gov/gridpoints/GSP/0,1\", \"observationStations\": \"https://api.weather.gov/gridpoints/GSP/113,53/stations\", \"relativeLocation\": { \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ -80.988933000000003, 35.014048000000003 ] }, \"properties\": { \"city\": \"Riverview\", \"state\": \"SC\", \"distance\": { \"value\": 1859.0424436098001, \"unitCode\": \"unit:m\" }, \"bearing\": { \"value\": 212, \"unitCode\": \"unit:degrees_true\" } } }, \"forecastZone\": \"https://api.weather.gov/zones/forecast/SCZ009\", \"county\": \"https://api.weather.gov/zones/county/SCC091\", \"fireWeatherZone\": \"https://api.weather.gov/zones/fire/SCZ009\", \"timeZone\": \"America/New_York\", \"radarStation\": \"KLOT\" } }";

  @Test
  public void getForecastGridLink_ReturnsCorrectURL() {
    PointInfoParser pointInfoParser = new PointInfoParser(pointInfoJson);
    assertEquals("https://api.weather.gov/gridpoints/GSP/113,53", pointInfoParser.getForecastGridLink());
  }

  @Test
  public void getForecastGridLink_DifferentGridLink_CorrectURLReturned() {
    PointInfoParser pointInfoParser = new PointInfoParser(pointInfoJsonDifferent);
    assertEquals("https://api.weather.gov/gridpoints/GSP/0,1", pointInfoParser.getForecastGridLink());
  }

  @Test
  public void getRadarStation_CorrectStationReturned() {
    PointInfoParser pointInfoParser = new PointInfoParser(pointInfoJson);
    assertEquals("KGSP", pointInfoParser.getRadarStation());
  }

  @Test
  public void getRadarStation_DifferentStation_CorrectStationReturned() {
    PointInfoParser pointInfoParser = new PointInfoParser(pointInfoJsonDifferent);
    assertEquals("KLOT", pointInfoParser.getRadarStation());
  }
}
