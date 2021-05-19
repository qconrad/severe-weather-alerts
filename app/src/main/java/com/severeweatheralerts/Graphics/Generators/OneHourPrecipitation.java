package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Bounds.AspectRatioEqualizer;
import com.severeweatheralerts.Graphics.Bounds.BoundCalculator;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinateToPointAdapter;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;

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
    ArrayList<Layer> layers = new ArrayList<>();
    Bounds bounds = getBounds();
    layers.add(new Layer(new URL().getDualPolPrecipitationType(bounds, radarStation)));
    generateGraphicFromLayers(layers);
  }

  private Bounds getBounds() {
    Polygon polygon = new Polygon();
    MercatorCoordinate userLocation = getMercatorCoordinate();
    polygon.addCoordinate(userLocation);
    polygon.addCoordinate(new MercatorCoordinate(getMercatorCoordinate().getX()-57290, getMercatorCoordinate().getY()+7828)); // user location
    return new AspectRatioEqualizer(new BoundCalculator(polygon).getBounds()).equalize();
  }

  @Override
  protected void layers(ArrayList<Bitmap> bitmaps) {
    Bitmap bitmap = bitmaps.get(0);
    StringBuilder colors = new StringBuilder();
    for (int i = 0; i < 60; i++) {
      colors.append("At minute "+ i + ": ");
      int color = getNextMinute(bitmap, getBounds(), getMercatorCoordinate(), i);
      Color color1 = null;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        color1 = Color.valueOf(color);
      }
      if (color == -16729344) colors.append("Heavy Rain").append("\n");
      else if (color == -16712816) colors.append("Light/Moderate Rain").append("\n");
      else if (color == -1638145) colors.append("Unknown").append("\n");
      else if (color == -3092384) colors.append("Big Drops").append("\n");
      else if (color == -65536) colors.append("Hail/Rain").append("\n");
      else if (color == -6513508) colors.append("Ground Clutter").append("\n");
      else colors.append(color).append(" ").append(color1.toString()).append("\n");
    }
    setSubtext(colors.toString());
    super.layers(bitmaps);
  }

  private int getNextMinute(Bitmap map, Bounds bounds, MercatorCoordinate start, int minute) {
    int offset = -950 * minute;
    MercatorCoordinate mercatorCoordinate = new MercatorCoordinate(start.getX()+offset, start.getY());
    Point point = new MercatorCoordinateToPointAdapter(bounds, 511, 511).getPoint(mercatorCoordinate);
    return map.getPixel(point.x, point.y);
  }
}
