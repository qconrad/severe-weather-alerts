package com.severeweatheralerts.Graphics.BitmapTools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinateToPointAdapter;

public class LocationDrawer {
  private final Bounds bounds;
  private final MercatorCoordinate location;
  private final int color;

  private final Bitmap bitmap;
  private final Canvas canvas;

  public LocationDrawer(Bounds bounds, MercatorCoordinate location, int color) {
    this.bounds = bounds;
    this.location = location;
    this.color = color;
    bitmap = createBitmap();
    canvas =  new Canvas(bitmap);
    drawLocation();
  }

  public Bitmap getBitmap() {
    return bitmap;
  }

  protected void drawLocation() {
    if (location != null) {
      Point locPoint = new MercatorCoordinateToPointAdapter(bounds, getWidth(), getHeight()).getPoint(location);
      canvas.drawCircle(locPoint.x, getHeight() - locPoint.y, 10, getLocationPaint());
    }
  }

  protected Paint getLocationPaint() {
    Paint paint = new Paint();
    paint.setColor(color);
    paint.setStyle(Paint.Style.FILL);
    paint.setShadowLayer(5, 0, 0, Color.BLACK);
    return paint;
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
}
