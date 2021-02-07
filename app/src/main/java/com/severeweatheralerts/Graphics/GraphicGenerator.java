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
import com.severeweatheralerts.PolygonListBoundCalculator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class GraphicGenerator {
  private final Alert alert;
  private final Context context;
  private final MercatorCoordinate location;

  public GraphicGenerator(Context context, GraphicType graphicType, Alert alert, MercatorCoordinate location) {
    this.context = context;
    this.alert = alert;
    this.location = location;
  }

  public void generate(GraphicCompleteListener graphicCompleteListener) {
    Graphic graphic = new Graphic();
    if (!alert.hasGeometry()) fetchZones(graphicCompleteListener, graphic);
    else generateImage(graphicCompleteListener, graphic);
  }

  private void fetchZones(GraphicCompleteListener graphicCompleteListener, Graphic graphic) {
    StringListFetch fetchService = new StringListFetch(context, alert.getZones());
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        parseZones((ArrayList<String>) response);
        generateImage(graphicCompleteListener, graphic);
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

  private void generateImage(GraphicCompleteListener graphicCompleteListener, Graphic graphic) {
    Bounds bounds = getBounds();
    ArrayList<String> urls = new ArrayList<>();
    urls.add(new URLGenerator().getTotalSnow(bounds, "conus", "2021-02-10T00:00"));
    urls.add(new URLGenerator().getCountyMap(bounds));
    urls.add(new URLGenerator().getTotalSnowPoints(bounds, "conus", "2021-02-10T00:00"));
    ImageListFetch fetchService = new ImageListFetch(context, urls);
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        ArrayList<Bitmap> bitmaps = (ArrayList<Bitmap>) response;
        bitmaps.add(new ZoneDrawer(alert.getPolygons(), alert.getColorAt(new Date()), bounds, location).getBitmap());
        graphic.setImage(new BitmapCombiner(bitmaps).combine());
        graphicCompleteListener.onComplete(graphic);
      }

      @Override
      public void error(VolleyError error) {
        System.out.println(error.getMessage());
      }
    });
  }

  private Bounds getBounds() {
    return new BoundMargin(new BoundAspectRatio(new PolygonListBoundCalculator(alert.getPolygons()).getBounds()).equalize(), Constants.DEFAULT_GRAPHIC_MARGIN).getBounds();
  }
}
