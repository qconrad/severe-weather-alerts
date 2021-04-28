package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Color;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.URL;

import java.util.ArrayList;

public class SPCOutlookGenerator extends GraphicGenerator {
  private ArrayList<MapTime> mapTimes;

  public SPCOutlookGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getMapTimes("convoutlook");
  }

  @Override
  protected void mapTimes(ArrayList<MapTime> mapTimes) {
    this.mapTimes = mapTimes;
    generateLayers();
  }

  private void generateLayers() {
    MercatorCoordinate loc = getMercatorCoordinate();
    double margin = 400000;
    Bounds bounds = new Bounds(loc.getY()+margin,loc.getX()+margin,loc.getY()-margin,loc.getX()-margin);
    ArrayList<Layer> layers = new ArrayList<>();
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getStartTime()).getMapTime().getString();
    layers.add(new Layer(new URL().getSpcOutlook(bounds, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCompositeRadarImage(bounds)));
    layers.add(new Layer(new URL().getCountyMap(bounds)));
    layers.add(new Layer(getLocationPointOverlay(bounds, Color.CYAN)));
    generateGraphicFromLayers(layers);
  }
}
