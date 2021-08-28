package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Color;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Bounds.BoundRounder;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.URL;

import java.util.ArrayList;

public class HurricaneWindThreatGenerator extends GraphicGenerator {
  private static final int ZOOM_SIZE = 400;
  public HurricaneWindThreatGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getMapTimes("tcwind");
  }

  protected Bounds getBounds(int size, MercatorCoordinate loc) {
    double margin = size * 1000;
    return new BoundRounder(new Bounds(loc.getY()+margin,loc.getX()+margin,loc.getY()-margin,loc.getX()-margin)).getBounds();
  }

  @Override
  protected void mapTimes(ArrayList<MapTime> mapTimes) {
    MercatorCoordinate loc = getMercatorCoordinate();
    Bounds bounds = getBounds(ZOOM_SIZE, loc);
    String dateString = mapTimes.get(0).getString();
    ArrayList<Layer> layers = new ArrayList<>();
    layers.add(new Layer(new URL().getHurricaneWindThreat(bounds, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bounds)));
    layers.add(new Layer(getLocationPointOverlay(bounds, Color.CYAN)));
    generateGraphicFromLayers(layers);
  }
}
