package com.severeweatheralerts.Graphics.Generators;

import static com.severeweatheralerts.Graphics.Tools.UnitConverter.mmToIn;

import android.content.Context;
import android.graphics.Color;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.SumCalculator;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.Tools.InterpolatedParameterTrim;
import com.severeweatheralerts.Graphics.Tools.RangeGenerator;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;

import java.util.ArrayList;
import java.util.Date;

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
    double snowfallAmountInches = mmToIn(getSnowfall(gridData));
    setSubtext(getForecastSubtext(alert, snowfallAmountInches));
    subTextSet = true;
    fetchFinish();
  }

  private String getForecastSubtext(Alert alert, double snowfallAmountInches) {
    Date currentTime = new Date();
    boolean eventStarted = currentTime.after(alert.getStartTime());
    String rangeForecast = new RangeGenerator(alert, snowfallAmountInches).getRange();

    if (eventStarted) {
      if (snowfallAmountInches <= 0) {
        return "No more snow is expected";
      } else if (snowfallAmountInches <= 0.1) {
        return "Snow is expected to stop soon";
      } else if (snowfallAmountInches <= 0.25) {
        return "Light snow is expected to stop soon";
      } else if (snowfallAmountInches <= .75) {
        return "Light snow is expected with remaining accumulations of " + rangeForecast;
      } else {
        return "Snow is expected to continue with remaining accumulations of " + rangeForecast;
      }
    } else {
      if (snowfallAmountInches <= 0) {
        return "No snow is expected";
      } else if (snowfallAmountInches <= 0.1) {
        return "Only a trace of snow is expected";
      } else if (snowfallAmountInches <= 0.25) {
        return "Only a small amount of snow is expected, with a light dusting likely.";
      } else {
        return "Total snow accumulations of " + rangeForecast;
      }
    }
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
    return new SumCalculator(new InterpolatedParameterTrim(forecast)
              .trimLeft(new Date())
              .trimRight(alert.getEndTime())
              .trim())
              .getSum();
  }
}
