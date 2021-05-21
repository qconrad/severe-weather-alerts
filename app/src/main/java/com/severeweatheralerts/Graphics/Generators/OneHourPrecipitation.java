package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;

import com.android.volley.VolleyError;
import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.AspectRatioEqualizer;
import com.severeweatheralerts.Graphics.Bounds.BoundCalculator;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.DiagonalOffset;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinateToPointAdapter;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;
import com.severeweatheralerts.Networking.FetchServices.RequestCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;
import com.severeweatheralerts.TextUtils.DateTimeConverter;
import com.severeweatheralerts.TextUtils.RegExMatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class OneHourPrecipitation extends GraphicGenerator {
  private final int angle = 207;
  private final double metersPerSecond = 31 / 1.944;
  private String radarStation;
  private Date radarTime;
  private final MercatorCoordinate location;
  private final Bounds bounds;
  private Bitmap map;

  public OneHourPrecipitation(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
    this.location = getMercatorCoordinate();
    bounds = getBounds();
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
    if (radarStation != null) getRadarImageTime();
  }

  private void getRadarImageTime() {
    StringFetchService fetch = new StringFetchService(context, new URL().getRadarCapabilities(radarStation, "bdhc"));
    fetch.setUserAgent(Constants.USER_AGENT);
    fetch.request(new RequestCallback() {

      @Override
      public void success(Object response) {
        radarTime = parseTime(response);
        if (radarTime == null) throwError("Error parsing image times");

        ArrayList<Layer> layers = new ArrayList<>();
        layers.add(new Layer(new URL().getDualPolPrecipitationType(bounds, radarStation)));
        generateGraphicFromLayers(layers);
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error getting image times");
      }
    });
  }

  private Date parseTime(Object response) {
    ArrayList<String> timeMatch = RegExMatcher.match("time\\\" default=\\\".*\\\"", response.toString());
    if (timeMatch.size() < 1) return null;
    return DateTimeConverter.convertStringToDate(timeMatch.get(0).split("\\\"")[2], "yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC"));
  }

  private Bounds getBounds() {
    Polygon polygon = new Polygon();
    polygon.addCoordinate(getCoordinateAt(location, 0));
    polygon.addCoordinate(getCoordinateAt(location, 3600));
    return new AspectRatioEqualizer(new BoundCalculator(polygon).getBounds()).equalize();
  }

  private MercatorCoordinate getCoordinateAt(MercatorCoordinate start, int secondOffset) {
    return offsetCoordinate(start, new DiagonalOffset(-metersPerSecond * secondOffset, angle));
  }

  private MercatorCoordinate offsetCoordinate(MercatorCoordinate start, DiagonalOffset offset) {
    return new MercatorCoordinate(start.getX() + offset.getX(), start.getY() + offset.getY());
  }

  @Override
  protected void layers(ArrayList<Bitmap> bitmaps) {
    map = bitmaps.get(0);
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    String subtext = "";
    Bitmap hour = Bitmap.createBitmap(256, 50, Bitmap.Config.ARGB_8888);
    for (int i = 0; i <= 3600; i += 10) {
      PrecipitationType precipitationType = getPrecipitationType(getCoordinateAt(location, i));
      forecast.add(new ForecastTime(getDateAt(i), precipitationType.ordinal()));
      subtext += PrecipitationType.values()[(int) forecast.get(i/10).getValue()] + "\n";
      for (int y = 0; y < 50; y++)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          hour.setPixel((int) ((i/10)/1.411764705882353), y, Color.valueOf((float) (forecast.get(i/10).getValue()/4.0), (float) ((float) 1.0 - (forecast.get(i/10).getValue()/4.0)), 0).toArgb());
        }
    }
    bitmaps.clear();
    bitmaps.add(hour);
    setSubtext(subtext);
    super.layers(bitmaps);
  }

  private Date getDateAt(int secondsOffset) {
    return new Date(radarTime.getTime() + (secondsOffset * 1000));
  }

  public enum PrecipitationType { NONE, BIG_DROPS, LIGHT_MOD_RAIN, HEAVY_RAIN, HAIL_RAIN }

  private PrecipitationType getPrecipitationType(MercatorCoordinate coordinate) {
    int color = getColorAt(coordinate);
    if (color == -16729344) return PrecipitationType.HEAVY_RAIN;
    if (color == -16712816) return PrecipitationType.LIGHT_MOD_RAIN;
    if (color == -3092384) return PrecipitationType.BIG_DROPS;
    if (color == -65536) return PrecipitationType.HAIL_RAIN;
    if (color == -2980732) return PrecipitationType.LIGHT_MOD_RAIN;
    if (color == -16711681) return PrecipitationType.HEAVY_RAIN;
    return PrecipitationType.NONE;
  }

  private int getColorAt(MercatorCoordinate coordinate) {
    Point point = new MercatorCoordinateToPointAdapter(bounds, 511, 511).getPoint(coordinate);
    return map.getPixel(point.x, 511 - point.y);
  }
}
