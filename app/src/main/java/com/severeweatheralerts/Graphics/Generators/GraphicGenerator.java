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
import com.severeweatheralerts.Graphics.BitmapTools.ZoneDrawer;
import com.severeweatheralerts.Graphics.Bounds.AspectRatioEqualizer;
import com.severeweatheralerts.Graphics.Bounds.Bound;
import com.severeweatheralerts.Graphics.Bounds.BoundMargin;
import com.severeweatheralerts.Graphics.Graphic;
import com.severeweatheralerts.Graphics.GridData.MapRegion;
import com.severeweatheralerts.Graphics.GridData.Parameter;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.GridData.MapTime;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.JSONParsing.MapTimeParser;
import com.severeweatheralerts.JSONParsing.PointInfoParser;
import com.severeweatheralerts.Graphics.Polygon.GCSToMercatorCoordinateAdapter;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.PolygonListBoundCalculator;
import com.severeweatheralerts.JSONParsing.GeometryParser;
import com.severeweatheralerts.JSONParsing.GridDataParser;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.LayerListFetch;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;
import com.severeweatheralerts.Networking.FetchServices.StringListFetch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public abstract class GraphicGenerator {
  protected final Context context;
  private final GCSCoordinate location;
  private GraphicCompleteListener graphicCompleteListener;

  protected final Alert alert;
  protected ArrayList<Layer> layers = new ArrayList<>();
  protected Bound bound;
  protected ArrayList<MapTime> mapTimes;

  protected String gridParameter;
  protected String mapTimeParameter;

  protected abstract void getURLs();
  protected void gridDataAvailable(Parameter gridData) {}

  protected String getSubText() {
    return null;
  }

  public GraphicGenerator(Context context, Alert alert, GCSCoordinate location) {
    this.context = context;
    this.alert = alert;
    this.location = location;
  }

  public void generate(GraphicCompleteListener graphicCompleteListener) {
    this.graphicCompleteListener = graphicCompleteListener;
    if (!alert.hasGeometry()) fetchZones();
    else finish();
    fetchMapTimes();
    fetchGridData();
  }

  private int fetchesRemaining = 3;
  protected void finish() {
    if (--fetchesRemaining <= 0) generateImages();
  }

  private void generateImages() {
    bound = getBound();
    getURLs();
    fetchImages();
  }

  protected void throwError(String message) {
    graphicCompleteListener.error(message);
  }

  public void fetchGridData() {
    if (gridParameter != null) fetchPointInfo();
    else finish();
  }

  public void fetchMapTimes() {
    StringFetchService fetchService = new StringFetchService(context, new URL().getMapTimes(mapTimeParameter, getRegion()));
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        mapTimes = new MapTimeParser(response.toString()).getMapTimes();
        finish();
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error getting available map times");
      }
    });
  }

  private void fetchPointInfo() {
    StringFetchService fetchService = new StringFetchService(context, new URL().getPointInfo(location.getLat(), location.getLong()));
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        getForecastGridData(new PointInfoParser(response.toString()).getForecastGridLink());
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error getting zone info");
      }
    });
  }

  public void getForecastGridData(String gridDataURL) {
    StringFetchService fetchService = new StringFetchService(context, gridDataURL);
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        parseGridData(response);
        finish();
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error getting grid data");
      }
    });
  }

  private void parseGridData(Object response) {
    try { gridDataAvailable(new GridDataParser(response.toString()).getParameter(gridParameter)); }
    catch (JSONException e) { throwError("Error parsing grid data"); }
  }

  protected void fetchZones() {
    if (alert.getZoneLinkCount() == 0) finish();
    StringListFetch fetchService = new StringListFetch(context, alert.getZones());
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        parseZones((ArrayList<String>) response);
        finish();
      }

      @Override public void error(VolleyError error) {
        throwError("Error fetching alert zones");
      }
    });
  }

  protected void fetchImages() {
    LayerListFetch fetchService = new LayerListFetch(context, layers);
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        returnGraphic((ArrayList<Bitmap>) response);
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error fetching images");
      }
    });
  }

  private void returnGraphic(ArrayList<Bitmap> bitmaps) {
    Graphic graphic = new Graphic();
    graphic.setSubtext(getSubText());
    graphic.setImage(new BitmapCombiner(bitmaps).combine());
    graphicCompleteListener.onComplete(graphic);
  }

  protected Bitmap getZoneOverlay() {
    return new ZoneDrawer(alert.getPolygons(), alert.getColorAt(new Date()), bound, getMercatorCoordinate()).getBitmap();
  }

  protected String getRegion() {
    return new MapRegion(alert.getSenderCode()).get();
  }

  private MercatorCoordinate getMercatorCoordinate() {
    return new GCSToMercatorCoordinateAdapter(location).getCoordinate();
  }

  protected Bound getBound() {
    Bound bounds = new PolygonListBoundCalculator(alert.getPolygons()).getBounds();
    bounds = new AspectRatioEqualizer(bounds).equalize();
    bounds = new BoundMargin(bounds, Constants.DEFAULT_GRAPHIC_MARGIN).getBounds();
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
  }
}
