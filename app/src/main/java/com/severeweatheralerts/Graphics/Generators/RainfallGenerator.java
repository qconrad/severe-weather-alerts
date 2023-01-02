package com.severeweatheralerts.Graphics.Generators;

import static com.severeweatheralerts.Graphics.Tools.UnitConverter.mmToIn;

import android.content.Context;
import android.graphics.Color;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.GridData.NextMapTimeFromDate;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.GridData.SumCalculator;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.Tools.InterpolatedParameterTrim;
import com.severeweatheralerts.Graphics.Tools.RangeGenerator;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.PointInfoParser;

import java.util.ArrayList;
import java.util.Date;

public class RainfallGenerator extends GraphicGenerator {
  private ArrayList<Polygon> polygons;
  private ArrayList<MapTime> mapTimes;
  private boolean subTextSet;

  public RainfallGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
  }

  @Override
  public void generate(GraphicCompleteListener graphicCompleteListener) {
    super.generate(graphicCompleteListener);
    getAlertPolygons();
    getMapTimes("totalqpf");
    getPointInfo();
  }

  @Override
  protected void alertPolygons(ArrayList<Polygon> polygons) {
    this.polygons = polygons;
    fetchFinish();
  }

  @Override
  protected void mapTimes(ArrayList<MapTime> mapTimes) {
    this.mapTimes = mapTimes;
    fetchFinish();
  }

  @Override
  protected void pointInfo(String response) {
    getForecast(new PointInfoParser(response).getForecastGridLink(), "quantitativePrecipitation");
  }

  @Override
  protected void forecast(Parameter forecast) {
    double rainfallAmountInches = mmToIn(getRainfall(forecast));
   setSubtext(getForecastSubtext(alert, rainfallAmountInches));
//    setSubtext(rainfallAmountInches + new Plurality(rainfallAmountInches, " inch", " inches").getText());
    subTextSet = true;
    fetchFinish();
  }

  private String getForecastSubtext(Alert alert, double rainfallAmountInches) {
    Date currentTime = new Date();
    boolean eventStarted = currentTime.after(alert.getStartTime());
    RangeGenerator rangeGenerator = new RangeGenerator(alert, rainfallAmountInches);
    rangeGenerator.setFallbackMargin(0.5);
    String rangeForecast = rangeGenerator.getRange();

    if (eventStarted) {
      if (rainfallAmountInches <= 0) {
        return "No more rain is expected";
      } else if (rainfallAmountInches <= 0.1) {
        return "Rain is expected to stop soon";
      } else if (rainfallAmountInches <= 0.5) {
        return "Light rain is expected, with additional amounts of " + rangeForecast;
      } else if (rainfallAmountInches <= 1) {
        return "Moderate rain is expected, with additional amounts of " + rangeForecast;
      } else {
        return "Heavy rain is expected, with additional amounts of " + rangeForecast;
      }
    } else {
      if (rainfallAmountInches <= 0) {
        return "No rainfall expected with this event";
      } else if (rainfallAmountInches <= 0.1) {
        return "Only a trace of rain is expected";
      } else if (rainfallAmountInches <= 0.5) {
        return "Light rain is expected, with total amounts of " + rangeForecast;
      } else if (rainfallAmountInches <= 1) {
        return "Moderate rain is expected, with total amounts of " + rangeForecast;
      } else {
        return "Heavy rain is expected, with total amounts of " + rangeForecast;
      }
    }
  }

  private void fetchFinish() {
    if (polygons != null && mapTimes != null && subTextSet) generateLayers();
  }

  private void generateLayers() {
    Bounds bounds = getBounds(polygons, Constants.DEFAULT_GRAPHIC_MARGIN, Constants.MINIMUM_RAINFALL_GRAPHIC_SIZE);
    ArrayList<Layer> layers = new ArrayList<>();
    String dateString = new NextMapTimeFromDate(mapTimes, alert.getEndTime()).getMapTime().getString();
    layers.add(new Layer(new URL().getTotalRain(bounds, getRegion(), dateString)));
    layers.add(new Layer(new URL().getCountyMap(bounds)));
    layers.add(new Layer(getZoneOverlay(bounds)));
    layers.add(new Layer(getLocationPointOverlay(bounds, Color.YELLOW)));
    layers.add(new Layer(new URL().getTotalRainPoints(bounds, getRegion(), dateString)));
    generateGraphicFromLayers(layers);
  }

  private double getRainfall(Parameter gridData) {
    return new SumCalculator(new InterpolatedParameterTrim(gridData)
              .trimLeft(new Date())
              .trimRight(alert.getEndTime())
              .trim())
              .getSum();
  }
}
