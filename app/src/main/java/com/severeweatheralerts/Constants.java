package com.severeweatheralerts;

public class Constants {
  public static final String FEEDBACK_URL = "https://us-central1-severe-weather-alerts.cloudfunctions.net/feedback";
  public static final String USER_AGENT = "(Severe Weather Alerts Android Client, https://github.com/qconrad/severe-weather-alerts)";
  public static final double DEFAULT_GRAPHIC_MARGIN = 0.25;
  public static final int STATUS_SUBTEXT_TRANSITION_TIME = 5000;
  public static final int LOCATION_COMPUTE_INTERVAL = 30 * 60 * 1000;
  public static final int FASTEST_LOCATION_INTERVAL = 10 * 60 * 1000;
  public static final int MINIMUM_LOCATION_DISPLACEMENT = 200;
  public static final long LAST_KNOWN_LOCATION_EXPIRE = 5 * 60 * 1000;
  public static final long APP_OPENED_LOCATION_EXPIRE = 15 * 60 * 1000;
  public static final int CHECK_FOR_EXPIRATION_REFRESH_TIME = 5 * 1000;
  public static final int APP_OPEN_LAST_KNOWN_LOCATION_CHECK_INTERVAL = 2 * 60 * 1000;
  public static final double LOCATION_CHANGE_MARGIN = 0.001;
  public static final long COMPOSITE_RADAR_SHOW_BEFORE_EVENT_TIME = 5 * 60 * 60 * 1000;
  public static final int MAX_LOCATION_NAME_LENGTH = 20;
  public static final long SNOWFALL_AMOUNT_TIME_RANGE = 72 * 60 * 60 * 1000;
  public static final long RAINFALL_AMOUNT_TIME_RANGE = 72 * 60 * 60 * 1000;
  public static final long ICE_AMOUNT_TIME_RANGE = 72 * 60 * 60 * 1000;
  public static final double MINIMUM_SNOWFALL_GRAPHIC_SIZE = 500000;
  public static final double MINIMUM_RAINFALL_GRAPHIC_SIZE = 500000;
  public static final double MINIMUM_ICE_GRAPHIC_SIZE = 500000;
}