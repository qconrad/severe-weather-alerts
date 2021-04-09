package com.severeweatheralerts.Graphics.Generators;

import android.content.Context;

import com.android.volley.VolleyError;
import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.Bounds.Bound;
import com.severeweatheralerts.Graphics.Layer;
import com.severeweatheralerts.Graphics.URL;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;
import com.severeweatheralerts.RadarMapTimeParser;
import com.severeweatheralerts.TimeFormatters.AbsoluteTimeFormatter;

import java.util.Date;
import java.util.TimeZone;

import static com.severeweatheralerts.Adapters.CoordinateAdapter.lon2x;
import static com.severeweatheralerts.Adapters.CoordinateAdapter.x2lon;
import static com.severeweatheralerts.Adapters.CoordinateAdapter.y2lat;

public class RadarGenerator extends GraphicGenerator {
  private final double ZOOM_BUFFER = 0.8;

  public RadarGenerator(Context context, Alert alert, GCSCoordinate location) {
    super(context, alert, location);
  }

  @Override
  public void fetchMapTimes() {
    StringFetchService fetchService = new StringFetchService(context, new URL().getRadarMapTimes());
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        mapTimes = new RadarMapTimeParser(response.toString()).getMapTimes();
        finish();
      }

      @Override
      public void error(VolleyError error) {
        throwError("Error getting available map times");
      }
    });
  }

  @Override
  protected void getURLs() {
    layers.add(new Layer(new URL().getRadarImage(mapTimes.get(mapTimes.size()-1).getString(),
            String.valueOf(getZoomLevel()),
            String.valueOf(y2lat(getYCenter(bound))),
            String.valueOf(x2lon(getXCenter(bound))))));
    layers.add(new Layer(new URL().getCountyMap(bound)));
    layers.add(new Layer(getZoneOverlay()));
  }

  @Override
  protected String getSubText() {
    return new AbsoluteTimeFormatter(new Date(), mapTimes.get(mapTimes.size()-1).getDate(), TimeZone.getDefault()).getFormattedString();
  }

  private int getZoomLevel() {
    double boundWidth = (bound.getRight() * ZOOM_BUFFER) - (bound.getLeft() * ZOOM_BUFFER);
    return (int) Math.ceil(Math.log(lon2x(180) / boundWidth) / Math.log(2));
  }

  protected Bound getBound() {
    bound = super.getBound();
    double zoomLevel = getZoomLevel();
    double halfTileWidth = lon2x(180) / Math.pow(2, zoomLevel);
    double xCenter = getXCenter(bound);
    double yCenter = getYCenter(bound);
    return new Bound(yCenter+halfTileWidth, xCenter+halfTileWidth, yCenter-halfTileWidth, xCenter-halfTileWidth);
  }

  private double getXCenter(Bound bound) {
    return (bound.getRight() + bound.getLeft()) / 2;
  }

  private double getYCenter(Bound bound) {
    return (bound.getTop() + bound.getBottom()) / 2;
  }
}
