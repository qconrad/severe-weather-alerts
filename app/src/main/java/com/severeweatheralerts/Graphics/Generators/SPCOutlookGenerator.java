package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.URL;

import java.util.ArrayList;

public class SPCOutlookGenerator extends RegionalRadarGenerator {
  private static final int ZOOM_SIZE = 400;
  public SPCOutlookGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location, ZOOM_SIZE);
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
  }

  @Override
  protected void generateGraphic() {
    getMapTimes("convoutlook");
  }


  @Override
  protected void mapTimes(ArrayList<MapTime> mapTimes) {
    MercatorCoordinate loc = getMercatorCoordinate();
    Bounds bounds = getBounds(ZOOM_SIZE, loc);
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getStartTime()).getMapTime().getString();
    ArrayList<Layer> layers = getLayers(bounds);
    layers.add(0, new Layer(new URL().getSpcOutlook(bounds, getRegion(), dateString)));
    generateGraphicFromLayers(layers);
  }
}
