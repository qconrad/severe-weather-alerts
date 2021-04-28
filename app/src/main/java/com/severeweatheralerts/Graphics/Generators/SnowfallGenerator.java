package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Color;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.GridData.SumCalculator;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.Rounder;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;
import com.severeweatheralerts.TextUtils.Plurality;

import java.util.ArrayList;
import java.util.Date;

import static com.severeweatheralerts.Constants.SNOWFALL_AMOUNT_DECIMAL_PLACES;
import static com.severeweatheralerts.Graphics.UnitConverter.mmToIn;

public class SnowfallGenerator extends GraphicGenerator {
  boolean subTextSet = false;
  private ArrayList<MapTime> mapTimes;
  private ArrayList<Polygon> polygons;

  public SnowfallGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getAlertPolygons();
    getMapTimes("snowamt");
    getPointInfo();
  }

  @Override
  protected void alertPolygons(ArrayList<Polygon> polygons) {
    this.polygons = polygons;
    fetchFinish();
  }

  @Override
  protected void pointInfo(String response) {
    super.pointInfo(response);
    getForecast(new PointInfoParser(response).getForecastGridLink(), "snowfallAmount");
  }

  @Override
  protected void forecast(Parameter gridData) {
    double snowfallAmount = new Rounder(mmToIn(getSnowfall(gridData)), SNOWFALL_AMOUNT_DECIMAL_PLACES).getRounded();
    setSubtext(snowfallAmount + new Plurality(snowfallAmount, " inch", " inches").getText());
    subTextSet = true;
    fetchFinish();
  }

  @Override
  protected void mapTimes(ArrayList<MapTime> mapTimes) {
    this.mapTimes = mapTimes;
    fetchFinish();
  }

  private void fetchFinish() {
    if (polygons != null && mapTimes != null && subTextSet) generateLayers();
  }

  private void generateLayers() {
    Bounds bounds = getBounds(polygons, Constants.DEFAULT_GRAPHIC_MARGIN);
    ArrayList<Layer> layers = new ArrayList<>();
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getEndTime()).getMapTime().getString();
    layers.add(new Layer(new URL().getTotalSnow(bounds, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bounds)));
    layers.add(new Layer(getZoneOverlay(bounds)));
    layers.add(new Layer(getLocationPointOverlay(bounds, Color.YELLOW)));
    layers.add(new Layer(new URL().getTotalSnowPoints(bounds, getRegion(), dateString)));
    generateGraphicFromLayers(layers);
  }

  private double getSnowfall(Parameter forecast) {
    return new SumCalculator(new ParameterTrim(forecast)
              .trimLeft(new Date())
              .trimRight(alert.getEndTime())
              .getTrimmed())
              .getSum();
  }
}
