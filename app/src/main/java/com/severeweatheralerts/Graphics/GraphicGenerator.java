package com.severeweatheralerts.Graphics;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.VolleyError;
import com.severeweatheralerts.Adapters.GeoJSONPolygon;
import com.severeweatheralerts.Adapters.PolygonAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.JSONParsing.GeometryParser;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.ImageListFetch;
import com.severeweatheralerts.Networking.FetchServices.StringListFetch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class GraphicGenerator {
  private final Alert alert;
  private final Context context;
  private final MercatorCoordinate location;
  private final GraphicType graphicType;
  private GraphicCompleteListener graphicCompleteListener;
  private Bounds bounds;

  public GraphicGenerator(Context context, GraphicType graphicType, Alert alert, MercatorCoordinate location) {
    this.context = context;
    this.alert = alert;
    this.location = location;
    this.graphicType = graphicType;
  }

  public void generate(GraphicCompleteListener graphicCompleteListener) {
    this.graphicCompleteListener = graphicCompleteListener;
    if (!alert.hasGeometry()) fetchZones();
    else generateImages();
  }

  private void fetchZones() {
    StringListFetch fetchService = new StringListFetch(context, alert.getZones());
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        parseZones((ArrayList<String>) response);
        bounds = getBounds();
        generateImages();
      }

      @Override public void error(VolleyError error) { }
    });
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

  private void generateImages() {
    URLGenerator graphicURLGenerator = new URLGenerator(context, graphicType, bounds);
    graphicURLGenerator.generate((URLGenCompleteListener) this::fetchImages);
  }

  private void fetchImages(ArrayList<String> urls) {
    ImageListFetch fetchService = new ImageListFetch(context, urls);
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        ArrayList<Bitmap> bitmaps = (ArrayList<Bitmap>) response;
        bitmaps.add(new ZoneDrawer(alert.getPolygons(), alert.getColorAt(new Date()), bounds, location).getBitmap());
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

  protected Bounds getBounds() {
    Bounds bounds = new PolygonListBoundCalculator(alert.getPolygons()).getBounds();
    bounds = new BoundAspectRatio(bounds).equalize();
    bounds = new BoundMargin(bounds, Constants.DEFAULT_GRAPHIC_MARGIN).getBounds();
    return bounds;
  }
}
