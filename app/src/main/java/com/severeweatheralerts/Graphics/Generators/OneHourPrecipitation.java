package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Color;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.AspectRatioEqualizer;
import com.severeweatheralerts.Graphics.Bounds.BoundCalculator;
import com.severeweatheralerts.Graphics.Bounds.BoundRounder;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;
import com.severeweatheralerts.Networking.FetchServices.ImageFetchService;

import java.util.ArrayList;

public class OneHourPrecipitation extends GraphicGenerator {
  private String radarStation;

  public OneHourPrecipitation(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getPointInfo();
  }

  @Override
  protected void pointInfo(String response) {
    radarStation = new PointInfoParser(response).getRadarStation().toLowerCase();
    fetchFinished();
  }

  private void fetchFinished() {
    if (radarStation != null) generateLayers();
  }

  private void generateLayers() {
    //Bounds bounds = getBounds(polygons, Constants.DEFAULT_GRAPHIC_MARGIN);
    ArrayList<Layer> layers = new ArrayList<>();
    Polygon polygon = new Polygon();
    polygon.addCoordinate(new MercatorCoordinate(getMercatorCoordinate().getX(), getMercatorCoordinate().getY())); // user location
    polygon.addCoordinate(new MercatorCoordinate(getMercatorCoordinate().getX()-57290, getMercatorCoordinate().getY()+7828)); // user location
    Bounds bounds = new AspectRatioEqualizer(new BoundCalculator(polygon).getBounds()).equalize();
    layers.add(new Layer(new URL().getDualPolPrecipitationType(bounds, radarStation)));
    //layers.add(new Layer(new URL().getCountyMap(bounds)));
    //layers.add(new Layer(getZoneOverlay(bounds)));
    //layers.add(new Layer(getLocationPointOverlay(bounds, Color.YELLOW)));
    generateGraphicFromLayers(layers);
  }

  protected Bounds getBounds(int size, MercatorCoordinate loc) {
    double margin = size * 1000;
    return new BoundRounder(new Bounds(loc.getY()+margin,loc.getX()+margin,loc.getY()-margin,loc.getX()-margin)).getBounds();
  }
}
