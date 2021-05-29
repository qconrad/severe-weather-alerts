package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Color;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.Tools.Rounder;
import com.severeweatheralerts.Graphics.GridData.SumCalculator;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;
import com.severeweatheralerts.TextUtils.Plurality;

import java.util.ArrayList;
import java.util.Date;

import static com.severeweatheralerts.Constants.RAINFALL_AMOUNT_DECIMAL_PLACES;
import static com.severeweatheralerts.Graphics.Tools.UnitConverter.mmToIn;

public class RainfallGenerator extends GraphicGenerator {
  private ArrayList<Polygon> polygons;
  private ArrayList<MapTime> mapTimes;
  private boolean subTextSet;

  public RainfallGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getAlertPolygons();
    getMapTimes("totalqpf");
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
    getForecast(new PointInfoParser(response).getForecastGridLink(), "quantitativePrecipitation");
  }

  @Override
  protected void forecast(Parameter forecast) {
    double rainfallAmount = new Rounder(mmToIn(getRainfall(forecast)), RAINFALL_AMOUNT_DECIMAL_PLACES).getRounded();
    setSubtext(rainfallAmount + new Plurality(rainfallAmount, " inch", " inches").getText());
    subTextSet = true;
    fetchFinish();
  }

  private void fetchFinish() {
    if (polygons != null && mapTimes != null && subTextSet) generateLayers();
  }

  private void generateLayers() {
    Bounds bounds = getBounds(polygons, Constants.DEFAULT_GRAPHIC_MARGIN);
    ArrayList<Layer> layers = new ArrayList<>();
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getEndTime()).getMapTime().getString();
    layers.add(new Layer(new URL().getTotalRain(bounds, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bounds)));
    layers.add(new Layer(getZoneOverlay(bounds)));
    layers.add(new Layer(getLocationPointOverlay(bounds, Color.YELLOW)));
    layers.add(new Layer(new URL().getTotalRainPoints(bounds, getRegion(), dateString)));
    generateGraphicFromLayers(layers);
  }

  private double getRainfall(Parameter gridData) {
    return new SumCalculator(new ParameterTrim(gridData)
              .trimLeft(new Date())
              .trimRight(alert.getEndTime())
              .getTrimmed())
              .getSum();
  }
}
