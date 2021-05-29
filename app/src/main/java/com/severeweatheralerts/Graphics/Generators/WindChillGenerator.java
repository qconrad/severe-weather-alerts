package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Color;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.GridData.Minimum;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;

import java.util.ArrayList;
import java.util.Date;

import static com.severeweatheralerts.Graphics.Tools.UnitConverter.cToF;

public class WindChillGenerator extends GraphicGenerator {
  private ArrayList<Polygon> polygons;
  private ArrayList<MapTime> mapTimes;
  private boolean subTextSet;
  private ForecastTime minWindChill;

  public WindChillGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getAlertPolygons();
    getMapTimes("apparenttt");
    getPointInfo();
  }

  @Override
  protected void alertPolygons(ArrayList<Polygon> polygons) {
    this.polygons = polygons;
    fetchFinish();
  }

  @Override
  protected void mapTimes(ArrayList<MapTime> mapTimes) {
    this.mapTimes = mapTimes;
    fetchFinish();
  }

  @Override
  protected void pointInfo(String response) {
    getForecast(new PointInfoParser(response).getForecastGridLink(), "apparentTemperature");
  }

  @Override
  protected void forecast(Parameter forecast) {
    if (forecast.getCount() < 1) throwError("Wind chills not available");
    minWindChill = getMinimumWindChill(forecast);
    setSubtext(Math.round(cToF(minWindChill.getValue())) + "°F");
    subTextSet = true;
    fetchFinish();
  }

  private void fetchFinish() {
    if (subTextSet && polygons != null && mapTimes != null) generateLayers();
  }

  private void generateLayers() {
    Bounds bounds = getBounds(polygons, Constants.DEFAULT_GRAPHIC_MARGIN);
    ArrayList<Layer> layers = new ArrayList<>();
    String dateString = new NextMapTimeFromDate(mapTimes, minWindChill.getDate()).getMapTime().getString();
    layers.add(new Layer(new URL().getApparentTemperature(bounds, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bounds)));
    layers.add(new Layer(getZoneOverlay(bounds)));
    layers.add(new Layer(getLocationPointOverlay(bounds, Color.YELLOW)));
    layers.add(new Layer(new URL().getApparentTemperaturePoints(bounds, getRegion(), dateString)));
    generateGraphicFromLayers(layers);
  }

  private ForecastTime getMinimumWindChill(Parameter gridData) {
    return new Minimum(new ParameterTrim(gridData)
            .trimLeft(new Date())
            .trimRight(alert.getEndTime())
            .getTrimmed())
            .get();
  }
}
