package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.BitmapTools.LocationDrawer;
import com.severeweatheralerts.Graphics.Bounds.AspectRatioEqualizer;
import com.severeweatheralerts.Graphics.Bounds.BoundCalculator;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.DiagonalOffset;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinateToPointAdapter;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;

import java.util.ArrayList;

public class OneHourPrecipitation extends GraphicGenerator {
  private final int angle = 20;
  private final int metersPerMinute = 950;
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
    layers.add(new Layer(new LocationDrawer(bounds, getMercatorCoordinate(), Color.YELLOW).getBitmap()));
    generateGraphicFromLayers(layers);
  }

  private Bounds getBounds() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(getMercatorCoordinate());
    DiagonalOffset offset = new DiagonalOffset(-metersPerMinute * 60, angle);
    polygon.addCoordinate(new MercatorCoordinate(getMercatorCoordinate().getX()+offset.getX(), getMercatorCoordinate().getY()+offset.getY()));
    return new AspectRatioEqualizer(new BoundCalculator(polygon).getBounds()).equalize();
  }

  @Override
  protected void layers(ArrayList<Bitmap> bitmaps) {
    Bitmap bitmap = bitmaps.get(0);
    StringBuilder colors = new StringBuilder();
    for (int i = 0; i <= 60; i++) {
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
      else if (color == -6513508) colors.append("Biological").append("\n");
      else if (color == -9013642) colors.append("Ground Clutter").append("\n");
      else if (color == 0) colors.append("Nothing").append("\n");
      else colors.append(color).append(" ").append(color1.toString()).append("\n");
      if (i % 15 == 0) {
        DiagonalOffset diagonalOffset = new DiagonalOffset(-metersPerMinute * i, angle);
        MercatorCoordinate mercatorCoordinate = new MercatorCoordinate(getMercatorCoordinate().getX()+diagonalOffset.getX(), getMercatorCoordinate().getY()+diagonalOffset.getY());
        bitmaps.add(new LocationDrawer(getBounds(), mercatorCoordinate, Color.RED).getBitmap());
      }
    }
    setSubtext(colors.toString());
    super.layers(bitmaps);
  }

  private int getNextMinute(Bitmap map, Bounds bounds, MercatorCoordinate start, int minute) {
    DiagonalOffset diagonalOffset = new DiagonalOffset(-metersPerMinute * minute, angle);
    MercatorCoordinate mercatorCoordinate = new MercatorCoordinate(start.getX()+diagonalOffset.getX(), start.getY()+diagonalOffset.getY());
    Point point = new MercatorCoordinateToPointAdapter(bounds, 511, 511).getPoint(mercatorCoordinate);
    return map.getPixel(point.x, 511 - point.y);
  }
}
