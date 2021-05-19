package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.VolleyError;
import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Adapters.GeoJSONPolygon;
import com.severeweatheralerts.Adapters.PolygonAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.BitmapTools.BitmapCombiner;
import com.severeweatheralerts.Graphics.BitmapTools.LocationDrawer;
import com.severeweatheralerts.Graphics.BitmapTools.ZoneDrawer;
import com.severeweatheralerts.Graphics.Bounds.AspectRatioEqualizer;
import com.severeweatheralerts.Graphics.Bounds.BoundRounder;
import com.severeweatheralerts.Graphics.Bounds.Bounds;
import com.severeweatheralerts.Graphics.Bounds.BoundMargin;
import com.severeweatheralerts.Graphics.Graphic;
import com.severeweatheralerts.Graphics.GridData.MapRegion;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.Polygon.Polygon;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.MapTimeParser;
import com.severeweatheralerts.Graphics.Polygon.GCSToMercatorCoordinateAdapter;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.PolygonListBoundCalculator;
import com.severeweatheralerts.JSONParsing.GeometryParser;
import com.severeweatheralerts.JSONParsing.GridDataParser;
import com.severeweatheralerts.Networking.FetchServices.RequestCallback;
import com.severeweatheralerts.Networking.FetchServices.LayerListFetch;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;
import com.severeweatheralerts.Networking.FetchServices.StringListFetch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public abstract class GraphicGenerator {
  private final GCSCoordinate location;
  private GraphicCompleteListener graphicCompleteListener;
  private final Graphic graphic;

  protected final Context context;
  protected final Alert alert;

  protected void forecast(Parameter gridData) {}
  protected void mapTimes(ArrayList<MapTime> mapTimes) {}
  protected void pointInfo(String response) {}
  protected void alertPolygons(ArrayList<Polygon> polygons) {}

  public GraphicGenerator(Context context, Alert alert, GCSCoordinate location) {
    this.context = context;
    this.alert = alert;
    this.location = location;
    graphic = new Graphic();
  }

  public void generate(GraphicCompleteListener graphicCompleteListener) {
    this.graphicCompleteListener = graphicCompleteListener;
  }

  protected void getAlertPolygons() {
    if (alert.hasGeometry()) { alertPolygons(alert.getPolygons()); return; }
    else if (alert.getZoneLinkCount() <= 0) throwError("This alert does not have zone data");
    StringListFetch fetchService = new StringListFetch(context, alert.getZones());
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new RequestCallback() {
      @Override
      public void success(Object response) {
        parseZones((ArrayList<String>) response);
      }

      @Override public void error(VolleyError error) {
        throwError("Error fetching alert zones");
      }
    });
  }

  protected void throwError(String message) {
    graphicCompleteListener.error(message);
  }

  protected void setSubtext(String subtext) {
    graphic.setSubtext(subtext);
  }

  protected void getMapTimes(String parameter) {
    StringFetchService fetchService = new StringFetchService(context, new URL().getMapTimes(parameter, getRegion()));
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.request(new RequestCallback() {
      @Override
      public void success(Object response) {
        ArrayList<MapTime> mapTimes = new MapTimeParser(response.toString()).getMapTimes();
        if (mapTimes == null || mapTimes.size() == 0) throwError("Times for this graphic unavailable");
        else mapTimes(mapTimes);
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error getting available map times");
      }
    });
  }

  protected void getPointInfo() {
    StringFetchService fetchService = new StringFetchService(context, new URL().getPointInfo(location.getLat(), location.getLong()));
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.request(new RequestCallback() {
      @Override
      public void success(Object response) {
        pointInfo((String)response);
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error getting zone info");
      }
    });
  }

  public void getForecast(String gridDataURL, String parameter) {
    StringFetchService fetchService = new StringFetchService(context, gridDataURL);
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.request(new RequestCallback() {
      @Override
      public void success(Object response) {
        parseForecast(response, parameter);
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error getting forecast");
      }
    });
  }

  private void parseForecast(Object response, String parameter) {
    try { forecast(new GridDataParser(response.toString()).getParameter(parameter)); }
    catch (JSONException e) { throwError("Error parsing grid data"); }
  }

  protected void generateGraphicFromLayers(ArrayList<Layer> layers) {
    LayerListFetch fetchService = new LayerListFetch(context, layers);
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new RequestCallback() {
      @Override
      public void success(Object response) {
        layers((ArrayList<Bitmap>) response);
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error fetching images");
      }
    });
  }

  protected void layers(ArrayList<Bitmap> bitmaps) {
    returnGraphic(bitmaps);
  }

  private void returnGraphic(ArrayList<Bitmap> bitmaps) {
    graphic.setImage(new BitmapCombiner(bitmaps).combine());
    graphicCompleteListener.onComplete(graphic);
  }

  protected Bitmap getZoneOverlay(Bounds bounds) {
    return new ZoneDrawer(alert.getPolygons(), alert.getColorAt(new Date()), bounds).getBitmap();
  }

  protected Bitmap getLocationPointOverlay(Bounds bounds, int color) {
    return new LocationDrawer(bounds, getMercatorCoordinate(), color).getBitmap();
  }

  protected String getRegion() {
    return new MapRegion(alert.getSenderCode()).get();
  }

  protected MercatorCoordinate getMercatorCoordinate() {
    return new GCSToMercatorCoordinateAdapter(location).getCoordinate();
  }

  protected Bounds getBounds(ArrayList<Polygon> polygons, double marginPercentage) {
    Bounds bounds = new PolygonListBoundCalculator(polygons).getBounds();
    bounds = new BoundRounder(bounds).getBounds();
    bounds = new AspectRatioEqualizer(bounds).equalize();
    bounds = new BoundMargin(bounds, marginPercentage).getBounds();
    return bounds;
  }

  private void parseZones(ArrayList<String> response) {
    for (String zone : response) {
      try {
        ArrayList<GeoJSONPolygon> geometry = new GeometryParser(new JSONObject(zone).getJSONObject("geometry")).parseGeometry();
        for (int i = 0; i < geometry.size(); i++)
          alert.addPolygon(PolygonAdapter.toMercatorPolygon(geometry.get(i)));
      }
      catch (JSONException e) { e.printStackTrace(); }
    }
    alertPolygons(alert.getPolygons());
  }
}
