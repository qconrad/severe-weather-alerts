package com.severeweatheralerts.Graphics.GraphicGeneration;

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
import com.severeweatheralerts.Graphics.Polygon.GCSToMercatorCoordinateAdapter;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Graphics.Polygon.PolygonListBoundCalculator;
import com.severeweatheralerts.Graphics.URLGeneration.URLGenerator;
import com.severeweatheralerts.JSONParsing.GeometryParser;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.ImageListFetch;
import com.severeweatheralerts.Networking.FetchServices.StringListFetch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public abstract class GraphicGenerator {
  protected final Context context;
  protected Bound bound;

  private final Alert alert;
  protected final Location location;
  protected GraphicCompleteListener graphicCompleteListener;

  public GraphicGenerator(Context context, Alert alert, Location location) {
    this.context = context;
    this.alert = alert;
    this.location = location;
  }

  protected abstract URLGenerator getURLGenerator();

  public void generate(GraphicCompleteListener graphicCompleteListener) {
    this.graphicCompleteListener = graphicCompleteListener;
    if (alert.hasGeometry()) generateImages();
    else fetchZones();
  }

  protected void generateImages() {
    bound = getBound();
    URLGenerator graphicURLGenerator = getURLGenerator();
    graphicURLGenerator.generate(this::fetchImages);
  }

  protected void fetchZones() {
    StringListFetch fetchService = new StringListFetch(context, alert.getZones());
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        parseZones((ArrayList<String>) response);
        generateImages();
      }

      @Override public void error(VolleyError error) { }
    });
  }

  protected void fetchImages(ArrayList<String> urls) {
    ImageListFetch fetchService = new ImageListFetch(context, urls);
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        ArrayList<Bitmap> bitmaps = (ArrayList<Bitmap>) response;
        bitmaps.add(new ZoneDrawer(alert.getPolygons(), alert.getColorAt(new Date()), bound, getMercatorCoordinate()).getBitmap());
        Graphic graphic = new Graphic();
        graphic.setImage(new BitmapCombiner(bitmaps).combine());
        graphicCompleteListener.onComplete(graphic);
      }

      @Override
      public void error(VolleyError error) {
        graphicCompleteListener.onComplete(null);
      }
    });
  }

  private MercatorCoordinate getMercatorCoordinate() {
    GCSCoordinate lonLat = new GCSCoordinate(location.getLatitude(), location.getLongitude());
    return new GCSToMercatorCoordinateAdapter(lonLat).getCoordinate();
  }

  protected Bound getBound() {
    Bound bounds = new PolygonListBoundCalculator(alert.getPolygons()).getBounds();
    bounds = new AspectRatioEqualizer(bounds).equalize();
    bounds = new BoundMargin(bounds, Constants.DEFAULT_GRAPHIC_MARGIN).getBounds();
    return bounds;
  }

  private void parseZones(ArrayList<String> response) {
    for (String zone : response) {
      ArrayList<GeoJSONPolygon> geometry = null;
      try { geometry = new GeometryParser(new JSONObject(zone).getJSONObject("geometry")).parseGeometry(); }
      catch (JSONException e) { e.printStackTrace(); }
      for (int i = 0; i < geometry.size(); i++)
        alert.addPolygon(PolygonAdapter.toMercatorPolygon(geometry.get(i)));
    }
  }
}
