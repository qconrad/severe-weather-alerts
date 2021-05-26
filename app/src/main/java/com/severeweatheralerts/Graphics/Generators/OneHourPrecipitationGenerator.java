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
import com.severeweatheralerts.Graphics.BitmapTools.LocationDrawer;
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
import com.severeweatheralerts.TimeFormatters.RelativeTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static com.severeweatheralerts.TextUtils.DateTimeConverter.convertStringToDate;

public class OneHourPrecipitationGenerator extends GraphicGenerator {
  private final int heading;
  private final double speedMetersPerSecond;
  private String radarStation;
  private Date radarTime;
  private final MercatorCoordinate location;
  private final Bounds bounds;
  private Bitmap map;

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
    polygon.addCoordinate(getCoordinateAt(location, -60));
    polygon.addCoordinate(getCoordinateAt(location, 4500));
    return new BoundMargin(new AspectRatioEqualizer(new BoundCalculator(polygon).getBounds()).equalize(), 1.00).getBounds();
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
      map = bitmaps.get(0);
      ArrayList<ForecastTime> forecast = new ArrayList<>();
      String subtext = "";
      Bitmap hour = Bitmap.createBitmap(256, 50, Bitmap.Config.ARGB_8888);
      for (int i = -60; i <= 4500; i += 10) {
        double hourPercent = i / 3600.0;
        double lookAround = Math.min((((hourPercent * hourPercent) * 30000)+1000), 30000);
        // PrecipitationType precipitationType = getPrecipitationType(getCoordinateAt(location, i));
        int sum = 0;
        int count = 0;
        for (int w = (int) -lookAround; w < lookAround; w+= Math.max(lookAround / 5, 1)) {
          count++;
          DiagonalOffset diagonalOffset = new DiagonalOffset(w, heading + 90);
          MercatorCoordinate mercatorCoordinate = getCoordinateAt(new MercatorCoordinate(location.getX() + diagonalOffset.getX(), location.getY() + diagonalOffset.getY()), i);
          if (i % 900 == 0) bitmaps.add(new LocationDrawer(bounds, mercatorCoordinate, Color.RED).getBitmap());
          PrecipitationType precipitationType = getPrecipitationType(mercatorCoordinate);
          sum += precipitationType.ordinal();
        }
        forecast.add(new ForecastTime(getDateAt(i), (double)sum/count));
      }
//      forecast = smoothNoise(new ParameterTrim(new Parameter(forecast)).trimLeft(new Date()).trimRight(new Date(new Date().getTime() + 60*60*1000)).getTrimmed()).getForecastTimes();
      forecast = new ParameterTrim(getSmoothed(new Parameter(forecast))).trimLeft(new Date()).trimRight(new Date(new Date().getTime() + 60 * 60 * 1000)).getTrimmed().getForecastTimes();
      int lastVal = 0;
      for (int i = 0; i < forecast.size(); i++) {
        String formattedString = new RelativeTimeFormatter(new Date(), forecast.get(i).getDate()).getFormattedString();
        int val = (int) Math.round(forecast.get(i).getValue());
        if (lastVal != val) {
          subtext += formattedString + ": " + PrecipitationType.values()[val] + "\n";
          lastVal = val;
        }
        for (int y = 0; y < 50; y++)
          hour.setPixel((int) (i/(forecast.size()/255.0)), y, getColor(forecast.get(i).getValue()));
      }
//      bitmaps.clear();
      bitmaps.add(hour);
      setSubtext(subtext);
      super.layers(bitmaps);
    }).start();
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
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      return new ColorMap(colors, 5.0).getValue((value));
    }
    return 0;
  }

  private Date getDateAt(int secondsOffset) {
    return new Date(radarTime.getTime() + (secondsOffset * 1000));
  }

  public enum PrecipitationType { NONE, BIG_DROPS, LIGHT_MOD_RAIN, HEAVY_RAIN, HAIL_RAIN, LARGE_HAIL }

  private PrecipitationType getPrecipitationType(MercatorCoordinate coordinate) {
    int color = getColorAt(coordinate);
    if (color == -16729344) return PrecipitationType.HEAVY_RAIN;
    if (color == -16712816) return PrecipitationType.LIGHT_MOD_RAIN;
    if (color == -3092384) return PrecipitationType.BIG_DROPS;
    if (color == -65536) return PrecipitationType.HAIL_RAIN;
    if (color == -2980732) return PrecipitationType.LIGHT_MOD_RAIN;
    if (color == -16711681) return PrecipitationType.HEAVY_RAIN;
    if (color == -1638145) return PrecipitationType.LARGE_HAIL;
    return PrecipitationType.NONE;
  }

  private int getColorAt(MercatorCoordinate coordinate) {
    Point point = new MercatorCoordinateToPointAdapter(bounds, 511, 511).getPoint(coordinate);
    return map.getPixel(point.x, 511 - point.y);
  }
}
