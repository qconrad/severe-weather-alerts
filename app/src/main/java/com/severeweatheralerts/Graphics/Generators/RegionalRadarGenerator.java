package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Color;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Bounds.BoundRounder;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.URL;

import java.util.ArrayList;

public class RegionalRadarGenerator extends GraphicGenerator {
  private final int size;

  public RegionalRadarGenerator(Context context, Alert alert, GCSCoordinate location, int size) {
    super(context, alert, location);
    this.size = size;
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    generateGraphic();
  }

  protected void generateGraphic() {
    MercatorCoordinate loc = getMercatorCoordinate();
    Bounds bounds = getBounds(size, loc);
    generateGraphicFromLayers(getLayers(bounds));
  }

  protected ArrayList<Layer> getLayers(Bounds bounds) {
    ArrayList<Layer> layers = new ArrayList<>();
    layers.add(new Layer(new URL().getCompositeRadarImage(bounds)));
    layers.add(new Layer(new URL().getCountyMap(bounds)));
    layers.add(new Layer(getLocationPointOverlay(bounds, Color.CYAN)));
    return layers;
  }

  protected Bounds getBounds(int size, MercatorCoordinate loc) {
    double margin = size * 1000;
    return new BoundRounder(new Bounds(loc.getY()+margin,loc.getX()+margin,loc.getY()-margin,loc.getX()-margin)).getBounds();
  }
}
