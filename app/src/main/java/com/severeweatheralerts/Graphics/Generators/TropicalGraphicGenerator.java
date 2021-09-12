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

public abstract class TropicalGraphicGenerator extends GraphicGenerator {
  protected static final int ZOOM_SIZE = 400;
  private final String mapName;

  public TropicalGraphicGenerator(Context context, Alert alert, GCSCoordinate location, String mapName) {
    super(context, alert, location);
    this.mapName = mapName;
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getMapTimes(mapName);
  }

  protected Bounds getBounds(int size, MercatorCoordinate loc) {
    double margin = size * 1000;
    return new BoundRounder(new Bounds(loc.getY()+margin,loc.getX()+margin,loc.getY()-margin,loc.getX()-margin)).getBounds();
  }

  protected abstract Layer getLayer(Bounds bounds, String dateString);

  @Override
  protected void mapTimes(ArrayList<MapTime> mapTimes) {
    MercatorCoordinate loc = getMercatorCoordinate();
    Bounds bounds = getBounds(ZOOM_SIZE, loc);
    String dateString = mapTimes.get(0).getString();
    ArrayList<Layer> layers = new ArrayList<>();
    layers.add(getLayer(bounds, dateString));
    layers.add(new Layer(new URL().getCountyMap(bounds)));
    layers.add(new Layer(getLocationPointOverlay(bounds, Color.CYAN)));
    generateGraphicFromLayers(layers);
  }
}
