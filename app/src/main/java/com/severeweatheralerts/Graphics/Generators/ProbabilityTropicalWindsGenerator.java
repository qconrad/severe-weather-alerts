package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Color;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.GridData.Maximum;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;

import java.util.ArrayList;

public class ProbabilityTropicalWindsGenerator extends TropicalGraphicGenerator {
  private ArrayList<MapTime> mapTimes;
  private Parameter gridData;
  public ProbabilityTropicalWindsGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location, "probwindspd34c");
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getMapTimes("probwindspd34c");
    getPointInfo();
  }

  @Override
  protected void pointInfo(String response) {
    getForecast(new PointInfoParser(response).getForecastGridLink(), "probabilityOfTropicalStormWinds");
  }

  @Override
  protected void mapTimes(ArrayList<MapTime> mapTimes) {
    this.mapTimes = mapTimes;
    fetchComplete();
  }

  @Override
  protected void forecast(Parameter gridData) {
    this.gridData = gridData;
    fetchComplete();
  }

  @Override
  protected Layer getLayer(Bounds bounds, String dateString) {
    return new Layer(new URL().getProbabilityTropicalWinds(bounds, getRegion(), dateString));
  }

  private void fetchComplete() {
    if (mapTimes == null || gridData == null) return;
    setSubtext((int)(new Maximum(gridData).get().getValue()) + "% chance of tropical storm force (≥39mph) winds");
    MercatorCoordinate loc = getMercatorCoordinate();
    Bounds bounds = getBounds(ZOOM_SIZE, loc);
    String dateString = mapTimes.get(mapTimes.size()-1).getString();
    ArrayList<Layer> layers = new ArrayList<>();
    layers.add(getLayer(bounds, dateString));
    layers.add(new Layer(new URL().getCountyMap(bounds)));
    layers.add(new Layer(getLocationPointOverlay(bounds, Color.YELLOW)));
    generateGraphicFromLayers(layers);
  }
}
