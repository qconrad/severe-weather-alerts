package com.severeweatheralerts.Graphics.Generators.OneHourPrecipitation;

import static com.severeweatheralerts.TextUtils.DateTimeConverter.convertStringToDate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

import com.android.volley.VolleyError;
import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.AspectRatioEqualizer;
import com.severeweatheralerts.Graphics.Bounds.BoundCalculator;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.Generators.GraphicCompleteListener;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.ParameterSmooth;
import com.severeweatheralerts.Graphics.GridData.ParameterTrim;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinateToPointAdapter;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.Tools.ColorMap;
import com.severeweatheralerts.Graphics.Tools.DiagonalOffset;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.Graphics.ViewInflaters.OneHourPrecipitationGraphic;
import com.severeweatheralerts.JSONParsing.PointInfoParser;
import com.severeweatheralerts.Networking.FetchServices.RequestCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;
import com.severeweatheralerts.TextUtils.RegExMatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

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
    getRadarImageTime(radarStation);
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
    ArrayList<MercatorCoordinate> startRange = getPerpendicularCoordinates(getCoordinateAt(location, 0), getMarginAtPercent(0));
    polygon.addCoordinate(startRange.get(startRange.size() - 1));
    polygon.addCoordinate(startRange.get(0));
    ArrayList<MercatorCoordinate> endRange = getPerpendicularCoordinates(getCoordinateAt(location, 4200), getMarginAtPercent(1.166));
    polygon.addCoordinate(endRange.get(endRange.size() - 1));
    polygon.addCoordinate(endRange.get(0));
    return new AspectRatioEqualizer(new BoundCalculator(polygon).getBounds()).equalize();
  }

  private ArrayList<MercatorCoordinate> getPerpendicularCoordinates(MercatorCoordinate coordinate, double marginMeters) {
    ArrayList<MercatorCoordinate> coordinates = new ArrayList<>();
    double interval = marginMeters / 5.0;
    for (double i = 0; i < 10; i++)  {
      DiagonalOffset diagonalOffset = new DiagonalOffset(-marginMeters + (i * interval), heading + 90);
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
      ArrayList<ForecastTime> forecast = getForecast(bitmaps.get(0));
      graphicCompleteListener.onComplete(new OneHourPrecipitationGraphic(context, getBitmap(forecast), new PrecipitationTextGenerator(forecast, new Date()).getText()));
    }).start();
  }

  private ArrayList<ForecastTime> getForecast(Bitmap map) {
    ArrayList<ForecastTime> forecast = new ArrayList<>();
    for (int seconds = 0; seconds <= 4200; seconds += 10)
      forecast.add(new ForecastTime(getDateAt(seconds), parseForecastPoint(map, seconds)));
    return trimAndSmooth(forecast);
  }

  private double parseForecastPoint(Bitmap map, int seconds) {
    ArrayList<MercatorCoordinate> coordinates = getPerpendicularCoordinates(getCoordinateAt(location, seconds), getMarginAtPercent(seconds / 3600.0));
    ArrayList<Integer> nearbyPrecip = new ArrayList<>();
    for (MercatorCoordinate coordinate : coordinates)
      nearbyPrecip.add(getPrecipitationType(map, coordinate).ordinal());
    return getAverage(new UnknownFilter(nearbyPrecip).filter());
  }

  private double getAverage(ArrayList<Integer> values) {
    double sum = 0.0;
    for (Integer value : values) sum += value;
    return sum / values.size();
  }

  private ArrayList<ForecastTime> trimAndSmooth(ArrayList<ForecastTime> forecast) {
    forecast = new ParameterTrim(getSmoothed(new Parameter(forecast))).trimLeft(new Date()).trimRight(new Date(new Date().getTime() + 60 * 60 * 1000)).trim().getForecastTimes();
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
    colors.add(Color.argb(128, 82, 120, 167));
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
    if (color == -16712816 || color == -2980732 || color == -16711681 || color == -16740097)
      return PrecipitationType.LIGHT_MOD_RAIN;
    if (color == -3092384) return PrecipitationType.BIG_DROPS;
    if (color == -65536) return PrecipitationType.HAIL_RAIN;
    if (color == -1638145) return PrecipitationType.LARGE_HAIL;
    return PrecipitationType.NONE;
  }

  private int getColorAt(Bitmap map, MercatorCoordinate coordinate) {
    Point point = new MercatorCoordinateToPointAdapter(bounds, 511, 511).getPoint(coordinate);
    return map.getPixel(point.x, 511 - point.y);
  }
}
