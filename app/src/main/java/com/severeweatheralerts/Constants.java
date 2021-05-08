package com.severeweatheralerts;

public class Constants {
  public static final double DEFAULT_GRAPHIC_MARGIN = 0.25;
  public static final int SNOWFALL_AMOUNT_DECIMAL_PLACES = 1;
  public static final int RAINFALL_AMOUNT_DECIMAL_PLACES = 2;
  public static final String USER_AGENT = "(Severe Weather Alerts Android Client, https://github.com/qconrad/severe-weather-alerts)";
  public static final int STATUS_SUBTEXT_TRANSITION_TIME = 5000;
  public static final int LOCATION_COMPUTE_INTERVAL = 30 * 60 * 1000;
  public static final int FASTEST_LOCATION_INTERVAL = 10 * 60 * 1000;
  public static final int MINIMUM_LOCATION_DISPLACEMENT = 200;
  public static final long LAST_KNOWN_LOCATION_EXPIRE = 5 * 60 * 1000;
}