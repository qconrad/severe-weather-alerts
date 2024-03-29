package com.severeweatheralerts.Graphics.Tools.Bitmaps;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinateToPointAdapter;
import com.severeweatheralerts.Graphics.Polygon.Polygon;

import java.util.ArrayList;

public class ZoneDrawer {
  private final int color;
  private final Bounds bounds;
  private final ArrayList<Polygon> polygons;
  private final Paint paint;
  private final Bitmap bitmap;
  private final Canvas canvas;

  public ZoneDrawer(ArrayList<Polygon> polygons, int color, Bounds bounds) {
    this.color = color;
    this.polygons = polygons;
    this.bounds = bounds;
    paint = getZonePaint();
    bitmap = createBitmap();
    canvas =  new Canvas(bitmap);
    drawPolygons();
  }

  public Bitmap getBitmap() {
    return bitmap;
  }

   private void drawPolygons() {
     ArrayList<Polygon> polygonsCopy = new ArrayList<>(polygons);
     for (Polygon polygon : polygonsCopy) drawPolygon(polygon);
   }

  private void drawPolygon(Polygon polygon) {
    Path path = new Path();
    movePathToStartPosition(polygon, path);
    drawCoordinates(polygon, path);
    canvas.drawPath(path, paint);
  }

  private void drawCoordinates(Polygon polygon, Path path) {
    for (MercatorCoordinate coordinate : polygon.getCoordinates())
      drawLineToCoordinate(path, coordinate);
  }

  private void movePathToStartPosition(Polygon polygon, Path path) {
    Point startPoint = new MercatorCoordinateToPointAdapter(bounds, getWidth(), getHeight()).getPoint(polygon.getCoordinate(0));
    path.moveTo(startPoint.x , getHeight() - startPoint.y);
  }

  private void drawLineToCoordinate(Path path, MercatorCoordinate coordinate) {
    Point point = new MercatorCoordinateToPointAdapter(bounds, getWidth(), getHeight()).getPoint(coordinate);
    path.lineTo(point.x, getHeight() - point.y);
  }

  protected Bitmap createBitmap() {
    return Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
  }

  protected int getWidth() {
    return 512;
  }

  protected int getHeight() {
    return 512;
  }

  protected Paint getZonePaint() {
    Paint paint = new Paint();

    // Stroke
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(5);

    // Set color and shadow
    paint.setColor(color);
    paint.setShadowLayer(5, 0, 0, Color.BLACK);

    // Rounded corners
    paint.setDither(true);
    paint.setStrokeJoin(Paint.Join.ROUND);
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setPathEffect(new CornerPathEffect(15) );

    return paint;
  }
}
