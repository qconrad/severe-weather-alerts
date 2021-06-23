package com.severeweatheralerts.Graphics.Generators.OneHourPrecipitation;

import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.TimeFormatters.RelativeTimeFormatter;

import java.util.ArrayList;
import java.util.Date;

public class PrecipitationTextGenerator {
  private final ArrayList<ForecastTime> forecast;
  private final Date date;

  public PrecipitationTextGenerator(ArrayList<ForecastTime> forecast, Date date) {
    this.forecast = forecast;
    this.date = date;
  }

  public String getText() {
    String textForecast;
    if (forecast.size() <= 0) return "No forecast found";
    textForecast = getParameter("Heavy rain", 2.5);
    textForecast += getParameter(", hail", 3.5);
    if (textEmpty(textForecast)) textForecast += getParameter("Rain", 1.5);
    if (textEmpty(textForecast)) return null;
    return textForecast;
  }

  private boolean textEmpty(String textForecast) {
    return textForecast.equals("");
  }

  private String getParameter(String type, double threshold) {
    if (startsAboveThreshold(threshold)) return getDuration(type, threshold);
    return getStartTime(type, threshold);
  }

  private String getStartTime(String type, double threshold) {
    for (int i = 0; i < forecast.size(); i++) {
      if (forecast.get(i).getValue() >= threshold)
        return type + " in " + getRelativeTime(i);
    }
    return "";
  }

  private String getDuration(String type, double threshold) {
    for (int i = 0; i < forecast.size(); i++) {
      if (forecast.get(i).getValue() < threshold)
        return type + " for " + getRelativeTime(i);
    }
    return type;
  }

  private boolean startsAboveThreshold(double threshold) {
    return forecast.get(0).getValue() >= threshold;
  }

  private String getRelativeTime(int i) {
    return new RelativeTimeFormatter(date, forecast.get(i).getDate()).getFormattedString();
  }
}
