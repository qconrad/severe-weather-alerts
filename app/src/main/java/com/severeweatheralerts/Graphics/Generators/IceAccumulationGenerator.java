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
import com.severeweatheralerts.Graphics.Tools.Rounder;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;
import com.severeweatheralerts.TextUtils.Plurality;

import java.util.ArrayList;
import java.util.Date;

public class IceAccumulationGenerator extends GraphicGenerator {
  boolean subTextSet = false;
  private ArrayList<MapTime> mapTimes;
  private ArrayList<Polygon> polygons;

  public IceAccumulationGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getAlertPolygons();
    getMapTimes("totaliceaccum");
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
    getForecast(new PointInfoParser(response).getForecastGridLink(), "iceAccumulation");
  }

  @Override
  protected void forecast(Parameter gridData) {
    double iceAccumulationInches = mmToIn(getIceAccumulation(gridData));
    setSubtext(getForecastSubtext(alert, iceAccumulationInches));
    subTextSet = true;
    fetchFinish();
  }

  private String getForecastSubtext(Alert alert, double iceAccumulation) {
    Date currentTime = new Date();
    boolean eventStarted = currentTime.after(alert.getStartTime());
    double iceAccumulationRounded = new Rounder(iceAccumulation, 1).getRounded();

    if (eventStarted) {
      if (iceAccumulation <= 0) {
        return "No more ice is expected";
      } else if (iceAccumulation <= 0.1) {
        return "Remaining ice accumulations of a light glaze.";
      } else {
        return "Remaining ice accumulations of " + new Plurality(iceAccumulationRounded, " inch", " inches").getText();
      }
    } else {
      if (iceAccumulation <= 0) {
        return "No ice is expected";
      } else if (iceAccumulation <= 0.1) {
        return "A glaze of ice is expected. Roads and sidewalks may be icy.";
      } else if (iceAccumulation <= 0.5) {
        return "Roads and sidewalks will become icy and there may be some tree damage and power outages. Total ice accumulations of around " + iceAccumulationRounded + new Plurality(iceAccumulationRounded, " inch", " inches").getText();
      } else if (iceAccumulation <= 1) {
        return "Widespread tree damage and power outages possible. Travel will be dangerous and walking outdoors may be difficult. Total ice accumulations of around " + iceAccumulationRounded + new Plurality(iceAccumulationRounded, " inch", " inches").getText();
      } else {
        return "Widespread impacts, including dangerous travel conditions, power outages, and significant tree damage. Walking outdoors wil be difficult. Total ice accumulations of around " + iceAccumulationRounded + new Plurality(iceAccumulationRounded, " inch", " inches").getText();
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
    Bounds bounds = getBounds(polygons, Constants.DEFAULT_GRAPHIC_MARGIN, Constants.MINIMUM_ICE_GRAPHIC_SIZE);
    ArrayList<Layer> layers = new ArrayList<>();
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getEndTime()).getMapTime().getString();
    layers.add(new Layer(new URL().getTotalIce(bounds, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bounds)));
    layers.add(new Layer(getZoneOverlay(bounds)));
    layers.add(new Layer(getLocationPointOverlay(bounds, Color.YELLOW)));
    layers.add(new Layer(new URL().getTotalIcePoints(bounds, getRegion(), dateString)));
    generateGraphicFromLayers(layers);
  }

  private double getIceAccumulation(Parameter forecast) {
    return new SumCalculator(new InterpolatedParameterTrim(forecast)
              .trimLeft(new Date())
              .trimRight(alert.getEndTime())
              .trim())
              .getSum();
  }
}
