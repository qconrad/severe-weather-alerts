package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;

import com.android.volley.VolleyError;
import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.ColorMap;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.AspectRatioEqualizer;
import com.severeweatheralerts.Graphics.Bounds.BoundCalculator;
import com.severeweatheralerts.Graphics.Bounds.BoundMargin;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.DiagonalOffset;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinateToPointAdapter;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;
import com.severeweatheralerts.Networking.FetchServices.RequestCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;
import com.severeweatheralerts.ParameterSmooth;
import com.severeweatheralerts.TextUtils.RegExMatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static com.severeweatheralerts.TextUtils.DateTimeConverter.convertStringToDate;

public class OneHourPrecipitationGenerator extends GraphicGenerator {
  private final int heading;
  private final double speedMetersPerSecond;
  private Date radarTime;
  private final MercatorCoordinate location;
  private final Bounds bounds;

  public OneHourPrecipitationGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
    this.location = getMercatorCoordinate();
    heading = alert.getMotionVector().getHeading();
    speedMetersPerSecond = alert.getMotionVector().getVelocity() / 1.944;
    bounds = getBounds();
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getPointInfo();
  }

  @Override
  protected void pointInfo(String response) {
    String radarStation = new PointInfoParser(response).getRadarStation().toLowerCase();
    if (radarStation != null) getRadarImageTime(radarStation);
  }

  private void getRadarImageTime(String radarStation) {
    StringFetchService fetch = new StringFetchService(context, new URL().getRadarCapabilities(radarStation, "bdhc"));
    fetch.setUserAgent(Constants.USER_AGENT);
    fetch.request(new RequestCallback() {
      @Override
      public void success(Object response) {
        String timeString = getTimeString(response);
        radarTime = getDateFromString(timeString);
        if (radarTime == null) throwError("Error parsing image times");
        ArrayList<Layer> layers = new ArrayList<>();
        layers.add(new Layer(new URL().getDualPolPrecipitationType(bounds, radarStation, timeString)));
        generateGraphicFromLayers(layers);
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error getting image times");
      }
    });
  }

  private String getTimeString(Object response) {
    ArrayList<String> timeMatch = RegExMatcher.match("time\\\" default=\\\".*\\\"", response.toString());
    if (timeMatch.size() < 1) return null;
    return timeMatch.get(0).split("\\\"")[2];
  }

  private Date getDateFromString(String string) {
    return convertStringToDate(string, "yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC"));
  }

  private Bounds getBounds() {
    Polygon polygon = new Polygon();
    ArrayList<MercatorCoordinate> startRange = getPerpendicularCoordinates(getCoordinateAt(location, 0), (int) getMarginAtPercent(0), 10);
    polygon.addCoordinate(startRange.get(startRange.size() - 1));
    polygon.addCoordinate(startRange.get(0));
    ArrayList<MercatorCoordinate> endRange = getPerpendicularCoordinates(getCoordinateAt(location, 3600), (int) getMarginAtPercent(1.0), 10);
    polygon.addCoordinate(endRange.get(endRange.size() - 1));
    polygon.addCoordinate(endRange.get(0));
    return new BoundMargin(new AspectRatioEqualizer(new BoundCalculator(polygon).getBounds()).equalize(), 0.1).getBounds();
  }

  private ArrayList<MercatorCoordinate> getPerpendicularCoordinates(MercatorCoordinate coordinate, int marginMeters, int numPoints) {
    ArrayList<MercatorCoordinate> coordinates = new ArrayList<>();
    for (int i = -marginMeters; i < marginMeters; i += Math.max(marginMeters / (numPoints / 2), 1)) {
      DiagonalOffset diagonalOffset = new DiagonalOffset(i, heading + 90);
      coordinates.add(new MercatorCoordinate(coordinate.getX() + diagonalOffset.getX(), coordinate.getY() + diagonalOffset.getY()));
    }
    return coordinates;
  }

  private MercatorCoordinate getCoordinateAt(MercatorCoordinate start, int secondOffset) {
    return offsetCoordinate(start, new DiagonalOffset(-speedMetersPerSecond * secondOffset, heading));
  }

  private MercatorCoordinate offsetCoordinate(MercatorCoordinate start, DiagonalOffset offset) {
    return new MercatorCoordinate(start.getX() + offset.getX(), start.getY() + offset.getY());
  }

  @Override
  protected void layers(ArrayList<Bitmap> bitmaps) {
    new Thread(() -> {
      bitmaps.add(getBitmap(getForecast(bitmaps.get(0))));
      bitmaps.remove(0);
      super.layers(bitmaps);
    }).start();
  }

  private ArrayList<ForecastTime> getForecast(Bitmap map) {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    for (int seconds = 0; seconds <= 3600; seconds += 10)
      forecast.add(new ForecastTime(getDateAt(seconds), parseForecastPoint(map, seconds)));
    return trimAndSmooth(forecast);
  }

  private double parseForecastPoint(Bitmap map, int seconds) {
    ArrayList<MercatorCoordinate> coordinates = getPerpendicularCoordinates(getCoordinateAt(location, seconds), (int) getMarginAtPercent(seconds / 3600.0), 10);
    double sum = 0.0;
    for (MercatorCoordinate coordinate : coordinates) sum += getPrecipitationType(map, coordinate).ordinal();
    return sum / coordinates.size();
  }

  private ArrayList<ForecastTime> trimAndSmooth(ArrayList<ForecastTime> forecast) {
    forecast = new ParameterTrim(getSmoothed(new Parameter(forecast))).trimLeft(new Date()).trimRight(new Date(new Date().getTime() + 60 * 60 * 1000)).getTrimmed().getForecastTimes();
    return forecast;
  }

  private Bitmap getBitmap(ArrayList<ForecastTime> forecast) {
    Bitmap hour = Bitmap.createBitmap(256, 32, Bitmap.Config.ARGB_8888);
    for (int i = 0; i < forecast.size(); i++) {
      for (int y = 0; y < 32; y++)
        hour.setPixel((int) (i / (forecast.size() / 255.0)), y, getColor(forecast.get(i).getValue()));
    }
    return hour;
  }

  private double getMarginAtPercent(double percent) {
    return ((percent * percent) * 30000) + 1000;
  }

  private Parameter getSmoothed(Parameter parameter) {
    return smoothNoise(smoothUncertainty(parameter));
  }

  private Parameter smoothNoise(Parameter parameter) {
    return new ParameterSmooth(parameter, 0.05).constantSmooth();
  }

  private Parameter smoothUncertainty(Parameter parameter) {
    return new ParameterSmooth(parameter, 0.15).exponentialSmoothAfter(new Date());
  }

  private int getColor(double value) {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.argb(0, 146, 31, 213));
    colors.add(Color.parseColor("#26b552"));
    colors.add(Color.parseColor("#089b06"));
    colors.add(Color.parseColor("#ebcd0d"));
    colors.add(Color.parseColor("#e30308"));
    colors.add(Color.parseColor("#fb4eee"));
    colors.add(Color.parseColor("#921fd5"));
    return new ColorMap(colors, 5.0).getValue((value));
  }

  private Date getDateAt(int secondsOffset) {
    return new Date(radarTime.getTime() + (secondsOffset * 1000));
  }

  public enum PrecipitationType { NONE, BIG_DROPS, LIGHT_MOD_RAIN, HEAVY_RAIN, HAIL_RAIN, LARGE_HAIL }

  private PrecipitationType getPrecipitationType(Bitmap map, MercatorCoordinate coordinate) {
    int color = getColorAt(map, coordinate);
    if (color == -16729344) return PrecipitationType.HEAVY_RAIN;
    if (color == -16712816) return PrecipitationType.LIGHT_MOD_RAIN;
    if (color == -3092384) return PrecipitationType.BIG_DROPS;
    if (color == -65536) return PrecipitationType.HAIL_RAIN;
    if (color == -2980732) return PrecipitationType.LIGHT_MOD_RAIN;
    if (color == -16711681) return PrecipitationType.HEAVY_RAIN;
    if (color == -1638145) return PrecipitationType.LARGE_HAIL;
    return PrecipitationType.NONE;
  }

  private int getColorAt(Bitmap map, MercatorCoordinate coordinate) {
    Point point = new MercatorCoordinateToPointAdapter(bounds, 511, 511).getPoint(coordinate);
    return map.getPixel(point.x, 511 - point.y);
  }
}
