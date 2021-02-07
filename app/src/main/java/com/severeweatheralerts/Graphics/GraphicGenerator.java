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
import com.severeweatheralerts.Networking.FetchServices.ImageFetchService;
import com.severeweatheralerts.Networking.FetchServices.ListFetch;
import com.severeweatheralerts.PolygonListBoundCalculator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GraphicGenerator {
  private final Alert alert;
  private final Context context;

  public GraphicGenerator(Context context, GraphicType graphicType, Alert alert) {
    this.context = context;
    this.alert = alert;
  }

  public void generate(GraphicCompleteListener graphicCompleteListener) {
    Graphic graphic = new Graphic();
    if (!alert.hasGeometry()) fetchZones(graphicCompleteListener, graphic);
    else generateImage(graphicCompleteListener, graphic);
  }

  private void fetchZones(GraphicCompleteListener graphicCompleteListener, Graphic graphic) {
    ListFetch fetchService = new ListFetch(context, alert.getZones());
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
    ImageFetchService imageFetchService = new ImageFetchService(context, new URLGenerator().getCountyMap(bounds));
    imageFetchService.setUserAgent(Constants.USER_AGENT);
    imageFetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        Bitmap baseCountyMap = (Bitmap) response;
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(baseCountyMap);
        Bitmap zonePolygons = new ZoneDrawer(alert.getPolygons(), alert.getColor(), bounds).getBitmap();
        bitmaps.add(zonePolygons);
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
